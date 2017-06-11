package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.dao.DataTypeOmcpDao;
import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface to abstract DataType DAO implementations
 * @see DataTypeOmcpDao
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public interface DataTypeRepository {
    DataTypeVsnTo getById(long id) throws AbstractClientRuntimeException, AbstractRequestException;
    List<DataTypeVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException;
    URI insert(DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException;
    void update(String id, DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException;
    void delete(String id) throws AbstractClientRuntimeException, AbstractRequestException;
}
