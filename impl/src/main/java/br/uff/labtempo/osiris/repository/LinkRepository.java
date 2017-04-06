package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;

import java.util.List;

public interface LinkRepository {
    LinkVsnTo getById(String id);
    List<LinkVsnTo> getAll();
    void save(LinkVsnTo linkVsnTo);
    void update(String id, LinkVsnTo linkVsnTo);
    void delete(String id);
}
