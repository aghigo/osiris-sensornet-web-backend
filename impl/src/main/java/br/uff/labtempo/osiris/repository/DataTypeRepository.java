package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface DataTypeRepository {
    DataTypeVsnTo getById(String id) throws AbstractClientRuntimeException, AbstractRequestException;
    List<DataTypeVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException;
    URI insert(DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException;
    void update(String id, DataTypeVsnTo dataTypeVsnTo) throws AbstractClientRuntimeException, AbstractRequestException;
    void delete(String id) throws AbstractClientRuntimeException, AbstractRequestException;
}
