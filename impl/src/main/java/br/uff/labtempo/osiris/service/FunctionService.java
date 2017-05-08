package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.configuration.AvailableFunctionListConfig;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class with business rules to select/create/select/remove Function from Function and VirtualSensorNet modueles
 * @see InterfaceFnTo
 * @see FunctionVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class FunctionService {

    @Autowired
    private AvailableFunctionListConfig availableFunctionListConfig;

    @Autowired
    private FunctionRepository functionRepository;

    /**
     * Get a list of all available Functions URI locations
     * @return List of String with Function URI locations
     */
    public List<String> getAvailableFunctionsUri() {
        List<String> functionUriList = new ArrayList<>();
        for(String functionName : availableFunctionListConfig.getFunctionNames()) {
            functionUriList.add(String.format(availableFunctionListConfig.getFunctionUri(), functionName));
        }
        return functionUriList;
    }

    /**
     * Get a list of Available Functions modules Interfaces
     * @see InterfaceFnTo
     * @return List<InterfaceFnTo>
     * @throws AbstractRequestException
     */
    public List<InterfaceFnTo> getAvailableFunctionsInterface() {
        List<InterfaceFnTo> interfaceFnToList = new ArrayList<>();
        for(String functionName : availableFunctionListConfig.getFunctionNames()) {
            InterfaceFnTo interfaceFnTo = null;
            try {
                interfaceFnTo = this.getInterface(functionName);
                interfaceFnToList.add(interfaceFnTo);
            } catch (Exception e) {

            }
        }
        return interfaceFnToList;
    }

    /**
     * Get a function from VirtualSensorNet module by its name
     * @see FunctionVsnTo
     * @param functionName
     * @return FunctionVsnTo
     * @throws AbstractRequestException
     */
    public FunctionVsnTo getFromVirtualSensorNetByName(String functionName) throws AbstractRequestException {
        return this.functionRepository.getFromVirtualSensorNet(functionName);
    }

    /**
     * Get function interface from Function Module by its name
     * @see InterfaceFnTo
     * @param functionName
     * @return InterfaceFnTo
     * @throws AbstractRequestException
     */
    public InterfaceFnTo getInterface(String functionName) throws AbstractRequestException {
        return this.functionRepository.getInterface(functionName);
    }

    /**
     * Get function from VirtualSensorNet module by its name
     * @see FunctionVsnTo
     * @param functionName
     * @return FunctionVsnTo
     * @throws AbstractRequestException
     */
    public FunctionVsnTo getFromVirtualSensorNet(String functionName) throws AbstractRequestException {
        return this.functionRepository.getFromVirtualSensorNet(functionName);
    }

    public List<FunctionVsnTo> getAllFromVirtualSensorNet() throws AbstractRequestException {
        List<FunctionVsnTo> functionVsnToList = this.functionRepository.getAllFromVirtualSensorNet();
        return functionVsnToList;
    }
}
