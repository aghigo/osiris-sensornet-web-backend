package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
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

import java.net.URISyntaxException;
import java.util.ArrayList;
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

    /**
     * Get all interfaces from all available function modules
     * @return List<InterfaceFnTo>
     * @throws AbstractRequestException
     */
    public List<InterfaceFnTo> getAllInterfaces() throws AbstractRequestException {
        List<InterfaceFnTo> interfaceFnToList = this.functionModuleRepository.getAllInterfaces();
        return interfaceFnToList;
    }

    /**
     * Get a function module interface by its name
     * @param functionName
     * @return InterfaceFnTo
     * @throws AbstractRequestException
     */
    public InterfaceFnTo getInterfaceByName(String functionName) throws AbstractRequestException {
        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(functionName);
        return interfaceFnTo;
    }

    public void createFunctionData(FunctionRequest functionRequest) throws URISyntaxException, AbstractRequestException, IllegalArgumentException {
        List<FunctionData> functionDataList = this.functionDataRepository.findByName(functionRequest.getFunctionName());
        if(functionDataList.isEmpty()) {
            DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(functionRequest.getDataTypeId());
            List<ParamFnTo> requestParams = new ArrayList<>();
            requestParams.add(new ParamFnTo(dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getUnit(), ValueType.NUMBER, true));

            List<ParamFnTo> responseParams = new ArrayList<>();
            responseParams.add(new ParamFnTo(functionRequest.getFunctionName(), dataTypeVsnTo.getUnit(), ValueType.NUMBER, false));

            FunctionFactory functionFactory = new FunctionFactory(functionRequest.getFunctionName(),
                    functionRequest.getDescription(), functionRequest.getImplementation(), requestParams, responseParams);

            FunctionData functionData = FunctionData.builder()
                    .name(functionRequest.getFunctionName())
                    .fullName(functionFactory.getInterfaceFnTo().getName())
                    .description(functionRequest.getDescription())
                    .implementation(functionRequest.getImplementation())
                    .moduleUri(functionFactory.getInterfaceFnTo().getAddress())
                    .operation(functionFactory.getOperation())
                    .requestParamName(functionRequest.getRequestParamName())
                    .responseParamName(functionRequest.getResponseParamName())
                    .type(functionFactory.getType())
                    .unit(functionRequest.getUnit())
                    .build();

            this.functionDataRepository.save(functionData);
        } else {
            throw new BadRequestException(String.format("Failed to create function module: Function module %s already exists.", functionRequest.getFunctionName()));
        }
    }
}
