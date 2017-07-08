package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.response.ErrorResponse;
import br.uff.labtempo.osiris.service.VirtualSensorService;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@CrossOrigin
@RequestMapping(value = "/virtualsensornet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VirtualSensorController {

    @Autowired
    private VirtualSensorService virtualSensorService;

    /**
     * Get a list of all available Virtual Sensors from VirtualSensorNet module
     * @return List of VirtualSensors
     */
    @RequestMapping(value = "/virtualsensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "false") @Valid boolean count) {
        try {
            List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorService.getAll();
            if(count) {
                return ResponseEntity.ok(virtualSensorVsnToList.size());
            } else {
                return ResponseEntity.ok(virtualSensorVsnToList);
            }
        } catch (AbstractRequestException e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(e.getStatusCode().toCode()).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get a list of all available HTTP methods of the /virtualsensor endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/virtualsensors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> getOptions() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get a specific VirtualSensor from VirtualSensorNet module based on a unique Id
     * @param virtualSensorId
     * @return VirtualSensorVsnTo
     */
    @RequestMapping(value = "/virtualsensors/{virtualSensorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String virtualSensorId) {
        try {
            VirtualSensorVsnTo virtualSensorVsnTo = this.virtualSensorService.getById(virtualSensorId);
            return ResponseEntity.ok(virtualSensorVsnTo);
        } catch (AbstractRequestException e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(e.getStatusCode().toCode()).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get a list of all available HTTP methods of the /virtualsensor/{virtualsensorId} endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/virtualsensors/{virtualSensorId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> getOptionsById() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
