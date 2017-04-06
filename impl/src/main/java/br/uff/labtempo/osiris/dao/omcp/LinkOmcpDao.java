package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("linkOmcpDao")
public class LinkOmcpDao implements LinkRepository {
    @Override
    public LinkVsnTo getById(String id) {
        return null;
    }

    @Override
    public List<LinkVsnTo> getAll() {
        return null;
    }

    @Override
    public void save(LinkVsnTo linkVsnTo) {

    }

    @Override
    public void update(String id, LinkVsnTo linkVsnTo) {

    }

    @Override
    public void delete(String id) {

    }
}
