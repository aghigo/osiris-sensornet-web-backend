package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.FunctionRequest;
import br.uff.labtempo.osiris.service.FunctionService;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
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
 * Controller class to serve REST endpoints
 * to control and manage functions from the OSIRIS FunctionFactory module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/functions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FunctionController {

    @Autowired
    private FunctionService functionService;

    /**
     * Get a list of all available Functions modules interfaces
     * @return ResponseEntity with status OK and body with List<InterfaceFnTo>
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllInterfaces() {
        try {
            List<InterfaceFnTo> interfaceFnToList = this.functionService.getAllInterfaces();
            return ResponseEntity.status(HttpStatus.OK).body(interfaceFnToList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a FunctionFactory interface based on the function name
     * @param functionName
     * @return ResponseEntity with status 200 OK and body with an InterfaceFnTo (FunctionFactory interface)
     * @throws AbstractRequestException
     */
    @RequestMapping(value = "/{functionName}", method = RequestMethod.GET)
    public ResponseEntity<?> getInterface(@PathVariable String functionName) throws AbstractRequestException {
        try {
            InterfaceFnTo interfaceFnTo = this.functionService.getInterfaceByName(functionName);
            return ResponseEntity.status(HttpStatus.OK).body(interfaceFnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Creates a new FunctionFactory module on OSIRIS
     * @param functionRequest
     * @return ResponseEntity with status 201 Created and body with URI of the created resource
     * @throws AbstractRequestException
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> getInterface(@RequestBody @Valid FunctionRequest functionRequest) throws AbstractRequestException {
        try {
            this.functionService.createFunctionModule(functionRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
