package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.osiris.factory.function.FunctionModuleFactory;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.domain.function.FunctionModule;
import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class LinkFromSensorSyncSchedule {

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

    @Autowired
    private FunctionModuleFactory functionModuleFactory;

    private Map<FunctionModule, Thread> functionModuleThreadMap = new HashMap<>();

    /**
     * Synchronize all Sensors from SensorNet with Link sensors on VirtualSensorNet
     * Verify each sensor from SensorNet
     * if does not exist any associated virtual Link sensor, create on VirtualSensorNet
     * a new Link sensor with Sensor data (sensor id, collector id, network id).
     */
    @Scheduled(cron = "${sensornet.schedule.sync.link.cron:*/2 * * * * ?}")
    public void createLinkSensorsFromSensorNetSensors() throws Exception {
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
}
