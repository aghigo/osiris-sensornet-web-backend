package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.service.CollectorService;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CollectorController {

    private CollectorService collectorService;

    @Autowired
    private CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @RequestMapping(value = "/collectors/mock", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRandom() {
        CollectorCoTo collectorCoTo = this.collectorService.getRandom();
        return ResponseEntity.ok().body(collectorCoTo);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId) {
        List<CollectorCoTo> collectorCoTo = this.collectorService.getAllByNetworkId(networkId);
        return ResponseEntity.ok().body(collectorCoTo);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId, @PathVariable String collectorId) {
        CollectorCoTo collectorCoTo = this.collectorService.getByNetworkId(networkId, collectorId);
        return ResponseEntity.ok().body(collectorCoTo);
    }
}
