package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Repository inteface to abstract VirtualSensorNet FunctionFactory DAO implementations
 * @see FunctionVsnTo
 * @author andre.ghigo
 * @version 1.0
 * @since 10/07/2017
 */

public interface VsnFunctionRepository {
    List<FunctionVsnTo> getAll() throws AbstractRequestException;
    FunctionVsnTo getById(long functionId) throws AbstractRequestException;
    URI create(FunctionVsnTo functionVsnTo) throws AbstractRequestException, URISyntaxException;
    void update(long functionId, FunctionVsnTo functionVsnTo) throws AbstractRequestException;
    void delete(long functionId) throws AbstractRequestException;
}
