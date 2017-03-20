package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.service.SampleService;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sample", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SampleController {

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @RequestMapping(value ="/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        SampleCoTo sampleCoTo = this.sampleService.getRandom();
        return ResponseEntity.ok(sampleCoTo);
    }
}
