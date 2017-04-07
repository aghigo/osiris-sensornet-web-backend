package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.repository.OmcpRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
@RequestMapping(value = "/sensornet/omcp", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorNetOmcpControler {
    @Autowired
    @Qualifier("sensorNetOmcpDao")
    private OmcpRepository<String> omcpRepository;

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

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectorIdSensorId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
