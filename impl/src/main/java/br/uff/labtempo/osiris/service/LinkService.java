package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.osiris.mapper.LinkMapper;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.repository.CollectorRepository;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class with business rules to select/create/update/remove Link sensors on/from VirtualSensorNet module
 * @see LinkVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class LinkService {

    @Autowired
    private LinkGenerator linkGenerator;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Get a random LinkVsnTo mocked object
     * @see LinkVsnTo
     * @return LinkVsnTo
     * @throws AbstractRequestException
     */
    public LinkVsnTo getRandom() throws AbstractRequestException {
        return this.linkGenerator.generateVsnTo();
    }

    /**
     * Get a specific Link sensor from VirtualSensorNet module by its unique id
     * @see LinkResponse
     * @param id
     * @return LinkResponse
     * @throws AbstractRequestException
     */
    public LinkResponse getById(String id) throws AbstractRequestException {
        LinkVsnTo linkVsnTo = this.linkRepository.getById(id);
        LinkResponse linkResponse = LinkMapper.vsnToToResponse(linkVsnTo);
        return linkResponse;
    }

    /**
     * Get a list with all available Link sensors from VirtualSensorNet
     * @return List of LinkResponse
     * @throws AbstractRequestException
     */
    public List<LinkResponse> getAll() throws AbstractRequestException {
        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        List<LinkResponse> linkResponseList = LinkMapper.vsnToToResponse(linkVsnToList);
        return linkResponseList;
    }

    /**
     * Creates a new Link sensor on VirtualSensorNet module
     * @param linkRequest
     * @return URI with new Link sensor location
     * @throws AbstractRequestException
     * @throws URISyntaxException
     */
    public URI create(LinkRequest linkRequest) throws AbstractRequestException, URISyntaxException {
        String networkId = linkRequest.getNetworkId();
        String collectorId = linkRequest.getCollectorId();
        String sensorId = linkRequest.getSensorId();

        if(!validateLink(networkId, collectorId, sensorId)) {
            throw new BadRequestException("Failed to create Link sensor: Invalid (networkId, collectorId, sensorId) association.");
        }

        LinkVsnTo linkVsnTo = LinkMapper.requestToVsnTo(linkRequest);
        URI uri = this.linkRepository.save(linkVsnTo);
        return uri;
    }

    /**
     * Update an existing Link sensor from VirtualSensorNet module
     * @param id
     * @param linkRequest
     * @throws AbstractRequestException
     */
    public void update(String id, LinkRequest linkRequest) throws AbstractRequestException {
        String networkId = linkRequest.getNetworkId();
        String collectorId = linkRequest.getCollectorId();
        String sensorId = linkRequest.getSensorId();

        if(!validateLink(networkId, collectorId, sensorId)) {
            throw new BadRequestException("Failed to create Link sensor: Invalid (networkId, collectorId, sensorId) association.");
        }

        LinkVsnTo linkVsnTo = LinkMapper.requestToVsnTo(linkRequest);
        this.linkRepository.update(id, linkVsnTo);
    }

    /**
     * Removes an existing Link sensor from VirtualSensorNet module
     * @param id
     * @throws AbstractRequestException
     */
    public void delete(String id) throws AbstractRequestException {
        this.linkRepository.delete(id);
    }

    /**
     * Validates given networkId, collectorId and sensorId for the LinkId creation/update
     * @param networkId
     * @param collectorId
     * @param sensorId
     * @return boolean
     * @throws AbstractRequestException
     */
    private boolean validateLink(String networkId, String collectorId, String sensorId) throws AbstractRequestException {
        SensorSnTo sensorSnTo = this.sensorRepository.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId);
        return sensorSnTo != null && sensorSnTo.getId() != null && !sensorSnTo.getId().isEmpty();
    }
}
