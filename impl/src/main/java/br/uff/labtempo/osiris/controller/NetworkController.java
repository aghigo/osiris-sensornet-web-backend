package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.request.network.NetworkRequest;
import br.uff.labtempo.osiris.service.NetworkService;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/network")
public class NetworkController {

    private NetworkService networkService;

    @Autowired
    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @RequestMapping(value = "/mock", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NetworkCoTo> getRandom() {
        NetworkCoTo networkCoTo = this.networkService.getRandom();
        return ResponseEntity.ok(networkCoTo);
    }
}
