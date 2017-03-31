package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.mapper.NetworkMapper;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkService {
    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkGenerator networkGenerator;

    @Autowired
    public NetworkService(NetworkRepository networkRepository, NetworkGenerator networkGenerator) {
        this.networkRepository = networkRepository;
        this.networkGenerator = networkGenerator;
    }

    public NetworkCoTo getRandom() {
        return this.networkGenerator.getNetworkCoto();
    }

    public List<NetworkResponse> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        return NetworkMapper.toResponse(this.networkRepository.getAll());
    }

    public NetworkResponse getById(String id) throws AbstractRequestException, AbstractClientRuntimeException {
        return NetworkMapper.toResponse(this.networkRepository.getById(id));
    }
}