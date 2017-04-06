package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.model.request.LinkRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LinkController {

    @RequestMapping(value = "/links", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return null;
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String linkId) {
        return null;
    }

    @RequestMapping(value = "/links", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid LinkRequest linkRequest) {
        return null;
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable String linkId, @Valid LinkRequest linkRequest) {
        return null;
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String linkId) {
        return null;
    }

    @RequestMapping(value = "/links", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return null;
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options(@PathVariable String linkId) {
        return null;
    }
}