package org.example.commercantservice.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChequeCertificationRequestedEvent {
    private Long chequeId;
    private String codeBanque;
    private String numeroCompte;
    private Double montant;
    private LocalDateTime timestamp;
    private String eventType = "ChequeCertificationRequested";
}