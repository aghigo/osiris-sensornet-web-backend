package br.uff.labtempo.osiris.schedule.mock;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.generator.BlendingGenerator;
import br.uff.labtempo.osiris.generator.CompositeGenerator;
import br.uff.labtempo.osiris.generator.blending.BlendingRequestGenerator;
import br.uff.labtempo.osiris.generator.datatype.DataTypeGenerator;
import br.uff.labtempo.osiris.generator.link.LinkGenerator;
import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.repository.BlendingRepository;
import br.uff.labtempo.osiris.repository.CompositeRepository;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.service.BlendingService;
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

import javax.xml.ws.soap.Addressing;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by osiris on 09/07/17.
 */
@EnableScheduling
@Service
@Slf4j
@Profile("vsn_init_mock_schedule")
public class VirtualSensorNetInitMockSchedule {

    @Autowired
    private DataTypeGenerator dataTypeGenerator;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private LinkGenerator linkGenerator;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private CompositeGenerator compositeGenerator;

    @Autowired
    private CompositeRepository compositeRepository;

    @Autowired
    private BlendingRequestGenerator blendingRequestGenerator;

    @Autowired
    private BlendingService blendingService;

    @Scheduled(fixedDelay = 10000)
    public void createMockedVsnData() throws Exception {
        //DataTypes
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeGenerator.getDataTypeVsnTo();
        this.dataTypeRepository.insert(dataTypeVsnTo);
        log.info(String.format("Mocked DataType created [%s]", dataTypeVsnTo.getDisplayName()));

        //Links
        LinkVsnTo linkVsnTo = this.linkGenerator.generateVsnTo();
        this.linkRepository.save(linkVsnTo);
        log.info(String.format("Mocked Link crated [%s]", linkVsnTo.getId()));

        //Composites
        CompositeVsnTo compositeVsnTo = this.compositeGenerator.generateCompositeVsnTo();
        this.compositeRepository.create(compositeVsnTo);
        log.info(String.format("Mocked Composite crated [%s]", compositeVsnTo.getId()));

        //Blendings
        BlendingRequest blendingRequest = this.blendingRequestGenerator.generateBlendingRequest();
        URI blendingUri = this.blendingService.create(blendingRequest);
        log.info(String.format("Mocked Link crated [%s with dataType %s and function %s]", blendingUri.getPath(), blendingRequest.getDataTypeId(), blendingRequest.getFunctionName()));
    }
}
