package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.repository.OmcpRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class for generic component management on SensorNet module
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@RestController
@RequestMapping(value = "/sensornet/omcp", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorNetOmcpController {
    @Autowired
    @Qualifier("sensorNetOmcpDao")
    private OmcpRepository<String> omcpRepository;

    /**
     * do GET HTTP request to SensorNet module based on a valid OMCP uri
     * @param uri
     * @return Response with the result of the GET request
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> doGet(@RequestHeader(value = "uri") @NotEmpty @NotNull @Valid String uri) {
        String responseContent = null;
        try {
            responseContent = (String) this.omcpRepository.doGet(uri);
        } catch(AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        }
        if(responseContent == null || responseContent.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(responseContent);
    }

    /**
     * Get a list of available HTTP methods of the /sensornet/omcp endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectorIdSensorId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
