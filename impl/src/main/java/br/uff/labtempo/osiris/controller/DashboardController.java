package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.response.SensorNetDashboardResponse;
import br.uff.labtempo.osiris.model.response.VirtualSensorNetDashboardResponse;
import br.uff.labtempo.osiris.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by osiris on 25/06/17.
 */
@RestController
@RequestMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "/sensornet")
    public ResponseEntity<?> getSensorNetDashboard() {
        try {
            SensorNetDashboardResponse sensorNetDashboardResponse = this.dashboardService.getSensornetDashboard();
            return ResponseEntity.status(HttpStatus.OK).body(sensorNetDashboardResponse);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/virtualsensornet")
    public ResponseEntity<?> getVirtualSensorNetDashboard() {
        try {
            VirtualSensorNetDashboardResponse virtualSensorNetDashboardResponse = this.dashboardService.getVirtualSensornetDashboard();
            return ResponseEntity.status(HttpStatus.OK).body(virtualSensorNetDashboardResponse);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
