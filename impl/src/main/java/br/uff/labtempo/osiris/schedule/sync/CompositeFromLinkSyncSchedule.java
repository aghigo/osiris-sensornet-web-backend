package br.uff.labtempo.osiris.schedule.sync;

import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

/**
 * Perform synchonizations between Composite and Link sensors
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
@Slf4j
public class CompositeFromLinkSyncSchedule {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private CompositeRepository compositeRepository;

    /**
     * Creates Composite sensors on VirtualSensorNet based on
     * VirtualSensorNet Link sensor compositions of the same DataType
     * @throws Exception
     */
    //@Scheduled(cron = "${sensornet.schedule.sync.composite.cron:*/5 * * * * ?}")
    public void createCompositeSensorsFromLinkSensors() throws Exception {
        log.info("Beginnning synchronization between Link and Composite sensors...");
        List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
        for(DataTypeVsnTo dataTypeVsnTo : dataTypeVsnToList) {
            List<CompositeVsnTo> compositeVsnToList = this.compositeRepository.getAll();
            boolean found = false;
            for(CompositeVsnTo compositeVsnTo : compositeVsnToList) {
                if(compositeVsnTo.getBoundFields().get(0).getDataTypeId() == dataTypeVsnTo.getId()) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
                CompositeVsnTo compositeVsnTo = new CompositeVsnTo();
                for(LinkVsnTo linkVsnTo : linkVsnToList) {
                    for(FieldTo fieldTo : linkVsnTo.getFields()) {
                        if(fieldTo.getDataTypeId() == dataTypeVsnTo.getId()) {
                            compositeVsnTo.bindToField(fieldTo);
                        }
                    }
                }
                if(!compositeVsnTo.getBoundFields().isEmpty()) {
                    URI uri = this.compositeRepository.create(compositeVsnTo);
                    log.info(String.format("Composite sensor created [%s]", uri.getPath()));
                }
            }
        }
        log.info("Composite synchronization completed...");
    }
}
