package org.example.commercantservice.Repository;

import org.example.commercantservice.Entities.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
}
