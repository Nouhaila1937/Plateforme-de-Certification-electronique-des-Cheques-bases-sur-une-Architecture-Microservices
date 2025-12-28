package org.example.commercantservice.Web;

import org.springframework.web.bind.annotation.*;
import org.example.commercantservice.command.CreateChequeCommand;
import org.example.commercantservice.command.RequestChequeCertificationCommand;
import org.example.commercantservice.command.ChequeCommandHandler;
import org.example.commercantservice.query.ChequeQueryService;
import org.example.commercantservice.query.ChequeReadModel;

import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/cheques")
public class ChequeController {

    private final ChequeCommandHandler commandHandler;
    private final ChequeQueryService queryService;

    public ChequeController(ChequeCommandHandler commandHandler,
                            ChequeQueryService queryService) {
        this.commandHandler = commandHandler;
        this.queryService = queryService;
    }

    // ========== COMMANDS ==========

    @PostMapping
    public ResponseEntity<Long> createCheque(@RequestBody CreateChequeCommand command) {
        try {
            Long chequeId = commandHandler.handle(command);
            return ResponseEntity.ok(chequeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/request-certification")
    public ResponseEntity<Void> requestCertification(@PathVariable Long id) {
        try {
            RequestChequeCertificationCommand command = new RequestChequeCertificationCommand(id);
            commandHandler.handle(command);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ========== QUERIES ==========

    @GetMapping
    public ResponseEntity<List<ChequeReadModel>> getAllCheques() {
        return ResponseEntity.ok(queryService.getAllCheques());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChequeReadModel> getChequeById(@PathVariable Long id) {
        return queryService.getChequeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ChequeReadModel>> getChequesByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(queryService.getChequesByStatut(statut));
    }
}