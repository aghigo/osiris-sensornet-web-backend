package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.service.BlendingService;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
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
public class BlendingController {

    @Autowired
    private BlendingService blendingService;

    @RequestMapping(value = "/blendings", method = RequestMethod.GET)
    public ResponseEntity<?> doGetAll() {
        try {
            List<BlendingResponse> blendingResponseList = this.blendingService.getAll();
            return ResponseEntity.ok(blendingResponseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/blendings", method = RequestMethod.POST)
    public ResponseEntity<?> doPost(@RequestBody @Valid BlendingRequest blendingRequest) {
        try {
            URI uri = this.blendingService.create(blendingRequest);
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/blendings", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptions() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/blendings/mock", method = RequestMethod.GET)
    public ResponseEntity<?> doGetMock() {
        try {
            BlendingVsnTo blendingVsnTo = this.blendingService.getRandom();
            return ResponseEntity.ok(blendingVsnTo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/blendings/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/blendings/{blendingId}", method = RequestMethod.PUT)
    public ResponseEntity<?> doPut(@PathVariable String blendingId, @RequestBody @Valid BlendingRequest blendingRequest) {
        try {
            URI uri = this.blendingService.create(blendingRequest);
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/blendings/{blendingId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> doDelete(@PathVariable Long blendingId) {
        try {
            this.blendingService.delete(blendingId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/blendings/{blendingId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> doOptionsBlendingId() {
        return allows(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS);
    }
}
