package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.model.generator.CollectorGenerator;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectorService {
    public List<CollectorCoTo> getAll() {
        List<CollectorCoTo> collectorCoToList = new ArrayList<>();
        CollectorGenerator collectorGenerator = new CollectorGenerator();
        collectorCoToList.add(collectorGenerator.generate());
        return collectorCoToList;
    }
}
