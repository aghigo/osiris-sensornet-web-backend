package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.omcp.common.exceptions.NotFoundException;
import br.uff.labtempo.osiris.factory.function.FunctionModuleFactory;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.domain.function.FunctionModule;
import br.uff.labtempo.osiris.model.request.FunctionRequest;
import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service class with business rules to select/create/select/remove FunctionModuleFactory from FunctionModuleFactory modules
 * @see InterfaceFnTo
 * @see FunctionVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class FunctionService {
    @Autowired
    private FunctionDataRepository functionDataRepository;

    @Autowired
    private FunctionModuleRepository functionModuleRepository;

    @Autowired
    private BlendingRepository blendingRepository;

    @Autowired
    private VsnFunctionRepository vsnFunctionRepository;

    @Autowired
    private FunctionModuleFactory functionModuleFactory;

    /**
     * Get all interfaces from all available function modules
     * @return List<InterfaceFnTo>
     * @throws AbstractRequestException
     */
    public List<InterfaceFnTo> getAllInterfaces() throws AbstractRequestException {
        Iterable<FunctionData> functionDataIterable = this.functionDataRepository.findAll();
        Iterator<FunctionData> functionDataIterator = functionDataIterable.iterator();
        List<InterfaceFnTo> interfaceFnToList = new ArrayList<>();
        while(functionDataIterator.hasNext()) {
            FunctionData functionData = functionDataIterator.next();
            InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(functionData.getName());
            interfaceFnToList.add(interfaceFnTo);
        }
        return interfaceFnToList;
    }

    /**
     * Get a function module interface by its name
     * @param functionName
     * @return InterfaceFnTo
     * @throws AbstractRequestException
     */
    public InterfaceFnTo getInterfaceByName(String functionName) throws AbstractRequestException {
        List<FunctionData> functionDataList = this.functionDataRepository.findByName(functionName);
        if(functionDataList.isEmpty()) {
            throw new NotFoundException(String.format("No function interface found for name %s", functionName));
        }
        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(functionName);
        return interfaceFnTo;
    }

    /**
     * Persist function data into Osiris web database
     * @param functionRequest
     * @throws URISyntaxException
     * @throws AbstractRequestException
     * @throws IllegalArgumentException
     */
    public URI createFunctionData(FunctionRequest functionRequest) throws URISyntaxException, AbstractRequestException, IllegalArgumentException {
        List<FunctionData> functionDataList = this.functionDataRepository.findByName(functionRequest.getFunctionName().trim().toLowerCase());
        if(functionDataList.isEmpty()) {
            FunctionModule functionModule = this.functionModuleFactory.getInstance(functionRequest.getFunctionName(), functionRequest.getDescription(), functionRequest.getImplementation(), functionRequest.getDataTypeId());
            this.functionDataRepository.save(functionModule.getFunctionData());
            return new URI(functionModule.getFunctionData().getInterfaceUri());
        } else {
            throw new BadRequestException(String.format("Failed to create function module: Function module with name '%s' already exists.", functionRequest.getFunctionName()));
        }
    }

    /**
     * Get all FunctionData from Osiris Web database
     * @see FunctionData
     * @return List<FunctionData>
     */
    public List<FunctionData> getAllFunctionData() throws NotFoundException {
        Iterable<FunctionData> functionDataIterable = this.functionDataRepository.findAll();
        Iterator<FunctionData> functionDataIterator = functionDataIterable.iterator();
        List<FunctionData> functionDataList = new ArrayList<>();
        while(functionDataIterator.hasNext()) {
            functionDataList.add(functionDataIterator.next());
        }
        return functionDataList;
    }

    /**
     * Get function data from osiris web applicaton database
     * @param functionName
     * @return FunctionData
     * @throws NotFoundException
     */
    public FunctionData getFunctionData(String functionName) throws NotFoundException {
        Iterable<FunctionData> functionDataIterable = this.functionDataRepository.findAll();
        Iterator<FunctionData> functionDataIterator = functionDataIterable.iterator();
        while(functionDataIterator.hasNext()) {
            FunctionData functionData = functionDataIterator.next();
            if(functionData.getName().equals(functionName.trim().toLowerCase())) {
                return functionData;
            }
        }
        throw new NotFoundException(String.format("No function module data found for name '%s'.", functionName));
    }

    /**
     * Remove data of function module from osiris web application database
     * @param functionName
     * @throws AbstractRequestException
     */
    public void deleteFunctionData(String functionName) throws AbstractRequestException {
        List<FunctionData> functionDataList = this.functionDataRepository.findByName(functionName);
        if(functionDataList != null && !functionDataList.isEmpty()) {
            List<BlendingVsnTo> blendingVsnToList = this.blendingRepository.getAll();
            for(BlendingVsnTo blendingVsnTo : blendingVsnToList) {
                FunctionVsnTo functionVsnTo = this.vsnFunctionRepository.getById(blendingVsnTo.getFunctionId());
                if(functionVsnTo.getName().equals(functionName)) {
                    throw new BadRequestException(String.format("Failed to remove function %s data: function %s module is being used by one or more blending sensors.", functionName));
                }
            }
            this.functionDataRepository.delete(functionDataList.get(0));
        }
    }
}
