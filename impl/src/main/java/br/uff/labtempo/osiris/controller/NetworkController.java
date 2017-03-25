package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.service.NetworkService;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        NetworkResponse networkResponse = this.networkService.getRandom();
        return ResponseEntity.ok(networkResponse);
    }

    @RequestMapping(value = "/networks", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() throws BadRequestException {
        List<NetworkResponse> networkResponseList = this.networkService.getAll();
        return ResponseEntity.ok(networkResponseList);
    }

    @RequestMapping(value = "/networks/{networkId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable String networkId) throws BadRequestException {
        NetworkResponse networkResponse = this.networkService.getById(networkId);
        return ResponseEntity.ok(networkResponse);
    }

}
