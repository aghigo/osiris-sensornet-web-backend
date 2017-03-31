package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getRandom() {
        return ResponseEntity.ok().body(this.collectorService.getRandom());
    }

    @RequestMapping(value = "/collectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        List<CollectorResponse> collectorResponseList = this.collectorService.getAll();
        return ResponseEntity.ok().body(collectorResponseList);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId) {
        List<CollectorResponse> collectorResponseList = this.collectorService.getAllByNetworkId(networkId);
        return ResponseEntity.ok().body(collectorResponseList);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId, @PathVariable String collectorId) {
        CollectorResponse collectorResponse = this.collectorService.getByNetworkId(networkId, collectorId);
        return ResponseEntity.ok().body(collectorResponse);
    }
}
