package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.sample.SampleGenerator;
import br.uff.labtempo.osiris.repository.SampleRepository;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = "classpath:application.properties")
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleGenerator sampleGenerator;

    public SampleCoTo getRandom() {
        return this.sampleGenerator.generate();
    }

    public void post(SampleCoTo sampleCoTo) {
        this.sampleRepository.create(sampleCoTo);
    }

}
