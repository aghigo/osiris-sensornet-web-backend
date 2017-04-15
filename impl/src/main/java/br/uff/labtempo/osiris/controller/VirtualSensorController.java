package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.service.VirtualSensorService;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class that provides REST endpoint
 * to control and manage VirtualSensors from VirtualSensorNet module
 * A VirtualSensor (a.k.a vsensor) is an abstraction of VirtualSensorNet sensors types (Link, composite, blending)
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/virtualsensornet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VirtualSensorController {

    @Autowired
    private VirtualSensorService virtualSensorService;

    /**
     * Get a list of all available Virtual Sensors from VirtualSensorNet module
     * @return List of VirtualSensors
     */
    @RequestMapping(value = "/virtualsensor", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        try {
            List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorService.getAll();
            return ResponseEntity.ok(virtualSensorVsnToList);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Get a list of all available HTTP methods of the /virtualsensor endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/virtualsensor", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> getOptions() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get a specific VirtualSensor from VirtualSensorNet module based on a unique Id
     * @param virtualSensorId
     * @return VirtualSensorVsnTo
     */
    @RequestMapping(value = "/virtualsensor/{virtualSensorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String virtualSensorId) {
        try {
            VirtualSensorVsnTo virtualSensorVsnTo = this.virtualSensorService.getById(virtualSensorId);
            return ResponseEntity.ok(virtualSensorVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Get a list of all available HTTP methods of the /virtualsensor/{virtualsensorId} endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/virtualsensor/{virtualSensorId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> getOptionsById() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
