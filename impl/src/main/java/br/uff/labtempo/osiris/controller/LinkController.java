package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

@RestController
@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping(value = "/links", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<LinkResponse> linkResponseList;
        try {
            linkResponseList = this.linkService.getAll();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(linkResponseList == null || linkResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(linkResponseList);
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String linkId) {
        LinkResponse linkResponse;
        try {
            linkResponse = this.linkService.getById(linkId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(linkResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(linkResponse);
    }

    @RequestMapping(value = "/links", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid LinkRequest linkRequest) {
        URI uri;
        try {
            uri = this.linkService.create(linkRequest);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable String linkId, @Valid LinkRequest linkRequest) {
        try {
            this.linkService.update(linkId, linkRequest);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String linkId) {
        try {
            this.linkService.delete(linkId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/links", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/links/{linkId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options(@PathVariable String linkId) {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }
}