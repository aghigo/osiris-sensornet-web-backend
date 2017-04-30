package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.dao.CompositeOmcpDao;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface that abstracts Composite DAO implementations
 * @see CompositeOmcpDao
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public interface CompositeRepository {
    CompositeVsnTo getById(String compositeId) throws AbstractRequestException;
    List<CompositeVsnTo> getAll() throws AbstractRequestException;
    URI create(CompositeVsnTo compositeVsnTo) throws AbstractRequestException, URISyntaxException;
    void update(String compositeId, CompositeVsnTo compositeVsnTo) throws AbstractRequestException;
    void delete(String compositeId) throws AbstractRequestException;
}
