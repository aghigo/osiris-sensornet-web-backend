package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.service.LinkService;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class responsible to provide REST endpoints
 * to Control and manage Link sensors from VirtualSensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * Get all available Link sensors from VirtualSensorNet module
     * @return ResponseEntity of List<LinkResponse> (List of all available Link sensors from VirtualSensorNet module)
     */
    @RequestMapping(value = "/links", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "false") @Valid boolean count) {
        List<LinkResponse> linkResponseList;
        try {
            linkResponseList = this.linkService.getAll();
            if(count) {
                return ResponseEntity.status(HttpStatus.OK).body(linkResponseList.size());
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(linkResponseList);
            }
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of all available HTTP methods for the /links endpoint
     * @return list of available HTTP methods (ResponseEntity with allows Headers)
     */
    @RequestMapping(value = "/links", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    /**
     * Create a new Link sensor on VirtualSensorNet module
     * @param linkRequest (data required to create a new Link sensor)
     * @return URI containing the new Link location
     */
    @RequestMapping(value = "/links", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody @Valid LinkRequest linkRequest) {
        URI uri;
        try {
            uri = this.linkService.create(linkRequest);
            return ResponseEntity.created(uri).build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a Link sensor created randomly at runtime.
     * This is for mock purposes. Not persisted.
     * @return
     */
    @RequestMapping(value = "/links/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        LinkVsnTo linkVsnTo;
        try {
            linkVsnTo = this.linkService.getRandom();
            return ResponseEntity.status(HttpStatus.OK).body(linkVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a Link sensor created based on an existing SensorNet sensor.
     * This is for mock purposes. Not persisted.
     * @return
     */
    @RequestMapping(value = "/links/mock/{sensorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRandomBySensor(@PathVariable @Valid String sensorId) {
        LinkVsnTo linkVsnTo;
        try {
            linkVsnTo = this.linkService.getRandomById(sensorId);
            return ResponseEntity.status(HttpStatus.OK).body(linkVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of all available HTTP methods of the /links/mock endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/links/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

        @RequestMapping(value = "/links/{linkId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String linkId) {
        LinkResponse linkResponse;
        try {
            linkResponse = this.linkService.getById(linkId);
            return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Updates an existing Link sensor from VirtualSensorNet
     * @param linkId (Link id that will be updated)
     * @param linkRequest (data required to update the Link sensor)
     * @return
     */
    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable String linkId, @RequestBody @Valid LinkRequest linkRequest) {
        try {
            this.linkService.update(linkId, linkRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Removes an existing Link sensor from VirtualSensorNet module
     * @param linkId (Link sensor to be removed)
     * @return
     */
    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String linkId) {
        try {
            this.linkService.delete(linkId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of all available HTTP methods of the /links/{linkId} endpoint
     * @param linkId
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options(@PathVariable String linkId) {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }
}