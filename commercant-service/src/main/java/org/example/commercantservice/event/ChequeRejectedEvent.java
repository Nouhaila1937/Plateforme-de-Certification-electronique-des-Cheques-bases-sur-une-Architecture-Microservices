package org.example.commercantservice.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChequeRejectedEvent {
    private Long chequeId;
    private String raison;
    private LocalDateTime timestamp;
    private String eventType = "ChequeRejected";
}