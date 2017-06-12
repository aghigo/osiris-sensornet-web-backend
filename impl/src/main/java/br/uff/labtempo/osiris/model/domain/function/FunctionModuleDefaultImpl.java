/* 
 * Copyright 2015 Felipe Santos <fralph at ic.uff.br>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.uff.labtempo.osiris.model.domain.function;

import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Request;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.*;
import br.uff.labtempo.omcp.common.utils.ResponseBuilder;
import br.uff.labtempo.osiris.omcp.Controller;
import br.uff.labtempo.osiris.to.common.definitions.Path;
import br.uff.labtempo.osiris.to.function.*;
import bsh.EvalError;
import bsh.Interpreter;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.*;

/**
 * Controller class that extends the Controller class to implement the sum function business rules
 * @see Controller
 * @author Felipe Santos <fralph at ic.uff.br>
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
public class FunctionModuleDefaultImpl extends Controller {
    public static final String PROTOCOL_VERSION = "1.0";
    private final RabbitClient client;
    private final InterfaceFnTo interfaceFnTo;
    private final String implementation;

    public FunctionModuleDefaultImpl(RabbitClient omcpClient, InterfaceFnTo interfaceFnTo, String implementation) {
        this.client = omcpClient;
        this.interfaceFnTo = interfaceFnTo;
        this.implementation = implementation;
    }

    @Override
    public Response process(Request request) throws MethodNotAllowedException, NotFoundException, InternalServerErrorException, NotImplementedException, BadRequestException {
        try {
            return routing(request);
        } catch (Exception e) {
            System.out.println("Error in process: " + e.getMessage());
            return new Response(this.PROTOCOL_VERSION, StatusCode.INTERNAL_SERVER_ERROR, Calendar.getInstance(), this.interfaceFnTo.getName(), null, 0, MediaType.APPLICATION_JSON.toString(), null, "Failed to process request");
        }
    }

    private Response routing(Request request) throws MethodNotAllowedException, NotFoundException, InternalServerErrorException, NotImplementedException, BadRequestException {
        String contentType = request.getContentType();
        if (match(request.getResource(), Path.RESOURCE_FUNCTION_REQUEST.toString())) {
            Map<String, String> urlParams = super.extractParams(request.getResource(), Path.RESOURCE_FUNCTION_REQUEST.toString());
            //omcp://sum.function/
            switch (request.getMethod()) {
                case GET:
                    ParameterizedRequestFn prf = new ParameterizedRequestFn(urlParams);
                    RequestFnTo to = prf.getRequestFnTo();
                    ResponseFnTo responseFnTo = execute(to, request.getDate());
                    Response response = new ResponseBuilder().ok(responseFnTo, contentType).build();
                    return response;
                case POST:
                    to = request.getContent(RequestFnTo.class);
                    long id = create(to, request.getDate());
                    // /spool/{id}
                    String uri = Path.SEPARATOR.toString() + Path.NAMING_RESOURCE_SPOOL.toString() + Path.SEPARATOR + String.valueOf(id);
                    response = new ResponseBuilder().created(uri).build();
                    return response;
                default:
                    throw new MethodNotAllowedException("Action not allowed for this resource!");
            }
        } else if (match(request.getResource(), Path.RESOURCE_FUNCTION_SPOOL.toString())) {
            //omcp://sum.function/spool/
            switch (request.getMethod()) {
                default:
                    throw new MethodNotAllowedException("Action not allowed for this resource!");
            }
        } else if (match(request.getResource(), Path.RESOURCE_FUNCTION_SPOOL_ITEM.toString())) {
            Map<String, String> map = extractParams(request.getResource(), Path.RESOURCE_FUNCTION_SPOOL_ITEM.toString());
            String urlId = map.get(Path.ID1.toString());
            //omcp://sum.function/spool/{id}
            switch (request.getMethod()) {
                case DELETE:
                    long id = Long.valueOf(urlId);
                    deleteItem(id);
                    Response response = new ResponseBuilder().ok().build();
                    return response;
                default:
                    throw new MethodNotAllowedException("Action not allowed for this resource!");
            }
        } else if (match(request.getResource(), Path.RESOURCE_FUNCTION_INTERFACE.toString())) {
            //omcp://sum.function/interface/
            switch (request.getMethod()) {
                case GET:
                    InterfaceFnTo to = getInterface();
                    Response response = new ResponseBuilder().ok(to, contentType).build();
                    return response;
                default:
                    throw new MethodNotAllowedException("Action not allowed for this resource!");
            }
        }
        return null;
    }

    private synchronized ResponseFnTo execute(RequestFnTo requestFnTo, Calendar calendar) throws NotFoundException, InternalServerErrorException, BadRequestException {
        List<Double> values = getValuesFromRequest(requestFnTo);
        Double result = calculate(values);
        return createResponseFnTo(result, calendar);
    }

    private double calculate(List<Double> values) throws BadRequestException, InternalServerErrorException {
        if(values == null || values.isEmpty()) throw new BadRequestException("Failed to calculate the sum: empty/null list of values");
        double result;
        try {
            Interpreter interpreter = new Interpreter();
            interpreter.set("result", 0.0);
            interpreter.set("value", 0.0);
            interpreter.set("values", values);
            interpreter.set("total", values.size());
            result = (double) interpreter.eval(this.implementation);
        } catch (EvalError evalError) {
            throw new InternalServerErrorException("Failed to calculate: " + evalError.getMessage());
        }
        return result;
    }

    public synchronized long create(RequestFnTo requestFnTo, Calendar calendar) throws NotFoundException, BadRequestException, InternalServerErrorException {
        String responseTo = requestFnTo.getResponseTo();
        if (responseTo == null || responseTo.isEmpty()) {
            throw new BadRequestException("Async mode needs address to response message!");
        } else {
            try {
                URI uri = new URI(responseTo);
            } catch (Exception e) {
                throw new BadRequestException("Address to response message is wrong!");
            }
        }
        List<Double> doubles = getValuesFromRequest(requestFnTo);
        Double result = calculate(doubles);
        ResponseFnTo responseFnTo = createResponseFnTo(result, calendar);

        try {
            client.doPost(responseTo, responseFnTo);
        } catch (Exception e) {
            throw new InternalServerErrorException("Cannot response the request!");
        }

        return 0;
    }

    public InterfaceFnTo getInterface() {
        return this.interfaceFnTo;
    }

    public boolean deleteItem(long id) {
        return false;
    }

    private ResponseFnTo createResponseFnTo(Double value, Calendar calendar) {
        List<ValueFnTo> list = new ArrayList<>();
        list.add(new SingleValueFnTo(this.interfaceFnTo.getResponseParams().get(0).getName(), String.valueOf(value)));
        ResponseFnTo responseFnTo = new ResponseFnTo(calendar.getTimeInMillis(), System.currentTimeMillis(), list);
        return responseFnTo;
    }

    private List<Double> getValuesFromUriParams(Map<String, String> params) throws BadRequestException {
        final String key = this.interfaceFnTo.getRequestParams().get(0).getName();
        try {
            if (params.containsKey(key)) {
                String paramValue = params.get(key);
                if (paramValue.contains("[") && paramValue.contains("]") && paramValue.contains(",")) {
                    String[] values = paramValue.replace("[", "").replace("]", "").split(",");
                    List<String> stringValues = Arrays.asList(values);
                    List<Double> list = getDoubleValues(stringValues);
                    return list;
                }
            }
            throw new RuntimeException();
        } catch (RuntimeException re) {
            throw new BadRequestException("Params cannot be null!");
        }
    }

    private List<Double> getValuesFromRequest(RequestFnTo requestFnTo) throws BadRequestException {
        try {
            List<ValueFnTo> valueFnTos = requestFnTo.getValues();
            ValueFnTo valueFnTo = valueFnTos.get(0);
            List<String> stringValues = valueFnTo.getValues();
            List<Double> values = getDoubleValues(stringValues);
            return values;
        } catch (RuntimeException ex) {
            throw new BadRequestException("Params cannot be null!");
        }
    }

    private List<Double> getDoubleValues(List<String> values) {
        List<Double> list = new ArrayList<>();
        for (String value : values) {
            list.add(Double.valueOf(value.trim()));
        }
        return list;
    }
}
