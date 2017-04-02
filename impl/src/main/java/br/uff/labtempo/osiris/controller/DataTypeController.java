package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.service.DataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataTypeController {

    @Autowired
    private DataTypeService dataTypeService;

    @RequestMapping(value = "/datatypes/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        return ResponseEntity.ok(this.dataTypeService.getRandom());
    }

    @RequestMapping(value = "/datatypes", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<DataTypeResponse> dataTypeResponseList;
        try {
            dataTypeResponseList = this.dataTypeService.getAll();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(dataTypeResponseList);
    }

    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String dataTypeId) {
        DataTypeResponse dataTypeResponse;
        try {
            dataTypeResponse = this.dataTypeService.getById(dataTypeId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok(dataTypeResponse);
    }

    @RequestMapping(value = "/datatypes", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody @Valid DataTypeRequest dataTypeRequest) {
        URI uri = null;
        try {
            uri = this.dataTypeService.create(dataTypeRequest);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }
}
