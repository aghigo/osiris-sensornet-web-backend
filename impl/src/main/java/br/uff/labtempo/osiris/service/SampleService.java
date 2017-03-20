package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.SampleGenerator;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private SampleGenerator sampleGenerator;

    @Autowired
    public SampleService(SampleGenerator sampleGenerator) {
        this.sampleGenerator = sampleGenerator;
    }

    public SampleCoTo getRandom() {
        return this.sampleGenerator.generate();
    }
}
