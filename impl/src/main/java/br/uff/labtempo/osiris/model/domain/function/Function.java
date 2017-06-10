package br.uff.labtempo.osiris.model.domain.function;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.server.OmcpServer;
import br.uff.labtempo.omcp.server.rabbitmq.RabbitServer;
import br.uff.labtempo.osiris.configuration.FunctionModuleConfig;
import br.uff.labtempo.osiris.connection.FunctionConnection;
import br.uff.labtempo.osiris.connection.RabbitMQConnection;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.function.ParamFnTo;
import bsh.EvalError;
import bsh.Interpreter;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents function data into Application domain
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Getter
@Setter
public class Function {
    private final String DEFAULT_FUNCTION_NAME_TEMPLATE = "%s.function";
    private final String FUNCTION_MODULE_TEMPLATE_URI = "omcp://%s.function.osiris/";
    private final FunctionOperation DEFAULT_FUNCTION_OPERATION = FunctionOperation.SYNCHRONOUS;

    private String name;
    private String address;
    private String implementation;
    private long callIntervalInMillis;
    private FunctionOperation operation;
    private InterfaceFnTo interfaceFnTo;

    private OmcpServer omcpServer;

    @Autowired
    private RabbitMQConnection rabbitMQConnection;

    public Function(String name, String description, String implementation, List<Double> implementationInputTestCase, double implementationOutputTestCase, List<ParamFnTo> requestParams, List<ParamFnTo> responseParams) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create Function module: empty name.");
        }
        if(description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create Function module: no description provided.");
        }
        if(requestParams == null || requestParams.isEmpty()) {
            throw new IllegalArgumentException("Failed to create Function module: no request parameters provided.");
        }
        if(responseParams == null || responseParams.isEmpty()) {
            throw new IllegalArgumentException("Failed to create Function module: no response parameters provided.");
        }
        if(implementation == null || implementation.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create Function module: no formula implementation provided.");
        }
        this.validateImplementation(implementation, implementationInputTestCase, implementationOutputTestCase);
        this.implementation = implementation;

        String functionName = String.format(this.DEFAULT_FUNCTION_NAME_TEMPLATE, name.trim().toLowerCase());
        this.name = functionName;
        String address = String.format(this.FUNCTION_MODULE_TEMPLATE_URI, name.trim().toLowerCase());
        this.address = address;
        List<FunctionOperation> functionOperationList = new ArrayList<>();
        functionOperationList.add(this.DEFAULT_FUNCTION_OPERATION);
        this.interfaceFnTo = new InterfaceFnTo(functionName, description, address, functionOperationList, requestParams, responseParams);

        String host = this.rabbitMQConnection.getIp();
        String username = this.rabbitMQConnection.getUsername();
        String password = this.rabbitMQConnection.getPassword();

        this.omcpServer = new RabbitServer(functionName, host, username, password);

        MainController mainController = new MainController(new RabbitClient(host, username, password), this.interfaceFnTo, implementation);
        this.omcpServer.setHandler(mainController);
    }

    private void validateImplementation(String implementation, List<Double> inputTestCase, double outputTestCase) {
        try {
            Interpreter interpreter = new Interpreter();
            interpreter.set("values", inputTestCase);
            interpreter.set("total", inputTestCase.size());
            double result = (double) interpreter.eval(implementation);
            if(result != outputTestCase) {
                throw new IllegalArgumentException(String.format("Failed to create Function module: formula implementation test returned %s but expected %s for for %s", result, outputTestCase, inputTestCase));
            }
        } catch (EvalError evalError) {
            throw new IllegalArgumentException("Failed to create Function module: Invalid formula implementation syntax ("+ evalError.getMessage() + ")");
        }
    }

    public void start() {
        this.omcpServer.start();
    }

    public void close() throws Exception {
        this.omcpServer.close();
    }
}
