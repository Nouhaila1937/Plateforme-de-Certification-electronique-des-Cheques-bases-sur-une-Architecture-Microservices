package org.example.commercantservice.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChequeCreatedEvent {
    private Long chequeId;
    private String numero;
    private String codeBanque;
    private String numeroCompte;
    private String nomClient;
    private Double montant;
    private LocalDateTime timestamp;
    private String eventType = "ChequeCreated";
}
