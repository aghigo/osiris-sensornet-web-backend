package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.service.BlendingService;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class that provide REST endpoints
 * to control Blending sensors from VirtualSensorNet Module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BlendingController {

    @Autowired
    private BlendingService blendingService;

    /**
     * Get all the Blending sensors from VirtualSensorNet module
     * @return List of Blending sensors.
     */
    @RequestMapping(value = "/blendings", method = RequestMethod.GET)
    public ResponseEntity<?> doGetAll() {
        try {
            List<BlendingResponse> blendingResponseList = this.blendingService.getAll();
            return ResponseEntity.ok(blendingResponseList);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Creates a new Blending sensor on VirtualSensorNet module
     * @param blendingRequest (Data required to create new Blending)
     * @return URI with new Blending location
     */
    @RequestMapping(value = "/blendings", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody @Valid BlendingRequest blendingRequest) {
        try {
            URI uri = this.blendingService.create(blendingRequest);
            return ResponseEntity.created(uri).build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Return a list of available HTTP methods for /blending endpoint
     * @return List of allowed HTTP methods
     */
    @RequestMapping(value = "/blendings", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptions() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    /**
     * Create a new Blending (BlendingVsnTo object) radomly at runtime and returns it
     * This acts as a mock only. The Blending is not persisted on VirtualSensorNet.
     * @return new mocked BlendingVsnTo object
     */
    @RequestMapping(value = "/blendings/mock", method = RequestMethod.GET)
    public ResponseEntity<?> doGetMock() {
        try {
            BlendingVsnTo blendingVsnTo = this.blendingService.getRandom();
            return ResponseEntity.ok(blendingVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of available HTTP methods for the /blending/mock endpoint
     * @return List of available HTTP methods
     */
    @RequestMapping(value = "/blendings/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Update a existing Blending Sensor
     * @param blendingId (id of the existing blending sensor that will be updated)
     * @param blendingRequest (Blending data to modify during the update)
     * @return
     */
    @RequestMapping(value = "/blendings/{blendingId}", method = RequestMethod.PUT)
    public ResponseEntity<?> doPut(@PathVariable String blendingId, @RequestBody @Valid BlendingRequest blendingRequest) {
        try {
            URI uri = this.blendingService.create(blendingRequest);
            return ResponseEntity.created(uri).build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Removes a existing Blending sensor from VirtualSensorNet
     * @param blendingId
     * @return empty OK HTTP response
     */
    @RequestMapping(value = "/blendings/{blendingId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> doDelete(@PathVariable Long blendingId) {
        try {
            this.blendingService.delete(blendingId);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of available HTTP methods for the /blending/{blendingId} endpoint
     * @return List of available HTTP methods
     */
    @RequestMapping(value = "/blendings/{blendingId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsBlendingId() {
        return allows(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }
}
