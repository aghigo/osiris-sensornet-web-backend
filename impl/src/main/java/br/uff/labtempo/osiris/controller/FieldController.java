package br.uff.labtempo.osiris.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

/**
 * Controller Class for Virtual Sensor Fields Endpoints
 * @author andre.ghigo
 * @version 1.0
 * @since 01/05/17.
 */
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FieldController {

    @RequestMapping(value = "/virtualsensornet/fields")
    public ResponseEntity<?> doGetAllFields() {
        return null;
    }
}
