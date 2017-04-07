package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.mapper.LinkMapper;
import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class LinkService {

    private LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    public LinkResponse getById(String id) throws AbstractRequestException {
        LinkVsnTo linkVsnTo = this.linkRepository.getById(id);
        LinkResponse linkResponse = LinkMapper.toResponse(linkVsnTo);
        return linkResponse;
    }

    public List<LinkResponse> getAll() throws AbstractRequestException {
        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        List<LinkResponse> linkResponseList = LinkMapper.toResponse(linkVsnToList);
        return linkResponseList;
    }

    public URI create(LinkRequest linkRequest) throws AbstractRequestException {
        LinkVsnTo linkVsnTo = LinkMapper.toVsnTo(linkRequest);
        URI uri = this.linkRepository.save(linkVsnTo);
        return uri;
    }

    public void update(String id, LinkRequest linkRequest) throws AbstractRequestException {
        LinkVsnTo linkVsnTo = LinkMapper.toVsnTo(linkRequest);
        this.linkRepository.update(id, linkVsnTo);
    }

    public void delete(String id) throws AbstractRequestException {
        this.linkRepository.delete(id);
    }
}
