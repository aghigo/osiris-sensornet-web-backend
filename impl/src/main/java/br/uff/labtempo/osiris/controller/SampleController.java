package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.ErrorResponse;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.service.SampleService;
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

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

@RestController
@CrossOrigin
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SampleController {
    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @RequestMapping(value ="/samples/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        try {
            SampleResponse sampleResponse = this.sampleService.getRandom();
            return ResponseEntity.ok(sampleResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @RequestMapping(value = "/samples/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value ="/samples", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody(required = true) @Valid SampleRequest sampleRequest) throws URISyntaxException {
        try {
            URI uri = this.sampleService.create(sampleRequest);
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            ErrorResponse errorResponse = OmcpUtil.formatErrorResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @RequestMapping(value = "/samples", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsSamples() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}