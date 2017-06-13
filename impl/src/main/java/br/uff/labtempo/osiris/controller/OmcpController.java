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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller to call OMCP modules directly
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/omcp", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcpController {
    @Autowired
    @Qualifier("genericOmcpDao")
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
            responseContent = this.omcpRepository.doGet(uri);
            return ResponseEntity.ok().body(responseContent);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
