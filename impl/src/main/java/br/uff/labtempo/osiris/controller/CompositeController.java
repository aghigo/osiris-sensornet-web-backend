package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.CompositeRequest;
import br.uff.labtempo.osiris.model.response.CompositeResponse;
import br.uff.labtempo.osiris.model.response.ErrorResponse;
import br.uff.labtempo.osiris.service.CompositeService;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class with REST endpoints
 * to control Composite sensors from VirtualSensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CompositeController {

    @Autowired
    private CompositeService compositeService;

    /**
     * Get all Composite sensors from VirtualSensorNet module
     * @return List with all available Composite sensors from VirtualSensorNet module
     */
    @RequestMapping(value = "/composites", method = RequestMethod.GET)
    public ResponseEntity<?> doGetAll(@RequestParam(required = false, defaultValue = "false") boolean count) {
        try {
            List<CompositeResponse> compositeResponseList = this.compositeService.getAll();
            if(count) {
                return ResponseEntity.ok(compositeResponseList.size());
            } else {
                return ResponseEntity.ok(compositeResponseList);
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
     * Creates a new Composite sensor on VirtualSensorNet module
     * @param compositeRequest (data required to create a new Composite sensor)
     * @return URI with the new Composite location
     */
    @RequestMapping(value = "/composites", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody @Valid CompositeRequest compositeRequest) {
        try {
            URI uri = this.compositeService.create(compositeRequest);
            return ResponseEntity.created(uri).build();
        } catch (AbstractRequestException e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(e.getStatusCode().toCode()).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get a list of available HTTP methods for the /composites endpoint
     * @return list of available HTTP methods
     */
    @RequestMapping(value = "/composites", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsComposites() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    /**
     * Get a specific Composite sensor based on unique Id
     * @param compositeId
     * @return Composite Sensor (CompositeResponse)
     */
    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.GET)
    public ResponseEntity<?> doGetById(@PathVariable String compositeId) {
        try {
            CompositeResponse compositeResponse = this.compositeService.getById(compositeId);
            return ResponseEntity.ok(compositeResponse);
        } catch (AbstractRequestException e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(e.getStatusCode().toCode()).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Updates an existing Composite sensor from VirtualSensorNet module
     * @param compositeId (Composite sensor that will be updated)
     * @param compositeRequest (data to update the Composite sensor)
     * @return
     */
    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.PUT)
    public ResponseEntity<?> doPut(@PathVariable String compositeId, @RequestBody @Valid CompositeRequest compositeRequest) {
        try {
            this.compositeService.update(compositeId, compositeRequest);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(e.getStatusCode().toCode()).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Removes an existing Composite sensor from VirtualSensorNet module
     * @param compositeId
     * @return
     */
    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> doDelete(@PathVariable String compositeId) {
        try {
            this.compositeService.delete(compositeId);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(e.getStatusCode().toCode()).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get all available HTTP methods for the /composites/{compositeId} endpoint
     * @return
     */
    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsCompositeId() {
        return allows(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }

    /**
     * Creates a new Composite sensor randomly.
     * This acts as a mock. The Composite is not persisted.
     * @return CompositeVsnTo
     */
    @RequestMapping(value = "/composites/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getMock() {
        try {
            return ResponseEntity.ok(this.compositeService.getRandom());
        } catch (AbstractRequestException e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(e.getStatusCode().toCode()).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get a list of available HTTP methods for the /composites/mock endpoint
     * @return List of available HTTP methods
     */
    @RequestMapping(value = "/composites/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

}
