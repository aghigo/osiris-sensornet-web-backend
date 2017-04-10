package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.service.VirtualSensorService;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

@RestController
@RequestMapping(value = "/virtualsensornet", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VirtualSensorController {

    @Autowired
    private VirtualSensorService virtualSensorService;

    @RequestMapping(value = "/virtualsensor", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        try {
            List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorService.getAll();
            return ResponseEntity.ok(virtualSensorVsnToList);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/virtualsensor", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> getOptions() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    @RequestMapping(value = "/virtualsensor/{virtualSensorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String virtualSensorId) {
        try {
            VirtualSensorVsnTo virtualSensorVsnTo = this.virtualSensorService.getById(virtualSensorId);
            return ResponseEntity.ok(virtualSensorVsnTo);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/virtualsensor/{virtualSensorId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> getOptionsById() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
