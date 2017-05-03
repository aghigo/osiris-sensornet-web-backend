package br.uff.labtempo.osiris.schedule;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.generator.BlendingGenerator;
import br.uff.labtempo.osiris.generator.CompositeGenerator;
import br.uff.labtempo.osiris.generator.datatype.DataTypeGenerator;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.repository.BlendingRepository;
import br.uff.labtempo.osiris.repository.CompositeRepository;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with Schedule Jobs that periodically creates new mocked Datatypes and Virtual Sensors (Link, Composite and Blending) on VirtualSensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Slf4j
@Service
@EnableScheduling
@Profile("virtualsensornet_mock_schedule")
public class VirtualSensorNetMockSchedule {
    @Autowired
    private DataTypeGenerator dataTypeGenerator;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkGenerator linkGenerator;

    @Autowired
    private CompositeGenerator compositeGenerator;

    @Autowired
    private CompositeRepository compositeRepository;

    @Autowired
    private BlendingGenerator blendingGenerator;

    @Autowired
    private BlendingRepository blendingRepository;

    @Scheduled(cron="${virtualsensornet.schedule.create.datatype.cron:*/1 * * * * ?}")
    public void loadDataTypes() throws URISyntaxException, AbstractRequestException {
        try {
            log.info("Loading VirtualSensorNet with default DataTypes");
            List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
            if(dataTypeVsnToList == null || dataTypeVsnToList.isEmpty()) {
                List<DataTypeVsnTo> dataTypeList = new ArrayList<>();
                dataTypeList.add(new DataTypeVsnTo("temperature", ValueType.NUMBER, "celsius", "Cº"));
                dataTypeList.add(new DataTypeVsnTo("temperature", ValueType.NUMBER, "fahrenheit", "Fº"));
                dataTypeList.add(new DataTypeVsnTo("temperature", ValueType.NUMBER, "kelvin", "K"));
                dataTypeList.add(new DataTypeVsnTo("pressure", ValueType.NUMBER, "pascal", "P"));
                dataTypeList.add(new DataTypeVsnTo("luminosity", ValueType.NUMBER, "candela", "C"));
                for(DataTypeVsnTo dataTypeVsnTo : dataTypeList) {
                    this.dataTypeRepository.insert(dataTypeVsnTo);
                    log.info(String.format("DataType created [%s]", dataTypeVsnTo.toString()));
                }
                log.info("DataTypes loaded successfully.");
            }
        } catch (Exception e) {
            log.error("Loading VirtualSensorNet with default DataTypes");
            throw e;
        }
    }

    @Scheduled(cron="${virtualsensornet.schedule.create.link.cron:*/2 * * * * ?}")
    public void createLink() throws AbstractRequestException, URISyntaxException {
        LinkVsnTo linkVsnTo = this.linkGenerator.generateVsnTo();
        URI uri = this.linkRepository.save(linkVsnTo);
        log.info(String.format("Link sensor created [%s] on VirtualSensorNet module.", uri.getPath()));
    }

    @Scheduled(cron="virtualsensornet.schedule.create.composite.cron:*/3 * * * * ?")
    public void createComposite() throws URISyntaxException, AbstractRequestException {
        CompositeVsnTo compositeVsnTo = this.compositeGenerator.generateCompositeVsnTo();
        URI uri = this.compositeRepository.create(compositeVsnTo);
        log.info(String.format("Composite sensor created [%s] on VirtualSensorNet module.", uri.getPath()));
    }

//    @Scheduled(cron="${virtualsensornet.schedule.create.blending.cron:*/9 * * * * ?}")
//    public void createBlending() throws URISyntaxException, AbstractRequestException {
//        BlendingVsnTo blendingVsnTo = this.blendingGenerator.generateBlendingVsnTo();
//        URI uri = this.blendingRepository.create(blendingVsnTo);
//        log.info(String.format("Blending sensor created [%s] on VirtualSensorNet module.", uri.getPath()));
//    }
}
