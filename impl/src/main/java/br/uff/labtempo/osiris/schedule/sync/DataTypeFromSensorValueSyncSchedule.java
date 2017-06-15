package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.factory.function.FunctionModuleFactory;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.domain.function.FunctionModule;
import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Perform synchonizations between DataTypes and Sensors from SensorNet
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@EnableScheduling
@Service
@Slf4j
@Profile("datatype_sync_schedule")
public class DataTypeFromSensorValueSyncSchedule {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    /**
     * Synchronize SensorNet Sensors Values with VirtualSensorNet DataTypes
     * Search for all Sensors from SensorNet, for each sensor, check for its ValueTos
     * for each ValueTo, compare with all VirtualSensorNet DataTypes
     * if not found any equivalent, creates a correspondent DataType on VirtualSensorNet
     * @throws AbstractRequestException
     * @throws URISyntaxException
     */
    @Scheduled(cron = "${sensornet.schedule.sync.datatype.cron:*/5 * * * * ?}")
    public void createDataTypesFromSensorValues() throws AbstractRequestException, URISyntaxException {
        List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
        boolean found = false;
        log.info("Beginning synchronization between SensorsNet sensors values and VirtualSensorNet DataTypes...");
        for(NetworkSnTo networkSnTo : networkSnToList) {
            List<SensorSnTo> sensorSnToList = this.sensorRepository.getAllByNetworkId(networkSnTo.getId());
            for(SensorSnTo sensorSnTo : sensorSnToList) {
                for(ValueTo valueTo : sensorSnTo.getValuesTo()) {
                    for(DataTypeVsnTo dataTypeVsnTo : dataTypeVsnToList) {
                        if(dataTypeVsnTo.getDisplayName().equals(valueTo.getName().trim().toLowerCase())
                                && dataTypeVsnTo.getType().equals(valueTo.getType())
                                && dataTypeVsnTo.getUnit().equals(valueTo.getUnit().trim().toLowerCase())
                                && dataTypeVsnTo.getSymbol().equals(valueTo.getSymbol().trim().toLowerCase())) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(valueTo.getName().trim().toLowerCase(), valueTo.getType(), valueTo.getUnit().trim().toLowerCase(), valueTo.getSymbol().trim().toLowerCase());
                        dataTypeVsnTo.setUsedBy(0);
                        this.dataTypeRepository.insert(dataTypeVsnTo);
                        log.info(String.format("DataType created (%s, %s, %s, %s)", dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getType(), dataTypeVsnTo.getUnit(), dataTypeVsnTo.getSymbol()));
                    } else {
                        found = false;
                    }
                }
            }
        }
    }
}
