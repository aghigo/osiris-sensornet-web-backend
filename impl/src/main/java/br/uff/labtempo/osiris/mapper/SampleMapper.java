package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;

public class SampleMapper {
    public static SampleCoTo toCoTo(SampleRequest sampleRequest) {
        NetworkCoTo networkCoTo = NetworkMapper.toCoTo(sampleRequest.getNetwork());
        CollectorCoTo collectorCoTo = CollectorMapper.toCoTo(sampleRequest.getCollector());
        SensorCoTo sensorCoTo = SensorMapper.toCoTo(sampleRequest.getSensor());

        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        return sampleCoTo;
    }

    public static SampleResponse toResponse(SampleCoTo sampleCoTo) {
        SampleResponse sampleResponse = SampleResponse.builder()
                .network(sampleCoTo.getNetwork())
                .collector(sampleCoTo.getCollector())
                .sensor(sampleCoTo.getSensor())
                .build();
        return sampleResponse;
    }
}
