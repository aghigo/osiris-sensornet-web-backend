package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.service.SensorService;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorController {

    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @RequestMapping(value = "/sensors/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        SensorCoTo sensorCoTo = this.sensorService.getRandom();
        return ResponseEntity.ok(sensorCoTo);
    }

    @RequestMapping(value = "/networks/{networkId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId) {
        List<SensorCoTo> sensorCoToList = this.sensorService.getAllByNetworkId(networkId);
        return ResponseEntity.ok(sensorCoToList);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByCollectorIdAndNetworkId(@PathVariable String networkId, @PathVariable String collectorId) {
        List<SensorCoTo> sensorCoToList = this.sensorService.getAllByCollectorIdAndNetworkId(networkId, collectorId);
        return ResponseEntity.ok(sensorCoToList);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors/{sensorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getByCollectorIdAndNetworkId(@PathVariable String networkId, @PathVariable String collectorId, @PathVariable String sensorId) {
        SensorCoTo sensorCoTo = this.sensorService.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId);
        return ResponseEntity.ok(sensorCoTo);
    }



}
