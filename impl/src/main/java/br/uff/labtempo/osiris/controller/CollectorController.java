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

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class that provide REST endpoints
 * to control Collectors from SensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CollectorController {
    private CollectorService collectorService;

    @Autowired
    private CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    /**
     * Creates a new Collector (CollectorCoTo object) randomly.
     * This acts as a mock. Collector is not persisted.
     * @return CollectorCoTo
     */
    @RequestMapping(value = "/collectors/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        return ResponseEntity.ok().body(this.collectorService.getRandom());
    }

    /**
     * Get a list of available HTTP methods for the /collectors/mock endpoint
     * @return list of available HTTP methods
     */
    @RequestMapping(value = "/collectors/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get all Collectors from SensorNet module
     * @return List<CollectorResponse> List of all available Collectors from SensorNet
     */
    @RequestMapping(value = "/collectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "false") @Valid boolean count) {
        List<CollectorResponse> collectorResponseList = null;
        try {
            collectorResponseList = this.collectorService.getAll();
            if(count) {
                return ResponseEntity.ok().body(collectorResponseList.size());
            } else {
                return ResponseEntity.ok().body(collectorResponseList);
            }
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of available HTTP methods for the /collectors endpoint
     * @return List of available HTTP methods
     */
    @RequestMapping(value = "/collectors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsCollectors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get all Collectors based on a specific Network from SensorNet
     * @param networkId
     * @return List of all collectors of the {networkId} Network
     */
    @RequestMapping(value = "/networks/{networkId}/collectors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId, @RequestParam(required = false, defaultValue = "false") @Valid boolean count) {
        List<CollectorResponse> collectorResponseList = null;
        try {
            collectorResponseList = this.collectorService.getAllByNetworkId(networkId);
            if(count) {
                return ResponseEntity.ok().body(collectorResponseList.size());
            } else {
                return ResponseEntity.ok().body(collectorResponseList);
            }
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of available HTTP methods for the /networks/{networkId}/collectors endpoint
     * @return List of available HTTP methods.
     */
    @RequestMapping(value = "/networks/{networkId}/collectors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get a specific Collector based on collectorId and networkId
     * @param networkId
     * @param collectorId
     * @return CollectorResponse with id {collectorId} from the {networkId} Network
     */
    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId, @PathVariable String collectorId) {
        CollectorResponse collectorResponse = null;
        try {
            collectorResponse = this.collectorService.getByNetworkId(networkId, collectorId);
            return ResponseEntity.ok().body(collectorResponse);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of available HTTP methods for the /networks/{networkId}/collectors/{collectorId} endpoint
     * @return list of available HTTP methods.
     */
    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsCollectorId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
