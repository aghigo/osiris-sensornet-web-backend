package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
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

@RestController
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NetworkController {
    private NetworkService networkService;

    @Autowired
    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @RequestMapping(value = "/networks/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        NetworkCoTo networkCoTo = this.networkService.getRandom();
        return ResponseEntity.ok(networkCoTo);
    }

    @RequestMapping(value = "/networks/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/networks", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<NetworkResponse> networkResponseList;
        try {
            networkResponseList = this.networkService.getAll();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(networkResponseList);
    }

    @RequestMapping(value = "/networks", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworks() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/networks/{networkId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String networkId) throws AbstractRequestException, AbstractClientRuntimeException {
        NetworkResponse networkResponse = null;
        try {
            networkResponse = this.networkService.getById(networkId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(networkResponse);
    }

    @RequestMapping(value = "/networks/{networkId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
