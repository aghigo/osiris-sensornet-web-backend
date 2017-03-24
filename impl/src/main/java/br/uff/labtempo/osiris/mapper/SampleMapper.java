package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.model.response.SensorResponse;
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
        NetworkResponse networkResponse = NetworkMapper.toResponse(sampleCoTo.getNetwork());
        CollectorResponse collectorResponse = CollectorMapper.toResponse(sampleCoTo.getCollector());
        SensorResponse sensorResponse = SensorMapper.toResponse(sampleCoTo.getSensor());

        return SampleResponse.builder()
                .network(networkResponse)
                .collector(collectorResponse)
                .sensor(sensorResponse)
                .build();
    }
}
