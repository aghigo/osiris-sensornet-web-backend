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
import java.util.*;

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
public class BlendingFromFunctionSyncSchedule {

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
    //@Scheduled(cron = "${sensornet.schedule.sync.blending.cron:* */60 * * * ?}")
    public void createBlendingSensorsFromFunctionModules() throws Exception {
        log.info("Beginning synchronization between VSensors and Blendings...");
        log.info("Blending synchronization completed.");
    }
}
