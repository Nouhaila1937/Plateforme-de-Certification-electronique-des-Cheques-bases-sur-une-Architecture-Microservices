package org.example.commercantservice.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChequeCertifiedEvent {
    private Long chequeId;
    private String certificationReference;
    private LocalDateTime timestamp;
    private String eventType = "ChequeCertified";
}