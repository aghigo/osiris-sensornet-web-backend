package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.request.network.NetworkRequest;
import br.uff.labtempo.osiris.service.NetworkService;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/network", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NetworkController {

    private NetworkService networkService;

    @Autowired
    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @RequestMapping(value = "/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        NetworkCoTo networkCoTo = this.networkService.getRandom();
        return ResponseEntity.ok(networkCoTo);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll() throws BadRequestException {
        List<NetworkCoTo> networkCoTos = this.networkService.getAll();
        return ResponseEntity.ok(networkCoTos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable(value = "id") String id) throws BadRequestException {
        NetworkCoTo networkCoTo = this.networkService.getById(id);
        return ResponseEntity.ok(networkCoTo);
    }

}
