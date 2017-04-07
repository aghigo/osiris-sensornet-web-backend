package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;

public class SampleMapper {
    public static SampleCoTo requestToCoTo(SampleRequest sampleRequest) {
        NetworkCoTo networkCoTo = NetworkMapper.requestToCoTo(sampleRequest.getNetwork());
        CollectorCoTo collectorCoTo = CollectorMapper.requestToCoTo(sampleRequest.getCollector());
        SensorCoTo sensorCoTo = SensorMapper.requestToCoTo(sampleRequest.getSensor());

        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        return sampleCoTo;
    }

    public static SampleResponse coToToResponse(SampleCoTo sampleCoTo) {
        SampleResponse sampleResponse = SampleResponse.builder()
                .network(sampleCoTo.getNetwork())
                .collector(sampleCoTo.getCollector())
                .sensor(sampleCoTo.getSensor())
                .build();
        return sampleResponse;
    }
}
