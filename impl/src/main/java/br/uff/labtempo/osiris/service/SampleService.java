package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.sample.SampleGenerator;
import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@PropertySource(value = "classpath:application.properties")
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleGenerator sampleGenerator;

    public SampleResponse getRandom() {
        SampleCoTo sampleCoTo = this.sampleGenerator.getSampleCoTo();
        SampleResponse sampleResponse = SampleResponse.builder()
                .network(sampleCoTo.getNetwork())
                .collector(sampleCoTo.getCollector())
                .sensor(sampleCoTo.getSensor())
                .build();
        return sampleResponse;
    }

    public URI create(SampleRequest sampleRequest) throws URISyntaxException {
        SampleCoTo sampleCoTo = new SampleCoTo(sampleRequest.getNetwork(), sampleRequest.getCollector(), sampleRequest.getSensor());
        this.sampleRepository.save(sampleCoTo);

        URI uri = new URI(String.format("/sensornet/network/%s/collector/%s/sensor/%s/", sampleCoTo.getNetwork().getId(),
                sampleCoTo.getCollector().getId(), sampleCoTo.getSensor().getId()));

        return uri;
    }
}
