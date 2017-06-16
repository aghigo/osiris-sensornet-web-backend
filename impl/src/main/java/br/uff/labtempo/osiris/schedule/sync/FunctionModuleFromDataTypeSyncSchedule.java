package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.factory.function.FunctionModuleFactory;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.domain.function.FunctionModule;
import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Perform synchonizations between Function Modules and DataTypes
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Slf4j
public class FunctionModuleFromDataTypeSyncSchedule {

    @Autowired
    private FunctionDataRepository functionDataRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private FunctionModuleFactory functionModuleFactory;

    private final String FUNCTION_DESCRIPTION_TEMPLATE = "Function that calculates the %s of %s in %s";

    private final Map<String, String> functionImplMap = ImmutableMap.of(
                    "sum", "result = 0; for(Double value : values) { result = result + value; } return result;",
                    "average", "result = 0; for(Double value : values) { result = result + value; } result = result / total; return result;",
                    "min", "result = 0; for(Double value : values) { result = Math.min(result, value); } return result;",
                    "max", "result = 0; for(Double value : values) { result = Math.max(result, value); } return result;");

    /**
     * Creates new Function Modules based on available DataTypes from VirtualSensorNet module
     * (if does not exist yet).
     */
    //@Scheduled(cron = "${sensornet.schedule.sync.function.cron:*/5 * * * * ?}")
    public void createFunctionModulesFromDataTypes() throws AbstractRequestException {
        List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
        Iterator<FunctionData> functionDataIterator = this.functionDataRepository.findAll().iterator();
        for(String functionOperation : functionImplMap.keySet()) {
            for(DataTypeVsnTo dataTypeVsnTo : dataTypeVsnToList) {
                boolean found = false;
                while(functionDataIterator.hasNext()) {
                    FunctionData functionData = functionDataIterator.next();
                    String functionName = functionOperation + "." + dataTypeVsnTo.getDisplayName() + "." + dataTypeVsnTo.getUnit();
                    if(functionData.getName().equals(functionName)) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    String functionDescription = String.format(FUNCTION_DESCRIPTION_TEMPLATE, functionOperation, dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getUnit());
                    FunctionModule functionModule = this.functionModuleFactory.getInstance(functionOperation,functionDescription, this.functionImplMap.get(functionOperation), dataTypeVsnTo.getId());
                    this.functionDataRepository.save(functionModule.getFunctionData());
                }
            }
        }
    }
}
