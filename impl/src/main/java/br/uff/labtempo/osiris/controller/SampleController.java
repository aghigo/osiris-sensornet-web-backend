package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.osiris.model.request.sample.SampleRequest;
import br.uff.labtempo.osiris.service.SampleService;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SampleController {

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @RequestMapping(value ="/samples/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        SampleCoTo sampleCoTo = this.sampleService.getRandom();
        return ResponseEntity.ok(sampleCoTo);
    }

    @RequestMapping(value = "/samples", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody SampleRequest sampleRequest) throws URISyntaxException {
        if(sampleRequest == null) {
            return ResponseEntity.badRequest().body(new BadRequestException("Error: SampleCoTo provided is null."));
        }
        URI uri = this.sampleService.notify(sampleRequest);
        return ResponseEntity.created(uri).build();
    }
}
