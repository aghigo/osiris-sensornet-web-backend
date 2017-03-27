package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.mapper.SampleMapper;
import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
        SampleResponse sampleResponse = this.sampleService.getRandom();
        return ResponseEntity.ok(sampleResponse);
    }

    @RequestMapping(value ="/samples", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody(required = true) @Valid SampleRequest sampleRequest) throws URISyntaxException {
        return ResponseEntity.ok(SampleMapper.toCoTo(sampleRequest));
    }

}
