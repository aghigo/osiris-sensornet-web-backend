package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.service.FunctionService;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to serve REST endpoints
 * to control and manage functions from the OSIRIS Function module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FunctionController {
    @Autowired
    private FunctionService functionService;

    /**
     * Get a list of all available Functions names
     * @return ResponseEntity with List<String> (list of functionNames)
     */
    @RequestMapping(value = "/functions", method = RequestMethod.GET)
    public ResponseEntity<?> getAvailableFunctionList() {
        try {
            List<String> availableFunctionList = this.functionService.getAvailableFunctionsUri();
            return ResponseEntity.status(HttpStatus.OK).body(availableFunctionList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a list of all available Functions interfaces
     * @see InterfaceFnTo
     * @return ResponseEntity with List<InterfaceFnTo>
     */
    @RequestMapping(value = "/functions/interface", method = RequestMethod.GET)
    public ResponseEntity<?> getAvailableFunctionInterfaceList() {
        try {
            List<InterfaceFnTo> availableFunctionInterfaceList = this.functionService.getAvailableFunctionsInterface();
            return ResponseEntity.status(HttpStatus.OK).body(availableFunctionInterfaceList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a Function interface based on the function name
     * @param functionName
     * @return InterfaceFnTo (Function interface)
     * @throws AbstractRequestException
     */
    @RequestMapping(value = "/functions/{functionName}/interface", method = RequestMethod.GET)
    public ResponseEntity<?> getInterface(@PathVariable String functionName) throws AbstractRequestException {
        try {
            InterfaceFnTo interfaceFnTo = this.functionService.getInterface(functionName);
            return ResponseEntity.status(HttpStatus.OK).body(interfaceFnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a function from VirtualSensorNet module based on its id
     * @param functionId
     * @return FunctionVsnTo (Function from VirtualSensorNet module)
     * @throws AbstractRequestException
     */
    @RequestMapping(value = "/virtualsensornet/functions/{functionId}", method = RequestMethod.GET)
    public ResponseEntity<?> getFromVirtualSensorNet(@PathVariable long functionId) throws AbstractRequestException {
        try {
            FunctionVsnTo functionVsnTo = this.functionService.getFromVirtualSensorNet(functionId);
            return ResponseEntity.status(HttpStatus.OK).body(functionVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get all functions from VirtualSensorNet module
     * @return List<FunctionVsnTo>
     * @throws AbstractRequestException
     */
    @RequestMapping(value = "/virtualsensornet/functions", method = RequestMethod.GET)
    public ResponseEntity<?> getFromVirtualSensorNet() throws AbstractRequestException {
        try {
            List<FunctionVsnTo> functionVsnToList = this.functionService.getAllFromVirtualSensorNet();
            return ResponseEntity.status(HttpStatus.OK).body(functionVsnToList);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
