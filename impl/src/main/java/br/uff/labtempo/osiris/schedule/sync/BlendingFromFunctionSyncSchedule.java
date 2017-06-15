package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.service.BlendingService;
import br.uff.labtempo.osiris.service.FunctionService;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingBondVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

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

    private final long defaultCallIntervalInMillis = 2000;

    @Autowired
    private CompositeRepository compositeRepository;

    @Autowired
    private FunctionDataRepository functionDataRepository;

    @Autowired
    private BlendingService blendingService;

    @Autowired
    private BlendingRepository blendingRepository;

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
    @Scheduled(cron = "${sensornet.schedule.sync.blending.cron:*/5 * * * * ?}")
    public void createBlendingSensorsFromFunctionModules() throws Exception {
        log.info("Beginning synchronization between VSensors and Blendings...");
        List<CompositeVsnTo> compositeVsnToList = this.compositeRepository.getAll();
        for(CompositeVsnTo compositeVsnTo :compositeVsnToList) {
            List<BlendingVsnTo> blendingVsnToList = this.blendingRepository.getAll();
            boolean found = false;
            for(BlendingVsnTo blendingVsnTo : blendingVsnToList) {
                for(BlendingBondVsnTo blendingBondVsnTo : blendingVsnTo.getRequestParams()) {
                    for(FieldTo fieldTo : compositeVsnTo.getBoundFields()) {
                        if(fieldTo.getId() == blendingBondVsnTo.getFieldId()) {
                            found = true;
                            break;
                        }
                    }
                    if(found) break;
                }
                if(found) break;
            }
            if(!found) {
                Iterable<FunctionData> functionDataIterable = this.functionDataRepository.findAll();
                Iterator<FunctionData> functionDataIterator = functionDataIterable.iterator();
                while(functionDataIterator.hasNext()) {
                    FunctionData functionData = functionDataIterator.next();
                    BlendingRequest blendingRequest = BlendingRequest.builder()
                            .functionName(functionData.getName())
                            .dataTypeId(compositeVsnTo.getBoundFields().get(0).getDataTypeId())
                            .callIntervalInMillis(this.defaultCallIntervalInMillis)
                            .build();
                    URI uri = this.blendingService.createByComposite(blendingRequest);
                    log.info(String.format("Blending sensor created [%s].", uri.getPath()));
                }
            }
        }
        log.info("Blending synchronization completed.");
    }
}
