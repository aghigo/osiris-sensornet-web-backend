package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;

import java.util.List;

public interface SampleRepository {
    Response getAll();
    Response getById(String id);
    void create(SampleCoTo sampleCoTo);
}
