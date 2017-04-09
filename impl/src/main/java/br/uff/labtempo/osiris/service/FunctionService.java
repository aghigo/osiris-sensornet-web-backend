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

@Service
public class FunctionService {

    @Autowired
    private FunctionConfig functionConfig;

    @Autowired
    private FunctionRepository functionRepository;

    public List<String> getAvailableFunctionsUri() {
        List<String> functionUriList = new ArrayList<>();
        for(String functionName : functionConfig.getFunctionNames()) {
            functionUriList.add(String.format(functionConfig.getFunctionUri(), functionName));
        }
        return functionUriList;
    }

    public FunctionVsnTo getByName(String functionName) throws AbstractRequestException {
        return this.functionRepository.getFromVirtualSensorNet(functionName);
    }

    public InterfaceFnTo getInterface(String functionName) throws AbstractRequestException {
        return this.functionRepository.getInterface(functionName);
    }

    public FunctionVsnTo getFromVirtualSensorNet(String functionName) throws AbstractRequestException {
        return this.functionRepository.getFromVirtualSensorNet(functionName);
    }
}
