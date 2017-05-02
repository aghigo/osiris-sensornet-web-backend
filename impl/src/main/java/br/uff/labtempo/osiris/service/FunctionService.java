package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.configuration.FunctionConfig;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class with business rules to select/create/select/remove Function from Function and VirtualSensorNet modueles
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class FunctionService {

    @Autowired
    private FunctionConfig functionConfig;

    @Autowired
    private FunctionRepository functionRepository;

    /**
     * Get a list of all available Functions URI locations
     * @return List of String with Function URI locations
     */
    public List<String> getAvailableFunctionsUri() {
        List<String> functionUriList = new ArrayList<>();
        for(String functionName : functionConfig.getFunctionNames()) {
            functionUriList.add(String.format(functionConfig.getFunctionUri(), functionName));
        }
        return functionUriList;
    }

    /**
     * Get a function from VirtualSensorNet module by its name
     * @see FunctionVsnTo
     * @param functionName
     * @return FunctionVsnTo
     * @throws AbstractRequestException
     */
    public FunctionVsnTo getByName(String functionName) throws AbstractRequestException {
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
