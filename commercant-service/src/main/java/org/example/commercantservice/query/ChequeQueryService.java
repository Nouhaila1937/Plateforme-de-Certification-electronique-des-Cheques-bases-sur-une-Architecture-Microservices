package org.example.commercantservice.query;


import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChequeQueryService {

    private final ChequeReadModelRepository repository;

    public ChequeQueryService(ChequeReadModelRepository repository) {
        this.repository = repository;
    }

    public List<ChequeReadModel> getAllCheques() {
        return repository.findAll();
    }

    public Optional<ChequeReadModel> getChequeById(Long id) {
        return repository.findById(id);
    }

    public List<ChequeReadModel> getChequesByStatut(String statut) {
        return repository.findByStatut(statut);
    }

    public List<ChequeReadModel> searchByClient(String nomClient) {
        return repository.findByNomClientContaining(nomClient);
    }
}
