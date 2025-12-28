package org.example.commercantservice.Service;

import org.example.commercantservice.DTO.ChequeDTO;
import org.example.commercantservice.Entities.Cheque;
import org.example.commercantservice.Mapper.ChequeMapper;
import org.example.commercantservice.Repository.ChequeRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChequeService {

    private final ChequeRepository repository;
    private final KafkaTemplate<String, ChequeDTO> kafkaTemplate;

    public ChequeService(ChequeRepository repository, KafkaTemplate<String, ChequeDTO> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Cheque> getAll() { return repository.findAll(); }

    public Cheque save(Cheque c) {
        c.setCertifie(false);
        Cheque saved = repository.save(c);
        kafkaTemplate.send("cheque-topic", ChequeMapper.toDTO(saved));
        return saved;
    }

    public Cheque certifyCheque(Long id) {
        Cheque c = repository.findById(id).orElseThrow();
        c.setCertifie(true);
        Cheque saved = repository.save(c);
        kafkaTemplate.send("cheque-topic", ChequeMapper.toDTO(saved));
        return saved;
    }
}
