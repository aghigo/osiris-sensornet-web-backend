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

public class CompositeService {

    @Autowired
    private CompositeGenerator compositeGenerator;

    @Autowired
    private CompositeRepository compositeRepository;

    public CompositeVsnTo getRandom() throws AbstractRequestException {
        return this.compositeGenerator.generateCompositeVsnTo();
    }

    public List<CompositeResponse> getAll() throws AbstractRequestException {
        List<CompositeVsnTo> compositeVsnToList = this.compositeRepository.getAll();
        return CompositeMapper.vsnToToResponse(compositeVsnToList);
    }

    public CompositeResponse getById(String compositeId) throws AbstractRequestException {
        CompositeVsnTo compositeVsnTo = this.compositeRepository.getById(compositeId);
        CompositeResponse compositeResponse = CompositeMapper.vsnToToResponse(compositeVsnTo);
        return compositeResponse;
    }

    public URI create(CompositeRequest compositeRequest) throws URISyntaxException, AbstractRequestException {
        CompositeVsnTo compositeVsnTo = CompositeMapper.requestToVsnTo(compositeRequest);
        return this.compositeRepository.create(compositeVsnTo);
    }

    public void update(String compositeId, CompositeRequest compositeRequest) throws AbstractRequestException {
        CompositeVsnTo compositeVsnTo = CompositeMapper.requestToVsnTo(compositeRequest);
        this.compositeRepository.update(compositeId, compositeVsnTo);
    }

    public void delete(String compositeId) throws AbstractRequestException {
        this.compositeRepository.delete(compositeId);
    }
}
