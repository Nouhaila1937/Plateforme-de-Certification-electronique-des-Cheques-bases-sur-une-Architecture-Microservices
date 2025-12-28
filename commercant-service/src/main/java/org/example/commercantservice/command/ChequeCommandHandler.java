package org.example.commercantservice.command;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.commercantservice.domain.*;
import org.example.commercantservice.event.ChequeCreatedEvent;
import org.example.commercantservice.event.*;
import org.example.commercantservice.eventstore.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChequeCommandHandler {

    private final ChequeEventRepository eventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ChequeCommandHandler(ChequeEventRepository eventRepository,
                                KafkaTemplate<String, Object> kafkaTemplate,
                                ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Long handle(CreateChequeCommand command) throws Exception {
        // Générer un ID pour le nouvel aggregate
        Long chequeId = System.currentTimeMillis();

        // Créer l'événement
        ChequeCreatedEvent event = new ChequeCreatedEvent(
                chequeId,
                command.getNumero(),
                command.getCodeBanque(),
                command.getNumeroCompte(),
                command.getNomClient(),
                command.getMontant(),
                LocalDateTime.now(),
                "ChequeCreated"
        );

        // Sauvegarder dans l'Event Store
        saveEvent(chequeId, event, 1L);

        // Publier dans Kafka
        kafkaTemplate.send("cheque-events", event);

        return chequeId;
    }

    @Transactional
    public void handle(RequestChequeCertificationCommand command) throws Exception {
        // Charger l'état actuel
        ChequeAggregate aggregate = loadAggregate(command.getChequeId());

        if (aggregate.isCertifie()) {
            throw new IllegalStateException("Chèque déjà certifié");
        }

        if (aggregate.isRejected()) {
            throw new IllegalStateException("Chèque rejeté");
        }

        // Créer l'événement
        ChequeCertificationRequestedEvent event = new ChequeCertificationRequestedEvent(
                command.getChequeId(),
                aggregate.getCodeBanque(),
                aggregate.getNumeroCompte(),
                aggregate.getMontant(),
                LocalDateTime.now(),
                "ChequeCertificationRequested"
        );

        // Sauvegarder
        Long nextVersion = getNextVersion(command.getChequeId());
        saveEvent(command.getChequeId(), event, nextVersion);

        // Publier vers Banque Centrale
        kafkaTemplate.send("certification-requests", event);
    }

    @Transactional
    public void handle(MarkChequeAsCertifiedCommand command) throws Exception {
        ChequeCertifiedEvent event = new ChequeCertifiedEvent(
                command.getChequeId(),
                command.getCertificationReference(),
                LocalDateTime.now(),
                "ChequeCertified"
        );

        Long nextVersion = getNextVersion(command.getChequeId());
        saveEvent(command.getChequeId(), event, nextVersion);

        kafkaTemplate.send("cheque-events", event);
    }

    @Transactional
    public void handle(RejectChequeCommand command) throws Exception {
        ChequeRejectedEvent event = new ChequeRejectedEvent(
                command.getChequeId(),
                command.getRaison(),
                LocalDateTime.now(),
                "ChequeRejected"
        );

        Long nextVersion = getNextVersion(command.getChequeId());
        saveEvent(command.getChequeId(), event, nextVersion);

        kafkaTemplate.send("cheque-events", event);
    }

    // Méthodes utilitaires
    private void saveEvent(Long aggregateId, Object event, Long version) throws Exception {
        ChequeEvent storedEvent = new ChequeEvent();
        storedEvent.setAggregateId(aggregateId);
        storedEvent.setEventType(event.getClass().getSimpleName());
        storedEvent.setEventData(objectMapper.writeValueAsString(event));
        storedEvent.setTimestamp(LocalDateTime.now());
        storedEvent.setVersion(version);
        eventRepository.save(storedEvent);
    }

    private Long getNextVersion(Long aggregateId) {
        Long maxVersion = eventRepository.findMaxVersionByAggregateId(aggregateId);
        return maxVersion == null ? 1L : maxVersion + 1;
    }

    private ChequeAggregate loadAggregate(Long chequeId) throws Exception {
        List<ChequeEvent> events = eventRepository.findByAggregateIdOrderByVersionAsc(chequeId);

        if (events.isEmpty()) {
            throw new IllegalArgumentException("Chèque non trouvé");
        }

        ChequeAggregate aggregate = new ChequeAggregate();

        for (ChequeEvent event : events) {
            switch (event.getEventType()) {
                case "ChequeCreatedEvent":
                    ChequeCreatedEvent createdEvent = objectMapper.readValue(
                            event.getEventData(), ChequeCreatedEvent.class);
                    aggregate.apply(createdEvent);
                    break;
                case "ChequeCertifiedEvent":
                    aggregate.setCertifie(true);
                    break;
                case "ChequeRejectedEvent":
                    aggregate.setRejected(true);
                    break;
            }
        }

        return aggregate;
    }
}
