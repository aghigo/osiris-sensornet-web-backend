package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.model.request.NetworkRequest;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NetworkService {

    public List<NetworkCoTo> getAll(){
        OmcpClient omcpClient = new RabbitClient("172.17.0.3", "guest", "guest");
        Response omcpResponse = omcpClient.doGet("omcp://sensornet.osiris/");
        NetworkCoTo[] networkCoTos = omcpResponse.getContent(NetworkCoTo[].class);
        List<NetworkCoTo> networkCoToList = Arrays.asList(networkCoTos);
        return networkCoToList;
    }

    public HttpStatus post(NetworkRequest networkRequest) {
        OmcpClient omcpClient = new RabbitClient("172.17.0.3", "guest", "guest");
        Response omcpResponse = omcpClient.doPost("omcp://sensornet.osiris/", networkRequest);
        return OmcpUtil.toHttpStatus(omcpResponse.getStatusCode());
    }

    public void put() {

    }

    public void delete() {

    }
}