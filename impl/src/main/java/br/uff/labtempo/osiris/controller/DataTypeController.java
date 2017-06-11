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

/**
 * Controller class to serve endpoints to manage DataTypes from VirtualSensorNet module
 * @see DataTypeRequest
 * @see br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo
 * @see DataTypeResponse
 * @author andre.ghigo
 * @version 1.0
 * @since 06/05/2017
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataTypeController {

    @Autowired
    private DataTypeService dataTypeService;

    /**
     * Return a randomly created DataType mocked object
     * @see br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo
     * @return DataTypeVsnTo
     */
    @RequestMapping(value = "/datatypes/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        return ResponseEntity.ok(this.dataTypeService.getRandom());
    }

    /**
     * Get a list of all available HTTP Methods for the /datatypes/mock endpoint
     * @return List of HTTP Methods
     */
    @RequestMapping(value = "/datatypes/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get a list of All available DataTypes from VirtualSensorNet module
     * @see DataTypeResponse
     * @see br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo
     * @return List of DataTypeVsnTo
     */
    @RequestMapping(value = "/datatypes", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "false") @Valid boolean count) {
        List<DataTypeResponse> dataTypeResponseList;
        try {
            dataTypeResponseList = this.dataTypeService.getAll();
            if(count) {
                return ResponseEntity.ok(dataTypeResponseList.size());
            } else {
                return ResponseEntity.ok(dataTypeResponseList);
            }
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Creates a new DataType on VirtualSensorNet module
     * @param dataTypeRequest
     * @return URI with the new created DataType location
     */
    @RequestMapping(value = "/datatypes", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody @Valid DataTypeRequest dataTypeRequest) {
        URI uri = null;
        try {
            uri = this.dataTypeService.create(dataTypeRequest);
            return ResponseEntity.created(uri).build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * List all available HTTP Methods for the /datatypes endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/datatypes", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsDatatypes() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    /**
     * Get a specific DataType from VirtualSensorNet based on its unique Id
     * @param dataTypeId
     * @return DataTypeVsnTo
     */
    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable long dataTypeId) {
        DataTypeResponse dataTypeResponse;
        try {
            dataTypeResponse = this.dataTypeService.getById(dataTypeId);
            return ResponseEntity.ok(dataTypeResponse);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

    }

    /**
     * Updates an existing DataType from VirtualSensorNet module
     * @param dataTypeId
     * @param dataTypeRequest
     * @return Empty HTTP response with status OK, if success.
     */
    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.PUT)
    public ResponseEntity<?> doPut(@PathVariable String dataTypeId, @RequestBody @Valid DataTypeRequest dataTypeRequest) {
        DataTypeResponse dataTypeResponse;
        try {
            this.dataTypeService.update(dataTypeId, dataTypeRequest);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Removes an existing DataType from VirtualSensorNet module
     * @param dataTypeId
     * @return empty HTTP response with OK status, if success.
     */
    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> doDelete(@PathVariable String dataTypeId) {
        DataTypeResponse dataTypeResponse;
        try {
            this.dataTypeService.delete(dataTypeId);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * List all available HTTP methods for the /datatypes/{dataTypeId} endpoint
     * @return List of available HTTP methods
     */
    @RequestMapping(value = "/datatypes/{dataTypeId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsDatatypeId() {
        return allows(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }
}
