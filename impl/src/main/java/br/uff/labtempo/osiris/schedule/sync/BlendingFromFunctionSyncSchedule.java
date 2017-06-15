package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.service.BlendingService;
import br.uff.labtempo.osiris.service.FunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * Perform synchonizations between Blending sensors and Function modules
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@EnableScheduling
@Service
@Slf4j
@Profile("blending_sync_schedule")
public class BlendingFromFunctionSyncSchedule {

    @Autowired
    private FunctionService functionService;

    @Autowired
    private FunctionDataRepository functionDataRepository;

    @Autowired
    private BlendingService blendingService;

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
    //@Scheduled(cron = "${sensornet.schedule.sync.blending.cron:*/5 * * * * ?}")
    public void createBlendingSensorsFromFunctionModules() throws Exception {
        log.info("Beginning synchronization between VSensors and Blendings...");
        log.info("Blending synchronization completed.");
    }
}
