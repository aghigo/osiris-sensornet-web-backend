package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.service.FunctionService;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/virtualsensornet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FunctionController {
    @Autowired
    private FunctionService functionService;

    @RequestMapping(value = "/functions")
    public ResponseEntity<?> getByName(String functionName) {
        try {
            FunctionVsnTo functionVsnTo = this.functionService.getByName(functionName);
            return ResponseEntity.status(HttpStatus.OK).body(functionVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getInterface(String functionName) throws AbstractRequestException {
        try {
            InterfaceFnTo interfaceFnTo = this.functionService.getInterface(functionName);
            return ResponseEntity.status(HttpStatus.OK).body(interfaceFnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getFromVirtualSensorNet(String functionName) throws AbstractRequestException {
        try {
            FunctionVsnTo functionVsnTo = this.functionService.getFromVirtualSensorNet(functionName);
            return ResponseEntity.status(HttpStatus.OK).body(functionVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
