package org.example.commercantservice.Web;

import org.example.commercantservice.Entities.Cheque;
import org.example.commercantservice.Service.ChequeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cheques")
@CrossOrigin(origins = "http://localhost:4200")
public class ChequeController {

    private final ChequeService service;

    public ChequeController(ChequeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cheque> getAll() { return service.getAll(); }

    @PostMapping
    public Cheque create(@RequestBody Cheque c) { return service.save(c); }

    @PutMapping("/certify/{id}")
    public Cheque certify(@PathVariable Long id) { return service.certifyCheque(id); }
}
