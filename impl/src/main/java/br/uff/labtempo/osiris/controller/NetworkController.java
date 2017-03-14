package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.model.generator.NetworkGenerator;
import br.uff.labtempo.osiris.model.request.NetworkRequest;
import br.uff.labtempo.osiris.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/network")
public class NetworkController {
    private NetworkService networkService;

    @Autowired
    public NetworkController(NetworkService networkService){
        this.networkService = networkService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(new NetworkGenerator().generate());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(new NetworkGenerator().generate());
        //return ResponseEntity.ok(this.networkService.getAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody NetworkRequest networkRequest){
        return ResponseEntity.ok().body(networkRequest);
        //return ResponseEntity.status(this.networkService.post(networkRequest)).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable(value = "id") String id, @RequestBody NetworkRequest networkRequest){
        return ResponseEntity.ok("teste put network");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id){
        return ResponseEntity.ok("teste delete network");
    }

    @RequestMapping(value = "/notify/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> notify(@PathVariable(value = "id") String id, @RequestBody NetworkRequest networkRequest){
        return ResponseEntity.ok("teste notify network");
    }
}
