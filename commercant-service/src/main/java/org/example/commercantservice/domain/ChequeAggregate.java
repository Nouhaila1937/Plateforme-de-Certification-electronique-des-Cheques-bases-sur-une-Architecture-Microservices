package org.example.commercantservice.domain;

import lombok.Data;
import org.example.commercantservice.event.ChequeCreatedEvent;

@Data
public class ChequeAggregate {
    private Long id;
    private String numero;
    private String codeBanque;
    private String numeroCompte;
    private String nomClient;
    private Double montant;
    private boolean certifie;
    private boolean rejected;

    public void apply(ChequeCreatedEvent event) {
        this.id = event.getChequeId();
        this.numero = event.getNumero();
        this.codeBanque = event.getCodeBanque();
        this.numeroCompte = event.getNumeroCompte();
        this.nomClient = event.getNomClient();
        this.montant = event.getMontant();
        this.certifie = false;
        this.rejected = false;
    }
}