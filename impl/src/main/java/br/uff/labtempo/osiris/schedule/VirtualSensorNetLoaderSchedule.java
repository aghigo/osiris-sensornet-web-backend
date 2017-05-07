package br.uff.labtempo.osiris.schedule;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Load VirtualSensorNet module with default data
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@EnableScheduling
@Service
@Slf4j
@Profile("virtualsensornet_loader_schedule")
public class VirtualSensorNetLoaderSchedule {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    private boolean hasLoadedDataTypes = false;

    /**
     * Load default DataTypes on VirtualSensorNet
     * @throws AbstractRequestException
     * @throws URISyntaxException
     */
    @Scheduled(cron = "* */10 * * * ?")
    public void loadDefaultDataTypes() throws AbstractRequestException, URISyntaxException {
        if(!hasLoadedDataTypes) {
            List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
            if(dataTypeVsnToList == null || dataTypeVsnToList.isEmpty()) {
                log.info("Loading default DataTypes on VirtualSensorNet module");
                this.dataTypeRepository.insert(new DataTypeVsnTo("temperature", ValueType.NUMBER,"celsius", "ºC"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("temperature", ValueType.NUMBER, "fahrenheit", "ºF"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("temperature", ValueType.NUMBER, "kelvin", "K"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("luminosity", ValueType.NUMBER, "candela", "C"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("pressure", ValueType.NUMBER, "pascal", "P"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("boolean", ValueType.LOGIC, "boolean", "(boolean)"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("length", ValueType.NUMBER, "meter", "m"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("mass", ValueType.NUMBER, "kilogram", "kg"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("time", ValueType.NUMBER, "second", "s"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("electric current", ValueType.NUMBER, "ampere", "A"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("frequency", ValueType.NUMBER, "hertz", "Hz"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("energy", ValueType.NUMBER, "energy", "J"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("power", ValueType.NUMBER, "watt", "W"));
                this.dataTypeRepository.insert(new DataTypeVsnTo("voltage", ValueType.NUMBER,"volt", "V"));
                log.info("Default DataTypes loaded successfully on VirtualSensorNet");
            }
        }
    }
}
