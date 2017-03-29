package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;

public class SampleMapper {
    public static SampleCoTo getSampleCoTo(SampleRequest sampleRequest) {
        SampleCoTo sampleCoTo = new SampleCoTo(sampleRequest.getNetwork(), sampleRequest.getCollector(), sampleRequest.getSensor());
        return sampleCoTo;
    }

    public static SampleResponse getSampleResponse(SampleCoTo sampleCoTo) {
        SampleResponse sampleResponse = SampleResponse.builder()
                .network(sampleCoTo.getNetwork())
                .collector(sampleCoTo.getCollector())
                .sensor(sampleCoTo.getSensor())
                .build();
        return sampleResponse;
    }
}
