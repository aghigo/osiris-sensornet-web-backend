package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.service.SensorService;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SensorCoTo> getRandom() {
        SensorCoTo sensorCoTo = this.sensorService.getRandom();
        return ResponseEntity.ok(sensorCoTo);
    }
}
