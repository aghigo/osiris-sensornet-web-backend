package br.uff.labtempo.osiris.schedule.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Perform synchonizations between Link sensors and SensorNet sensors
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@EnableScheduling
@Service
@Slf4j
@Profile("vsn_sync_schedule")
public class VirtualSensorNetSyncSchedule {

    @Autowired
    private DataTypeFromSensorValueSyncSchedule dataTypeFromSensorValueSyncSchedule;

    @Autowired
    private LinkFromSensorSyncSchedule linkFromSensorSyncSchedule;

    @Autowired
    private CompositeFromLinkSyncSchedule compositeFromLinkSyncSchedule;

    @Autowired
    private FunctionModuleFromDataTypeSyncSchedule functionModuleFromDataTypeSyncSchedule;

    @Autowired
    private BlendingFromFunctionSyncSchedule blendingFromFunctionSyncSchedule;

    @Autowired
    private FunctionModuleThreadSyncSchedule functionModuleThreadSyncSchedule;

    @Scheduled(cron = "${sensornet.schedule.sync.link.cron:*/10 * * * * ?}")
    public void synchronizeVirtualSensorNet() throws Exception {
        this.dataTypeFromSensorValueSyncSchedule.createDataTypesFromSensorValues();
        this.linkFromSensorSyncSchedule.createLinkSensorsFromSensorNetSensors();
        this.compositeFromLinkSyncSchedule.createCompositeSensorsFromLinkSensors();
        this.functionModuleFromDataTypeSyncSchedule.createFunctionModulesFromDataTypes();
        this.functionModuleThreadSyncSchedule.handleFunctionModuleThreadsFromFunctionData();
        this.blendingFromFunctionSyncSchedule.createBlendingSensorsFromFunctionModules();
    }
}
