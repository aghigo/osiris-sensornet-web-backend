package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/generic", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GenericOmcpControler {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> doGet(@RequestHeader(value = "url") String url) {
        String ip = this.sensorNetConfig.getIp();
        String username = this.sensorNetConfig.getUsername();
        String password = this.sensorNetConfig.getPassword();
        OmcpClient omcpClient = new RabbitClient(ip, username, password);
        Response response = omcpClient.doGet(url);
        return ResponseEntity.status(response.getStatusCode().toCode()).body(response.getContent());
    }

}
