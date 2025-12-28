package org.example.commercantservice.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ChequeEventRepository extends JpaRepository<ChequeEvent, Long> {
    List<ChequeEvent> findByAggregateIdOrderByVersionAsc(Long aggregateId);

    @Query("SELECT MAX(e.version) FROM ChequeEvent e WHERE e.aggregateId = :aggregateId")
    Long findMaxVersionByAggregateId(Long aggregateId);
}
