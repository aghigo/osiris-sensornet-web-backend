package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

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

    @RequestMapping(value = "/collectors/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/collectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<CollectorResponse> collectorResponseList = null;
        try {
            collectorResponseList = this.collectorService.getAll();
        } catch (AbstractRequestException e) {
            ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (AbstractClientRuntimeException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
        if(collectorResponseList == null || collectorResponseList.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(collectorResponseList);
    }

    @RequestMapping(value = "/collectors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsCollectors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId) {
        List<CollectorResponse> collectorResponseList = null;
        try {
            collectorResponseList = this.collectorService.getAllByNetworkId(networkId);
        } catch (AbstractRequestException e) {
            ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(collectorResponseList == null || collectorResponseList.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(collectorResponseList);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId, @PathVariable String collectorId) {
        CollectorResponse collectorResponse = null;
        try {
            collectorResponse = this.collectorService.getByNetworkId(networkId, collectorId);
        } catch (AbstractRequestException e) {
            ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()
            );
        }
        if(collectorResponse == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(collectorResponse);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsCollectorId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
