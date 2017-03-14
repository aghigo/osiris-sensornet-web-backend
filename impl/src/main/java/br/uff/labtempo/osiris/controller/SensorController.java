package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sensor")
public class SensorController {

    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.sensorService.getAll());
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(this.sensorService.getAll());
    }
}
