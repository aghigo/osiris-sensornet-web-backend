package br.uff.labtempo.osiris.schedule;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Initialize VirtualSensorNet module with default data
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@EnableScheduling
@Service
@Slf4j
public class VirtualSensorNetLoaderSchedule {
    @Autowired
    private DataTypeRepository dataTypeRepository;

    private boolean hasCalledBefore = false;

    @Scheduled(fixedDelay = 99999999)
    public void loadDataTypes() throws AbstractRequestException, URISyntaxException {
        if(hasCalledBefore) return;
        List<DataTypeVsnTo> dataTypeVsnToList = dataTypeRepository.getAll();
        if(dataTypeVsnToList == null || dataTypeVsnToList.isEmpty()) {
            log.info("Loading default DataTypes into VirtualSensorNet module...");
            dataTypeRepository.insert(new DataTypeVsnTo("temperature", ValueType.NUMBER, "celsius", "ºC"));
            dataTypeRepository.insert(new DataTypeVsnTo("temperature", ValueType.NUMBER, "fahrenheit", "ºF"));
            dataTypeRepository.insert(new DataTypeVsnTo("temperature", ValueType.NUMBER, "kelvin", "K"));
            dataTypeRepository.insert(new DataTypeVsnTo("luminosity", ValueType.NUMBER, "candela", "C"));
            dataTypeRepository.insert(new DataTypeVsnTo("pressure", ValueType.NUMBER, "pascal", "P"));
            dataTypeRepository.insert(new DataTypeVsnTo("boolean", ValueType.LOGIC, "boolean", "(boolean)"));
            dataTypeRepository.insert(new DataTypeVsnTo("length", ValueType.NUMBER, "meter", "m"));
            dataTypeRepository.insert(new DataTypeVsnTo("mass", ValueType.NUMBER, "kilogram", "kg"));
            dataTypeRepository.insert(new DataTypeVsnTo("time", ValueType.NUMBER, "second", "s"));
            dataTypeRepository.insert(new DataTypeVsnTo("electric current", ValueType.NUMBER, "ampere", "A"));
            dataTypeRepository.insert(new DataTypeVsnTo("frequency", ValueType.NUMBER, "hertz", "Hz"));
            dataTypeRepository.insert(new DataTypeVsnTo("energy", ValueType.NUMBER, "energy", "J"));
            dataTypeRepository.insert(new DataTypeVsnTo("power", ValueType.NUMBER, "watt", "W"));
            dataTypeRepository.insert(new DataTypeVsnTo("voltage", ValueType.NUMBER, "volt", "V"));
            hasCalledBefore = true;
        }
        log.info("DataTypes loaded succesfully.");
    }
}
