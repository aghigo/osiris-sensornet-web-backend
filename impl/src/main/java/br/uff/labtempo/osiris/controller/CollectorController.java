package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/collector")
public class CollectorController {

    private CollectorService collectorService;

    @Autowired
    private CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.collectorService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok(this.collectorService.getAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post() {
        return null;
    }
}
