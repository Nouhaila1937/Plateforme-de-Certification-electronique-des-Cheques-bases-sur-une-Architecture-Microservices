package org.example.commercantservice.command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectChequeCommand {
    private Long chequeId;
    private String raison;
}
