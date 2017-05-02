package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.dao.FunctionOmcpDao;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface that abstract Function DAO implementations
 * @see FunctionOmcpDao
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public interface FunctionRepository {
    InterfaceFnTo getInterface(String functionName) throws AbstractClientRuntimeException, AbstractRequestException;
    URI createOnVirtualSensorNet(InterfaceFnTo interfaceFnTo) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException;
    FunctionVsnTo getFromVirtualSensorNet(String functionName) throws AbstractClientRuntimeException, AbstractRequestException;
    List<FunctionVsnTo> getAll() throws AbstractClientRuntimeException, AbstractRequestException;
    List<FunctionVsnTo> getAllFromVirtualSensorNet() throws AbstractClientRuntimeException, AbstractRequestException;
}
