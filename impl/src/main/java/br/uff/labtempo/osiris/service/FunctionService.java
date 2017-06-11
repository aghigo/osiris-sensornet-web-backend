package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.omcp.common.exceptions.NotFoundException;
import br.uff.labtempo.osiris.factory.function.FunctionFactory;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.request.FunctionRequest;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.repository.FunctionDataRepository;
import br.uff.labtempo.osiris.repository.FunctionModuleRepository;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.function.ParamFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service class with business rules to select/create/select/remove FunctionFactory from FunctionFactory modules
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
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private FunctionModuleRepository functionModuleRepository;
    private List<InterfaceFnTo> allFunctionData;

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
            DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(functionRequest.getDataTypeId());
            List<ParamFnTo> requestParams = new ArrayList<>();
            requestParams.add(new ParamFnTo(dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getUnit(), ValueType.NUMBER, true));

            List<ParamFnTo> responseParams = new ArrayList<>();
            responseParams.add(new ParamFnTo(functionRequest.getFunctionName().trim().toLowerCase(), dataTypeVsnTo.getUnit(), ValueType.NUMBER, false));

            FunctionFactory functionFactory = new FunctionFactory(functionRequest.getFunctionName().trim().toLowerCase(),
                    functionRequest.getDescription(), functionRequest.getImplementation(), requestParams, responseParams);

            FunctionData functionData = FunctionData.builder()
                    .name(functionRequest.getFunctionName())
                    .fullName(functionFactory.getFullName())
                    .description(functionRequest.getDescription())
                    .implementation(functionRequest.getImplementation())
                    .dataUri(functionFactory.getDataUri())
                    .omcpUri(functionFactory.getOmcpUri())
                    .interfaceUri(functionFactory.getInterfaceUri())
                    .operation(functionFactory.getOperation())
                    .dataTypeId(dataTypeVsnTo.getId())
                    .build();

            this.functionDataRepository.save(functionData);
            return new URI(functionData.getInterfaceUri());
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
        if(functionDataList.isEmpty()) {
            throw new NotFoundException("No function module found.");
        }
        return functionDataList;
    }

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

    public void deleteFunctionData(String functionName) {
        //verify if functionName already exist
        //if exist
            //verify ir there are blendings using it
    }
}
