package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.mapper.VsnFunctionMapper;
import br.uff.labtempo.osiris.model.request.VsnFunctionRequest;
import br.uff.labtempo.osiris.repository.VsnFunctionRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Service layer with business rules to handle Functions from VirtualSensorNet modules
 * @see FunctionVsnTo
 * @author andre.ghigo
 * @version 1.0.0
 * @since 10/06/17.
 */
@Service
public class VsnFunctionService {

    @Autowired
    private VsnFunctionRepository vsnFunctionRepository;

    /**
     * Get all Functions from VirtualSensorNet module
     * @return List<FunctionVsnTo>
     * @throws AbstractRequestException
     */
    public List<FunctionVsnTo> getAllFunctionsFromVirtualSensorNet() throws AbstractRequestException {
        return this.vsnFunctionRepository.getAll();
    }

    /**
     * Get a function from VirtualSensorNet module by its id
     * @param functionId
     * @return FunctionVsnTo
     * @throws AbstractRequestException
     */
    public FunctionVsnTo getFunctionFromVirtualSensorNetById(long functionId) throws AbstractRequestException {
        return this.vsnFunctionRepository.getById(functionId);
    }

    /**
     * Creates a new FunctionModuleFactory into VirtualSensorNet module
     * by FunctionModuleFactory module interface
     * @param interfaceFnTo
     * @return URI with new resource location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI createFunctionOnVirtualSensorNet(InterfaceFnTo interfaceFnTo) throws URISyntaxException, AbstractRequestException {
        FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
        return this.vsnFunctionRepository.create(functionVsnTo);
    }

    /**
     * Creates a new FunctionModuleFactory into VirtualSensorNet module
     * @param functionVsnTo
     * @return URI with new resource location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI createFunctionOnVirtualSensorNet(FunctionVsnTo functionVsnTo) throws URISyntaxException, AbstractRequestException {
        return this.vsnFunctionRepository.create(functionVsnTo);
    }

    /**
     * Updates an existing function from VirtualSensorNet module
     * by a FunctionModuleFactory interface
     * @param functionId
     * @param interfaceFnTo
     * @throws AbstractRequestException
     */
    public void updateFunctionFromVirtualSensorNetById(long functionId, InterfaceFnTo interfaceFnTo) throws AbstractRequestException {
        FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
        this.vsnFunctionRepository.update(functionId, functionVsnTo);
    }

    /**
     * Updates an existing function from VirtualSensorNet module
     * @param functionId
     * @param functionVsnTo
     * @throws AbstractRequestException
     */

    public void updateFunctionFromVirtualSensorNetById(long functionId, FunctionVsnTo functionVsnTo) throws AbstractRequestException {
        this.vsnFunctionRepository.update(functionId, functionVsnTo);
    }

    /**
     * Remove an existing function from VirtualSensorNet by its id
     * @param functionId
     * @throws AbstractRequestException
     */
    public void deleteFunctionFromVirtualSensorNetById(long functionId) throws AbstractRequestException {
        this.vsnFunctionRepository.delete(functionId);
    }

    /**
     * Creates a new FunctionVsnTo into VirtualSensorNet module
     * by request body of the Controller API
     * @param vsnFunctionRequest
     * @return URI with new resource location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI createFunctionOnVirtualSensorNet(VsnFunctionRequest vsnFunctionRequest) throws URISyntaxException, AbstractRequestException {
        FunctionVsnTo functionVsnTo = VsnFunctionMapper.requestToVsnTo(vsnFunctionRequest);
        return this.createFunctionOnVirtualSensorNet(functionVsnTo);
    }

    /**
     * Updates an existing FunctionVsnTo from VirtualSensorNet module
     * based on a request passed by the Controller API
     * @param functionId
     * @param vsnFunctionRequest
     * @throws AbstractRequestException
     */
    public void updateFunctionFromVirtualSensorNetById(long functionId, VsnFunctionRequest vsnFunctionRequest) throws AbstractRequestException {
        FunctionVsnTo functionVsnTo = VsnFunctionMapper.requestToVsnTo(vsnFunctionRequest);
        this.updateFunctionFromVirtualSensorNetById(functionId, functionVsnTo);
    }
}
