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

@Service
public class LinkService {

    @Autowired
    private LinkGenerator linkGenerator;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public LinkVsnTo getRandom() throws AbstractRequestException {
        return this.linkGenerator.generateVsnTo();
    }

    public LinkResponse getById(String id) throws AbstractRequestException {
        LinkVsnTo linkVsnTo = this.linkRepository.getById(id);
        LinkResponse linkResponse = LinkMapper.vsnToToResponse(linkVsnTo);
        return linkResponse;
    }

    public List<LinkResponse> getAll() throws AbstractRequestException {
        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        List<LinkResponse> linkResponseList = LinkMapper.vsnToToResponse(linkVsnToList);
        return linkResponseList;
    }

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

    public void delete(String id) throws AbstractRequestException {
        this.linkRepository.delete(id);
    }

    private boolean validateLink(String networkId, String collectorId, String sensorId) throws AbstractRequestException {
        SensorSnTo sensorSnTo = this.sensorRepository.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId);
        return sensorSnTo != null && sensorSnTo.getId() != null && !sensorSnTo.getId().isEmpty();
    }
}
