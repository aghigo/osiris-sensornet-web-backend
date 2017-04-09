package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FunctionService {

    @Autowired
    private FunctionRepository functionRepository;

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
