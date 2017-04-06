package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.repository.LinkRepository;
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

    public LinkResponse getById(String id) {
        return null;
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
