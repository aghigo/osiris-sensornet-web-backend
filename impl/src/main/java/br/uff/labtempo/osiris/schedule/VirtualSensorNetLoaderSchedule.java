package br.uff.labtempo.osiris.schedule;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.repository.NetworkRepository;
import br.uff.labtempo.osiris.repository.SensorRepository;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
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
import java.util.List;

/**
 * Load VirtualSensorNet module with default data
 * Perform synchonizations between SensorNet and VirtualSensorNet
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
    private NetworkRepository networkRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkGenerator linkGenerator;

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

    /**
     * Synchronize SensorNet Sensors Values with VirtualSensorNet DataTypes
     * Search for all Sensors from SensorNet, for each sensor, check for its ValueTos
     * for each ValueTo, compare with all VirtualSensorNet DataTypes
     * if not found any equivalent, creates a correspondent DataType on VirtualSensorNet
     * @throws AbstractRequestException
     * @throws URISyntaxException
     */
    @Scheduled(cron = "* */10 * * * ?")
    public void syncSensorValuesWithDataTypes() throws AbstractRequestException, URISyntaxException {
        List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
        boolean found = false;
        log.info("Beginning sync between SensorsNet sensors values and VirtualSensorNet DataTypes");
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

    /**
     * Synchronize all Sensors from SensorNet with Link sensors on VirtualSensorNet
     */
    @Scheduled(cron = "* */10 * * * ?")
    public void syncSensorsWithLinks() throws AbstractRequestException, URISyntaxException {
        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        boolean found = false;
        log.info("Beginning sync between SensorsNet sensors and VirtualSensorNet Link sensors");
        for(NetworkSnTo networkSnTo : networkSnToList) {
            List<SensorSnTo> sensorSnToList = this.sensorRepository.getAllByNetworkId(networkSnTo.getId());
            for(SensorSnTo sensorSnTo : sensorSnToList) {
                for(LinkVsnTo linkVsnTo : linkVsnToList) {
                    if(linkVsnTo.getSensorId().equals(sensorSnTo.getId())) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    LinkVsnTo linkVsnTo = this.linkGenerator.generateVsnTo(sensorSnTo);
                    this.linkRepository.save(linkVsnTo);
                    log.info(String.format("Link sensor created based on SensorNet sensor [%s]", sensorSnTo.getId()));
                } else {
                    found = false;
                }
            }
        }
    }
}
