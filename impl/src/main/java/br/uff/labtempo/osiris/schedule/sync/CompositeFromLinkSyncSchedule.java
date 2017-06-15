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
 * Perform synchonizations between Composite and Link sensors
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@EnableScheduling
@Service
@Slf4j
@Profile("composite_sync_schedule")
public class CompositeFromLinkSyncSchedule {

    /**
     * Creates Composite sensors on VirtualSensorNet based on
     * VirtualSensorNet Link sensor compositions of the same DataType
     * @throws Exception
     */
    //@Scheduled(cron = "${sensornet.schedule.sync.composite.cron:*/5 * * * * ?}")
    public void createCompositeSensorsFromLinkSensors() throws Exception {
        log.info("Beginnning synchronization between Link and Composite sensors...");
        log.info("Composite synchronization completed...");
    }
}
