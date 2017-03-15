package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.collector.CollectorGenerator;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectorService {

    private CollectorGenerator collectorGenerator;

    @Autowired
    public CollectorService(CollectorGenerator collectorGenerator) {
        this.collectorGenerator = collectorGenerator;
    }

    public CollectorCoTo getRandom() {
        return this.collectorGenerator.generate();
    }
}
