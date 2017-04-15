package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface LinkRepository {
    LinkVsnTo getById(String id) throws AbstractRequestException, AbstractClientRuntimeException;
    List<LinkVsnTo> getAll() throws AbstractRequestException, AbstractClientRuntimeException;
    URI save(LinkVsnTo linkVsnTo) throws AbstractRequestException, AbstractClientRuntimeException, URISyntaxException;
    void update(String id, LinkVsnTo linkVsnTo) throws AbstractRequestException, AbstractClientRuntimeException;
    void delete(String id) throws AbstractRequestException, AbstractClientRuntimeException;
    List<FieldTo> getAllFields() throws AbstractRequestException, AbstractClientRuntimeException;
}
