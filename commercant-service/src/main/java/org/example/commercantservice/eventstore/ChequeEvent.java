package org.example.commercantservice.eventstore;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "cheque_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChequeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aggregateId; // chequeId
    private String eventType;

    @Column(length = 4000)
    private String eventData;

    private LocalDateTime timestamp;
    private Long version;
}