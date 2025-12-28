package org.example.commercantservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChequeDTO {
    public String numero;
    public String codeBanque;
    public String numeroCompte;
    public String nomClient;
    public Double montant;
    public Boolean certifie;
}
