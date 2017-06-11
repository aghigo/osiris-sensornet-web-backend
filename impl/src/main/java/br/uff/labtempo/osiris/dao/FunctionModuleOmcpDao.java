package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.configuration.AvailableFunctionListConfig;
import br.uff.labtempo.osiris.connection.RabbitMQConnection;
import br.uff.labtempo.osiris.repository.FunctionModuleRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.util.OmcpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * DAO class for FunctionFactory CRUD on FunctionFactory module
 * @author andre.ghigo
 * @since  1.8
 * @version  1.0
 */
@Component("functionModuleOmcpDao")
public class FunctionModuleOmcpDao implements FunctionModuleRepository {

    @Autowired
    AvailableFunctionListConfig availableFunctionListConfig;

    @Autowired
    RabbitMQConnection rabbitMQConnection;

    /**
     * Get a function Interface based on the function name
     * @param functionName
     * @return InterfaceFnTo (FunctionFactory interface)
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    @Override
    public InterfaceFnTo getInterface(String functionName) throws AbstractClientRuntimeException, AbstractRequestException {
        OmcpClient omcpClient = this.rabbitMQConnection.getConnection();
        String uri = String.format(this.availableFunctionListConfig.getFunctionInterfaceUri(), functionName);
        Response response = omcpClient.doGet(uri);
        OmcpUtil.handleOmcpResponse(response);
        InterfaceFnTo interfaceFnTo = response.getContent(InterfaceFnTo.class);
        return interfaceFnTo;
    }

    @Override
    public List<InterfaceFnTo> getAllInterfaces() throws AbstractRequestException {
        OmcpClient omcpClient = this.rabbitMQConnection.getConnection();
        String uri = this.availableFunctionListConfig.getFunctionInterfaceUri();
        Response response = omcpClient.doGet(uri);
        OmcpUtil.handleOmcpResponse(response);
        InterfaceFnTo[] interfaceFnToArray = response.getContent(InterfaceFnTo[].class);
        List<InterfaceFnTo> interfaceFnToList = Arrays.asList(interfaceFnToArray);
        return interfaceFnToList;
    }
}
