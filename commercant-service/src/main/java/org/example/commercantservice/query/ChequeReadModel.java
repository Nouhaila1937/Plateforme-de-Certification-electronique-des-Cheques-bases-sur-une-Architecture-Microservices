package org.example.commercantservice.query;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "cheque_read_model")
@Data
public class ChequeReadModel {
    @Id
    private Long id;
    private String numero;
    private String codeBanque;
    private String numeroCompte;
    private String nomClient;
    private Double montant;
    private String statut; // CREE, EN_ATTENTE, CERTIFIE, REJETE
    private String certificationReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}