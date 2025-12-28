package org.example.commercantservice.command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkChequeAsCertifiedCommand {
    private Long chequeId;
    private String certificationReference;
}