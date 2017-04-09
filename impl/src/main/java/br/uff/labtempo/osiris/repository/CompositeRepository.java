package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface CompositeRepository {
    CompositeVsnTo getById(String compositeId) throws AbstractRequestException;
    List<CompositeVsnTo> getAll() throws AbstractRequestException;
    URI create(CompositeVsnTo compositeVsnTo) throws AbstractRequestException, URISyntaxException;
    void update(String compositeId, CompositeVsnTo compositeVsnTo) throws AbstractRequestException;
    void delete(String compositeId) throws AbstractRequestException;
}
