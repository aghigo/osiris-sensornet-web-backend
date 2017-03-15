package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.service.CollectorService;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/collector")
public class CollectorController {

    private CollectorService collectorService;

    @Autowired
    private CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CollectorCoTo> getRandom() {
        CollectorCoTo collectorCoTo = this.collectorService.getRandom();
        return ResponseEntity.ok().body(collectorCoTo);
    }
}
