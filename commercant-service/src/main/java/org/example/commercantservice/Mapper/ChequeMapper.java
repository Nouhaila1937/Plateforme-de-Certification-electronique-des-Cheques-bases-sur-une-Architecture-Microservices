package org.example.commercantservice.Mapper;

import org.example.commercantservice.DTO.ChequeDTO;
import org.example.commercantservice.Entities.Cheque;

public class ChequeMapper {

    // Transformation de l'entité Cheque vers ChequeDTO
    public static ChequeDTO toDTO(Cheque c) {
        if (c == null) return null;

        ChequeDTO dto = new ChequeDTO();
        dto.setNumero(c.getNumero());
        dto.setCodeBanque(c.getCodeBanque());
        dto.setNumeroCompte(c.getNumeroCompte());
        dto.setNomClient(c.getNomClient());
        dto.setMontant(c.getMontant());
        dto.setCertifie(c.getCertifie());
        return dto;
    }

    // Transformation de ChequeDTO vers l'entité Cheque
    public static Cheque toEntity(ChequeDTO dto) {
        if (dto == null) return null;

        Cheque c = new Cheque();
        c.setNumero(dto.getNumero());
        c.setCodeBanque(dto.getCodeBanque());
        c.setNumeroCompte(dto.getNumeroCompte());
        c.setNomClient(dto.getNomClient());
        c.setMontant(dto.getMontant());
        c.setCertifie(dto.getCertifie());
        return c;
    }
}
