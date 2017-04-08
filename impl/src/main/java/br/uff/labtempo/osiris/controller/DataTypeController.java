package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.service.DataTypeService;
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

@RestController
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataTypeController {

    @Autowired
    private DataTypeService dataTypeService;

    @RequestMapping(value = "/datatypes/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        return ResponseEntity.ok(this.dataTypeService.getRandom());
    }

    @RequestMapping(value = "/datatypes/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
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

    @RequestMapping(value = "/datatypes", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsDatatypes() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
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

    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.PUT)
    public ResponseEntity<?> doPut(@PathVariable String dataTypeId, @Valid DataTypeRequest dataTypeRequest) {
        DataTypeResponse dataTypeResponse;
        try {
            this.dataTypeService.update(dataTypeId, dataTypeRequest);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> doDelete(@PathVariable String dataTypeId) {
        DataTypeResponse dataTypeResponse;
        try {
            this.dataTypeService.delete(dataTypeId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsDatatypeId() {
        return allows(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }
}
