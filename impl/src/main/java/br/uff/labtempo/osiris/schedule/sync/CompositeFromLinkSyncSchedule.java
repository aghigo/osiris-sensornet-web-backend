package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.osiris.factory.function.FunctionModuleFactory;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.domain.function.FunctionModule;
import br.uff.labtempo.osiris.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
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
public class CompositeFromLinkSyncSchedule {

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
     * Creates Composite sensors on VirtualSensorNet based on
     * VirtualSensorNet Link sensor compositions of the same DataType
     * @throws Exception
     */
    @Scheduled(cron = "${sensornet.schedule.sync.composite.cron:*/2 * * * * ?}")
    public void createCompositeSensorsFromLinkSensors() throws Exception {
        log.info("Beginnning synchronization between Link and Composite sensors...");

        log.info("Composite synchronization completed...");
    }
}
