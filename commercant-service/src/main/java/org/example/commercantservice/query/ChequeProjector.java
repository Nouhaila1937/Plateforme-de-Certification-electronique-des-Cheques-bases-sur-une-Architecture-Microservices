package org.example.commercantservice.query;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.commercantservice.event.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChequeProjector {

    private final ChequeReadModelRepository repository;
    private final ObjectMapper objectMapper;

    public ChequeProjector(ChequeReadModelRepository repository,
                           ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "cheque-events", groupId = "cheque-projector")
    @Transactional
    public void onEvent(String eventJson) {
        try {
            if (eventJson.contains("ChequeCreated")) {
                ChequeCreatedEvent event = objectMapper.readValue(
                        eventJson, ChequeCreatedEvent.class);
                projectChequeCreated(event);
            }
            else if (eventJson.contains("ChequeCertificationRequested")) {
                ChequeCertificationRequestedEvent event = objectMapper.readValue(
                        eventJson, ChequeCertificationRequestedEvent.class);
                projectCertificationRequested(event);
            }
            else if (eventJson.contains("ChequeCertified")) {
                ChequeCertifiedEvent event = objectMapper.readValue(
                        eventJson, ChequeCertifiedEvent.class);
                projectChequeCertified(event);
            }
            else if (eventJson.contains("ChequeRejected")) {
                ChequeRejectedEvent event = objectMapper.readValue(
                        eventJson, ChequeRejectedEvent.class);
                projectChequeRejected(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void projectChequeCreated(ChequeCreatedEvent event) {
        ChequeReadModel model = new ChequeReadModel();
        model.setId(event.getChequeId());
        model.setNumero(event.getNumero());
        model.setCodeBanque(event.getCodeBanque());
        model.setNumeroCompte(event.getNumeroCompte());
        model.setNomClient(event.getNomClient());
        model.setMontant(event.getMontant());
        model.setStatut("CREE");
        model.setCreatedAt(event.getTimestamp());
        repository.save(model);
    }

    private void projectCertificationRequested(ChequeCertificationRequestedEvent event) {
        ChequeReadModel model = repository.findById(event.getChequeId()).orElseThrow();
        model.setStatut("EN_ATTENTE");
        model.setUpdatedAt(event.getTimestamp());
        repository.save(model);
    }

    private void projectChequeCertified(ChequeCertifiedEvent event) {
        ChequeReadModel model = repository.findById(event.getChequeId()).orElseThrow();
        model.setStatut("CERTIFIE");
        model.setCertificationReference(event.getCertificationReference());
        model.setUpdatedAt(event.getTimestamp());
        repository.save(model);
    }

    private void projectChequeRejected(ChequeRejectedEvent event) {
        ChequeReadModel model = repository.findById(event.getChequeId()).orElseThrow();
        model.setStatut("REJETE");
        model.setUpdatedAt(event.getTimestamp());
        repository.save(model);
    }
}