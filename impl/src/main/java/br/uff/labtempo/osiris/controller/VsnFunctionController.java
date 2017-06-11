package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.VsnFunctionRequest;
import br.uff.labtempo.osiris.service.VsnFunctionService;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Controller class that provides a REST API
 * that handles HTTP requests for GET/POST/PUT/DELETE
 * Functions from VirtualSensorNet module
 * @see br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo
 * @author andre.ghigo
 * @since 10/07/2017
 * @version 1.0.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VsnFunctionController {

    @Autowired
    private VsnFunctionService vsnFunctionService;

    /**
     * Get all Functions from VirtualSensorNet module
     * @see FunctionVsnTo
     * @param count
     * @return List<FunctionVsnTo> (if count = true), int total of the list (if count = true)
     */
    @RequestMapping(value = "/functions", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "false") boolean count) {
        try {
            List<FunctionVsnTo> functionVsnToList = this.vsnFunctionService.getAllFunctionsFromVirtualSensorNet();
            if(count) {
                return ResponseEntity.ok(functionVsnToList.size());
            } else {
                return ResponseEntity.ok(functionVsnToList);
            }
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get all Functions from VirtualSensorNet module
     * @see FunctionVsnTo
     * @return FunctionVsnTo
     */
    @RequestMapping(value = "/functions/{functionId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable long functionId) {
        try {
            FunctionVsnTo functionVsnTo = this.vsnFunctionService.getFunctionFromVirtualSensorNetById(functionId);
            return ResponseEntity.ok(functionVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Creates a new function into VirtualSensorNet module
     * @see FunctionVsnTo
     * @return ResponseEntity with status 201 Created, empty body and Location header with
     * new URI of the created resource.
     */
    @RequestMapping(value = "/functions", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody @Valid VsnFunctionRequest vsnFunctionRequest) {
        try {
            URI uri = this.vsnFunctionService.createFunctionOnVirtualSensorNet(vsnFunctionRequest);
            return ResponseEntity.created(uri).build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Updates an existing function into VirtualSensorNet module
     * @see FunctionVsnTo
     * @return ResponseEntity with status 200 OK and empty body
     */
    @RequestMapping(value = "/functions/{functionId}", method = RequestMethod.PUT)
    public ResponseEntity<?> post(@PathVariable long functionId, @RequestBody @Valid VsnFunctionRequest vsnFunctionRequest) {
        try {
            this.vsnFunctionService.updateFunctionFromVirtualSensorNetById(functionId, vsnFunctionRequest);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Updates an existing function into VirtualSensorNet module
     * @see FunctionVsnTo
     * @return ResponseEntity with status 200 OK and empty body
     */
    @RequestMapping(value = "/functions/{functionId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> post(@PathVariable long functionId) {
        try {
            this.vsnFunctionService.deleteFunctionFromVirtualSensorNetById(functionId);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
