package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.mapper.NetworkMapper;
import br.uff.labtempo.osiris.generator.network.NetworkGenerator;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class with business rules to select/create/update/remove Networks from/on SensorNet module
 * @see NetworkCoTo (Network representation on SensorNet module to be sent by the client)
 * @see br.uff.labtempo.osiris.to.sensornet.NetworkSnTo (Network representation sent by the SensorNet module)
 * @see br.uff.labtempo.osiris.model.request.NetworkRequest (Network data sent by the client to the API on POST/PUT HTTP requests)
 * @see NetworkResponse (Network response data sent by the application to the Client)
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
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

    /**
     * Creates a random NetworkCoTo mocked object
     * @see NetworkCoTo
     * @return NetworkCoTo
     */
    public NetworkCoTo getRandom() {
        return this.networkGenerator.getNetworkCoto();
    }

    /**
     * Get a list of all available Networks from SensorNet module
     * @return List of NetworkResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public List<NetworkResponse> getAll() throws AbstractRequestException, AbstractClientRuntimeException {
        return NetworkMapper.snToToResponse(this.networkRepository.getAll());
    }

    /**
     * Get a specific Network from SensorNet module by its unique Id
     * @param id
     * @return NetworkResponse
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public NetworkResponse getById(String id) throws AbstractRequestException, AbstractClientRuntimeException {
        return NetworkMapper.snToToResponse(this.networkRepository.getById(id));
    }
}