package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("sampleRepository")
public interface SampleRepository {
    Response getAll();
    Response getById(String id);
    void notify(SampleCoTo sampleCoTo);
}
