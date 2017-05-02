package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.repository.OmcpRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
 * Controller class to do OMCP requests on VirtualSensorNet module
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@RestController
@RequestMapping(value = "/virtualsensornet/omcp", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VirtualSensorNetOmcpController {
    @Autowired
    @Qualifier("virtualSensorNetOmcpDao")
    private OmcpRepository<String> omcpRepository;

    /**
     * do a OMCP GET request on VirtualSensorNet module based on a valid OMCP URI
     * @param uri
     * @return RequestEntity with the GET response
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> doGet(@RequestHeader(value = "uri") @NotEmpty @NotNull @Valid String uri) {
        String responseContent = null;
        try {
            responseContent = this.omcpRepository.doGet(uri);
            return ResponseEntity.ok().body(responseContent);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Get a list of all available HTTP methods of the /virtualsensornet/omcp
     * @return List of HTTP methods
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectorIdSensorId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
