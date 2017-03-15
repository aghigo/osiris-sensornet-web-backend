package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.request.network.NetworkRequest;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NetworkService {

    private NetworkGenerator networkGenerator;

    @Autowired
    public NetworkService(NetworkGenerator networkGenerator) {
        this.networkGenerator = networkGenerator;
    }

    public NetworkCoTo getRandom() {
        return this.networkGenerator.generate();
    }
}