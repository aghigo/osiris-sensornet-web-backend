package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.service.NetworkService;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class that provides REST endpoints
 * to control and manage Networks from SensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NetworkController {
    private NetworkService networkService;

    @Autowired
    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * Get a Network mock. Create an NetworkCoTo randomly at runtime. Not persisted.
     * @return NetworkCoTo object
     */
    @RequestMapping(value = "/networks/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        NetworkCoTo networkCoTo = this.networkService.getRandom();
        return ResponseEntity.ok(networkCoTo);
    }

    /**
     * Get a List of all available HTTP methods of the /networks/mock endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/networks/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get all available Networks from SensorNet module
     * @return List of NetworkResponse
     */
    @RequestMapping(value = "/networks", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<NetworkResponse> networkResponseList;
        try {
            networkResponseList = this.networkService.getAll();
            return ResponseEntity.ok(networkResponseList);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of all available HTTP methods of the /networks endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/networks", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworks() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get a specific Network from SensorNet module based on its unique Id
     * @param networkId
     * @return NetworkResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    @RequestMapping(value = "/networks/{networkId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String networkId) throws AbstractRequestException, AbstractClientRuntimeException {
        NetworkResponse networkResponse = null;
        try {
            networkResponse = this.networkService.getById(networkId);
            return ResponseEntity.ok(networkResponse);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of all available HTTP methods of the /networks/{networkId} endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/networks/{networkId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
