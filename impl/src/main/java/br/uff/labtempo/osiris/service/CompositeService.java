package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.generator.CompositeGenerator;
import br.uff.labtempo.osiris.mapper.CompositeMapper;
import br.uff.labtempo.osiris.model.request.CompositeRequest;
import br.uff.labtempo.osiris.model.response.CompositeResponse;
import br.uff.labtempo.osiris.repository.CompositeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Service class with business rules to create/update/remove/select Composite sensors
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class CompositeService {

    @Autowired
    private CompositeGenerator compositeGenerator;

    @Autowired
    private CompositeRepository compositeRepository;

    /**
     * Get a random Composite sensor mocked object
     * @see CompositeVsnTo
     * @return CompositeVsnTo
     * @throws AbstractRequestException
     */
    public CompositeVsnTo getRandom() throws AbstractRequestException {
        return this.compositeGenerator.generateCompositeVsnTo();
    }

    /**
     * Get a list of all available Composite sensors from VirtualSensorNet
     * @return List of CompositeResponse
     * @throws AbstractRequestException
     */
    public List<CompositeResponse> getAll() throws AbstractRequestException {
        List<CompositeVsnTo> compositeVsnToList = this.compositeRepository.getAll();
        return CompositeMapper.vsnToToResponse(compositeVsnToList);
    }

    /**
     * Get a specific Composite sensor from VirtualSensorNet based on a unique {compositeId}
     * @param compositeId
     * @return CompositeResponse
     * @throws AbstractRequestException
     */
    public CompositeResponse getById(String compositeId) throws AbstractRequestException {
        CompositeVsnTo compositeVsnTo = this.compositeRepository.getById(compositeId);
        CompositeResponse compositeResponse = CompositeMapper.vsnToToResponse(compositeVsnTo);
        return compositeResponse;
    }

    /**
     * Create a new Composite Sensor on VirtualSensorNet
     * @param compositeRequest
     * @return URI with new Composite sensor location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI create(CompositeRequest compositeRequest) throws URISyntaxException, AbstractRequestException {
        CompositeVsnTo compositeVsnTo = CompositeMapper.requestToVsnTo(compositeRequest);
        return this.compositeRepository.create(compositeVsnTo);
    }

    /**
     * Update an existing Composite sensor from VirtualSensorNet
     * @param compositeId
     * @param compositeRequest
     * @throws AbstractRequestException
     */
    public void update(String compositeId, CompositeRequest compositeRequest) throws AbstractRequestException {
        CompositeVsnTo compositeVsnTo = CompositeMapper.requestToVsnTo(compositeRequest);
        this.compositeRepository.update(compositeId, compositeVsnTo);
    }

    /**
     * Removes an existing Composite sensor from VirtualSensorNet
     * @param compositeId
     * @throws AbstractRequestException
     */
    public void delete(String compositeId) throws AbstractRequestException {
        this.compositeRepository.delete(compositeId);
    }
}
