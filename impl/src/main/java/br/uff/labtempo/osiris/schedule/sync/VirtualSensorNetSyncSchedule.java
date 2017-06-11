package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.factory.function.FunctionFactory;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.repository.*;
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
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

/**
 * Perform synchonizations between SensorNet and VirtualSensorNet
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@EnableScheduling
@Service
@Slf4j
@Profile("virtualsensornet_sync_schedule")
public class VirtualSensorNetSyncSchedule {

    @Autowired
    private FunctionDataRepository functionDataRepository;

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

    private List<FunctionFactory> runningFunctionModuleList;

    /**
     * Synchronize SensorNet Sensors Values with VirtualSensorNet DataTypes
     * Search for all Sensors from SensorNet, for each sensor, check for its ValueTos
     * for each ValueTo, compare with all VirtualSensorNet DataTypes
     * if not found any equivalent, creates a correspondent DataType on VirtualSensorNet
     * @throws AbstractRequestException
     * @throws URISyntaxException
     */
    @Scheduled(cron = "${sensornet.schedule.sync.datatype.cron:* */60 * * * ?}")
    public void syncSensorValueWithDataType() throws AbstractRequestException, URISyntaxException {
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
        log.info("Datatype synchronization completed.");
    }

    /**
     * Synchronize all Sensors from SensorNet with Link sensors on VirtualSensorNet
     * Verify each sensor from SensorNet
     * if does not exist any associated virtual Link sensor, create on VirtualSensorNet
     * a new Link sensor with Sensor data (sensor id, collector id, network id).
     */
    @Scheduled(cron = "${sensornet.schedule.sync.link.cron:* */10 * * * ?}")
    public void syncSensorWithLink() throws Exception {
        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        boolean found = false;
        log.info("Beginning synchronization between SensorsNet sensors and VirtualSensorNet Link sensors...");
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
                    log.info(String.format("Link sensor [%s] created based on SensorNet sensor [%s]", linkVsnTo.getId(), sensorSnTo.getId()));
                } else {
                    found = false;
                }
            }
        }
        log.info("Link synchronization completed.");
    }

    /**
     * Creates Composite sensors on VirtualSensorNet based on
     * VirtualSensorNet Link sensor compositions
     * @throws Exception
     */
    @Scheduled(cron = "${sensornet.schedule.sync.composite.cron:* */10 * * * ?}")
    public void syncLinkWithComposite() throws Exception {
        log.info("Beginnning synchronization between Link and Composite sensors...");
        log.info("Composite synchronization completed...");
    }

    /**
     * Synchronize Function data on Osiris Web with Function Modules on OSIRIS Framework
     * Checks every function on OSIRIS Web Database and check if its running or not.
     * If not running, starts the corresponding OmcpServer
     * If a running Function module does not exist on Osiris web database, stop the function module
     * because it was removed by another service.
     * @throws Exception
     */
    @Scheduled(cron = "${sensornet.schedule.sync.function.cron:* */10 * * * ?}")
    public void syncFunctionDataWithFunctionModules() throws Exception {
        Iterable<FunctionData> functionDataInterable = this.functionDataRepository.findAll();
        Iterator<FunctionData> functionDataIterator = functionDataInterable.iterator();
        log.info("Beginning synchornization between Function modules...");
        for(FunctionFactory functionFactory : this.runningFunctionModuleList) {
            while(functionDataIterator.hasNext()) {
                //se existe no banco
                    //se estiver rodando entao OK
                    //se estiver pausado entao start
                //se nao existe no banco
                    //se estiver rodando entao stop e remove
                    //se estiver pausado entao remove
            }
        }
        log.info("Function module synchronization completed.");
    }

    /**
     * Synchronize every Function Module with Blending Sensors.
     * Creates a new Blending sensor for each Function Module (Running) and DataType (with available virtual sensors)
     *
     * Example:
     *
     * available functions: average, sum
     * available virtual sensors with the following datatypes: temperature (Cº), pressure (P)
     *
     * Result:
     *
     * creates one blending for the average of all temperatures sensors in Cº (celsius)
     * creates one blending for the sum of all temperatures sensors in Cº (celsius)
     * creates one blending for the average of all pressures sensors in P (pascal)
     * creates one blending for the sum of all pressures sensors in P (pascal)
     *
     * @throws Exception
     */
    @Scheduled(cron = "${sensornet.schedule.sync.blending.cron:* */10 * * * ?}")
    public void syncVSensorWithBlendingAndFunction() throws Exception {
        log.info("Beginning synchronization between VSensors and Blendings...");
        log.info("Blending synchronization completed.");
    }
}
