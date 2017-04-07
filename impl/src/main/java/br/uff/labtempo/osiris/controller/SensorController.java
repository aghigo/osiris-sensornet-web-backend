package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.service.SensorService;
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
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @RequestMapping(value = "/sensors/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        return ResponseEntity.ok(this.sensorService.getRandom());
    }

    @RequestMapping(value = "/sensors/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<SensorResponse> sensorResponseList = null;
        try {
            sensorResponseList = this.sensorService.getAll();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponseList == null || sensorResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponseList);
    }

    @RequestMapping(value = "/sensors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsSensors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/networks/{networkId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId) {
        List<SensorResponse> sensorResponseList;
        try {
            sensorResponseList = this.sensorService.getAllByNetworkId(networkId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponseList == null || sensorResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponseList);
    }

    @RequestMapping(value = "/networks/{networkId}/sensors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdSensors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByCollectorIdAndNetworkId(@PathVariable String networkId, @PathVariable String collectorId) {
        List<SensorResponse> sensorResponseList;
        try {
            sensorResponseList = this.sensorService.getAllByCollectorIdAndNetworkId(networkId, collectorId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponseList == null || sensorResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponseList);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectorIdSensors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors/{sensorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getByCollectorIdAndNetworkId(@PathVariable String networkId, @PathVariable String collectorId, @PathVariable String sensorId) {
        SensorResponse sensorResponse;
        try {
            sensorResponse = this.sensorService.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponse);
    }

    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors/{sensorId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectorIdSensorId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
