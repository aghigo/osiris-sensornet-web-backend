package br.uff.labtempo.osiris.factory.function;

import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.server.OmcpServer;
import br.uff.labtempo.omcp.server.rabbitmq.RabbitServer;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.function.ParamFnTo;
import bsh.EvalError;
import bsh.Interpreter;
import lombok.*;

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
public class FunctionFactory {
    private final ValueType DEFAULT_FUNCTION_TYPE = ValueType.NUMBER;
    private final String DEFAULT_FUNCTION_NAME_TEMPLATE = "%s.function";
    private final String FUNCTION_MODULE_TEMPLATE_URI = "omcp://%s.function.osiris/";
    private final FunctionOperation DEFAULT_FUNCTION_OPERATION = FunctionOperation.SYNCHRONOUS;

    private final ValueType type;
    private String name;
    private String address;
    private String implementation;
    private FunctionOperation operation;
    private InterfaceFnTo interfaceFnTo;

    private boolean running = false;

    private OmcpServer omcpServer;

    public FunctionFactory(String name, String description, String implementation, List<ParamFnTo> requestParams, List<ParamFnTo> responseParams) throws IllegalArgumentException {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create FunctionFactory module: empty name.");
        }
        if(description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create FunctionFactory module: no description provided.");
        }
        if(requestParams == null || requestParams.isEmpty()) {
            throw new IllegalArgumentException("Failed to create FunctionFactory module: no request parameters provided.");
        }
        if(responseParams == null || responseParams.isEmpty()) {
            throw new IllegalArgumentException("Failed to create FunctionFactory module: no response parameters provided.");
        }
        if(implementation == null || implementation.trim().isEmpty()) {
            throw new IllegalArgumentException("Failed to create FunctionFactory module: no formula implementation provided.");
        }
        this.validateImplementation(implementation);
        this.implementation = implementation;

        this.type = this.DEFAULT_FUNCTION_TYPE;
        this.operation = this.DEFAULT_FUNCTION_OPERATION;
        String functionName = String.format(this.DEFAULT_FUNCTION_NAME_TEMPLATE, name.trim().toLowerCase());
        this.name = functionName;
        String address = String.format(this.FUNCTION_MODULE_TEMPLATE_URI, name.trim().toLowerCase());
        this.address = address;
        List<FunctionOperation> functionOperationList = new ArrayList<>();
        functionOperationList.add(this.DEFAULT_FUNCTION_OPERATION);
        this.interfaceFnTo = new InterfaceFnTo(functionName, description, address, functionOperationList, requestParams, responseParams);
    }

    public OmcpServer createOmcpServer(String host, String username, String password) {
        this.omcpServer = new RabbitServer(this.name, host, username, password);
        FunctionDefaultControllerImpl functionDefaultControllerImpl = new FunctionDefaultControllerImpl(new RabbitClient(host, username, password), this.interfaceFnTo, this.implementation);
        this.omcpServer.setHandler(functionDefaultControllerImpl);
        return this.omcpServer;
    }

    private void validateImplementation(String implementation) {
        try {
            Interpreter interpreter = new Interpreter();
            interpreter.set("result", 0.0);
            interpreter.set("value", 0.0);
            List<Double> doubleList = new ArrayList<>();
            doubleList.add(0.0);
            interpreter.set("values", doubleList);
            interpreter.set("total", doubleList.size());
            Double result = (double) interpreter.eval(implementation);
            if(result == null || result.isNaN()) {
                throw new IllegalArgumentException(String.format("Failed to create FunctionFactory module: bad implementation returning null or invalid numeric value '%s'", result));
            }
        } catch (EvalError evalError) {
            throw new IllegalArgumentException("Failed to create FunctionFactory module: Invalid formula implementation syntax ("+ evalError.getMessage() + ")");
        }
    }

    public void start() {
        this.omcpServer.start();
        this.running = true;
    }

    public void close() throws Exception {
        this.omcpServer.close();
        this.running = false;
    }
}
