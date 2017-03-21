package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.UnreachableModuleException;
import br.uff.labtempo.osiris.model.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkService {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private NetworkGenerator networkGenerator;

//    @Autowired
//    public NetworkService(NetworkRepository networkRepository, NetworkGenerator networkGenerator) {
//        this.networkRepository = networkRepository;
//        this.networkGenerator = networkGenerator;
//    }

    public NetworkCoTo getRandom() {
        return this.networkGenerator.generate();
    }

    public List<NetworkCoTo> getAll() throws UnreachableModuleException, BadRequestException {
        return this.networkRepository.getAll();
    }

    public NetworkCoTo getById(String id) {
        return this.networkRepository.getById(id);
    }
}