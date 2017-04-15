package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.CompositeRequest;
import br.uff.labtempo.osiris.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import java.net.URISyntaxException;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

@RequestMapping(value = "/virtualsensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CompositeController {

    @Autowired
    private CompositeService compositeService;

    @RequestMapping(value = "/composites", method = RequestMethod.GET)
    public ResponseEntity<?> doGetAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.compositeService.getAll());
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/composites", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody @Valid CompositeRequest compositeRequest) {
        try {
            return ResponseEntity.created(this.compositeService.create(compositeRequest)).build();
        } catch (URISyntaxException | AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/composites", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsComposites() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.GET)
    public ResponseEntity<?> doGetById(@PathVariable String compositeId) {
        try {
            return ResponseEntity.ok(this.compositeService.getById(compositeId));
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.PUT)
    public ResponseEntity<?> doPut(@PathVariable String compositeId, @RequestBody @Valid CompositeRequest compositeRequest) {
        try {
            this.compositeService.update(compositeId, compositeRequest);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> doDelete(@PathVariable String compositeId) {
        try {
            this.compositeService.delete(compositeId);
            return ResponseEntity.ok().build();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/composites/{compositeId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsCompositeId() {
        return allows(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/composites/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getMock() {
        try {
            return ResponseEntity.ok(this.compositeService.getRandom());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/composites/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

}
