package br.uff.labtempo.osiris.repository;


import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.dao.FunctionModuleOmcpDao;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;

import java.util.List;

/**
 * Interface that abstract FunctionFactory DAO implementations
 * @see FunctionModuleOmcpDao
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public interface FunctionModuleRepository {
    InterfaceFnTo getInterface(String functionName) throws AbstractClientRuntimeException, AbstractRequestException;
    List<InterfaceFnTo> getAllInterfaces() throws AbstractClientRuntimeException, AbstractRequestException;
}
