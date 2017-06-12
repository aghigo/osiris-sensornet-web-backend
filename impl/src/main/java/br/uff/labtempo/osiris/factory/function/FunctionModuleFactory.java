package br.uff.labtempo.osiris.factory.function;

import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.server.OmcpServer;
import br.uff.labtempo.omcp.server.rabbitmq.RabbitServer;
import br.uff.labtempo.osiris.configuration.FunctionModuleConfig;
import br.uff.labtempo.osiris.factory.connection.FunctionConnectionFactory;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.domain.function.FunctionModule;
import br.uff.labtempo.osiris.model.domain.function.FunctionModuleDefaultImpl;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.function.ParamFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import bsh.EvalError;
import bsh.Interpreter;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents function data into Application domain
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class FunctionModuleFactory {
    @Autowired
    private FunctionModuleConfig functionModuleConfig;

    @Autowired
    private FunctionConnectionFactory functionConnectionFactory;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    public FunctionModule getInstance(String name, String description, String implementation, long dataTypeId) throws IllegalArgumentException, AbstractRequestException {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty name.");
        }
        name = name.trim().toLowerCase();

        if(description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("No description provided.");
        }
        description = description.trim().toLowerCase();

        if(implementation == null || implementation.trim().isEmpty()) {
            throw new IllegalArgumentException("No formula implementation provided.");
        }
        implementation = implementation.trim();
        this.validateImplementation(implementation);

        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(dataTypeId);
        if(dataTypeVsnTo == null) {
            throw new IllegalArgumentException("Invalid dataType id.");
        }

        FunctionData functionData = new FunctionData();
        functionData.setName(name);
        functionData.setDescription(description);
        functionData.setImplementation(implementation);
        functionData.setParamType(this.functionModuleConfig.getDefaultParamType());
        functionData.setDataTypeId(dataTypeVsnTo.getId());
        functionData.setDataTypeName(dataTypeVsnTo.getDisplayName());
        functionData.setDataTypeUnit(dataTypeVsnTo.getUnit());
        functionData.setFullName(String.format(this.functionModuleConfig.getFullNameTemplate(), name));
        functionData.setOperation(this.functionModuleConfig.getDefaultOperation());
        functionData.setOmcpUri(String.format(this.functionModuleConfig.getOmcpUriTemplate(), name));
        functionData.setInterfaceUri(String.format(this.functionModuleConfig.getOmcpInterfaceUriTemplate(), name));
        functionData.setDataUri(String.format(this.functionModuleConfig.getRestDataUriTemplate(), name));

        List<FunctionOperation> functionOperationList = new ArrayList<>();
        functionOperationList.add(this.functionModuleConfig.getDefaultOperation());

        List<ParamFnTo> requestParams = new ArrayList<>();
        requestParams.add(new ParamFnTo(dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getUnit(), this.functionModuleConfig.getDefaultParamType(), true));

        List<ParamFnTo> responseParams = new ArrayList<>();
        responseParams.add(new ParamFnTo(name, dataTypeVsnTo.getUnit(), this.functionModuleConfig.getDefaultParamType(), false));

        InterfaceFnTo interfaceFnTo = new InterfaceFnTo(name, description, String.format(this.functionModuleConfig.getOmcpUriTemplate(), name), functionOperationList, requestParams, responseParams);

        FunctionModuleDefaultImpl functionModuleDefaultImpl = new FunctionModuleDefaultImpl((RabbitClient) this.functionConnectionFactory.getConnection(), interfaceFnTo, implementation);

        String host = this.functionConnectionFactory.getCommunicationLayerConnectionFactory().getIp();
        String username = this.functionConnectionFactory.getCommunicationLayerConnectionFactory().getUsername();
        String password = this.functionConnectionFactory.getCommunicationLayerConnectionFactory().getPassword();

        OmcpServer omcpServer = new RabbitServer(name, host, username, password);
        omcpServer.setHandler(functionModuleDefaultImpl);

        FunctionModule functionModule = FunctionModule.builder()
                .functionData(functionData)
                .omcpServer(omcpServer)
                .running(false)
                .build();

        return functionModule;
    }

    private void validateImplementation(String implementation) {
        try {
            double initialValue = 0.0;
            Interpreter interpreter = new Interpreter();
            interpreter.set("result", initialValue);
            interpreter.set("value", initialValue);
            List<Double> doubleList = new ArrayList<>();
            doubleList.add(initialValue);
            interpreter.set("values", doubleList);
            interpreter.set("total", doubleList.size());
            Double result = (double) interpreter.eval(implementation);
            if(result == null || result.isNaN()) {
                throw new IllegalArgumentException(String.format("Bad implementation returning null or invalid numeric value '%s'", result));
            }
        } catch (EvalError evalError) {
            throw new IllegalArgumentException("Invalid formula implementation syntax ("+ evalError.getMessage() + ")");
        }
    }
}
