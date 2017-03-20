package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.UnreachableModuleException;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.request.network.NetworkRequest;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NetworkService {
    private final String RESOURCE_URI = "omcp://sensornet.osiris/";
    private final String SENSORNET_IP = "127.0.0.1";
    private final int SENSORNET_PORT = 8090;
    private final String RABBITMQ_USER = "guest";
    private final String RABBITMQ_PASSWORD = "guest";
    private NetworkGenerator networkGenerator;

    @Autowired
    public NetworkService(NetworkGenerator networkGenerator) {
        this.networkGenerator = networkGenerator;
    }

    public NetworkCoTo getRandom() {
        return this.networkGenerator.generate();
    }

    public List<NetworkCoTo> getAll() throws UnreachableModuleException, BadRequestException {
        OmcpClient omcpClient = new RabbitClient(SENSORNET_IP, RABBITMQ_USER, RABBITMQ_PASSWORD);
        Response response = omcpClient.doGet(RESOURCE_URI);
        NetworkCoTo[] networkCoToArray = response.getContent(NetworkCoTo[].class);
        List<NetworkCoTo> networkCoTos = Arrays.asList(networkCoToArray);
        return networkCoTos;
    }

    public NetworkCoTo getById(long id) {
        OmcpClient omcpClient = new RabbitClient(SENSORNET_IP, RABBITMQ_USER, RABBITMQ_PASSWORD);
        Response response = omcpClient.doGet(RESOURCE_URI + String.valueOf(id));
        NetworkCoTo networkCoTo = response.getContent(NetworkCoTo.class);
        return networkCoTo;
    }

    public void post(NetworkRequest networkRequest) {
        OmcpClient omcpClient = new RabbitClient(SENSORNET_IP, RABBITMQ_USER, RABBITMQ_PASSWORD);
        NetworkCoTo networkCoTo = new NetworkCoTo(networkRequest.getId());
        networkCoTo.addInfo(networkRequest.getInfo());
        Response response = omcpClient.doPost(RESOURCE_URI, networkCoTo);
    }

}