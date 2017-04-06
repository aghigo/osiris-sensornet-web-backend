package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;

import java.util.List;

public interface LinkRepository {
    LinkVsnTo getById(String id) throws AbstractRequestException, AbstractClientRuntimeException;
    List<LinkVsnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException;
    void save(LinkVsnTo linkVsnTo) throws AbstractRequestException, AbstractClientRuntimeException;
    void update(String id, LinkVsnTo linkVsnTo) throws AbstractRequestException, AbstractClientRuntimeException;
    void delete(String id) throws AbstractRequestException, AbstractClientRuntimeException;
}
