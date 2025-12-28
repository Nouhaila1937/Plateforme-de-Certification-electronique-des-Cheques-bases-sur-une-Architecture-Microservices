package org.example.commercantservice.query;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChequeReadModelRepository extends JpaRepository<ChequeReadModel, Long> {
    List<ChequeReadModel> findByStatut(String statut);
    List<ChequeReadModel> findByNomClientContaining(String nomClient);
}