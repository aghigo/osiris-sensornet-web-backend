package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.stereotype.Component;

@Component("sampleRepository")
public interface SampleRepository {
    void save(SampleCoTo sampleCoTo);
    void update(SampleCoTo sampleCoTo);
}
