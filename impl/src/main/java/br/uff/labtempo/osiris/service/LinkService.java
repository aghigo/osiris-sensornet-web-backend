package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.mapper.LinkMapper;
import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        LinkResponse linkResponse = LinkMapper.

    }

    public List<LinkResponse> getAll() {
        return null;
    }

    public void create(LinkRequest linkRequest) {

    }

    public void update(String id, LinkRequest linkRequest) {

    }

    public void delete(String id) {

    }
}
