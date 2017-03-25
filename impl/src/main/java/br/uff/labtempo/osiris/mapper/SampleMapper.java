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

import java.util.ArrayList;
import java.util.List;

public class SampleMapper {

    public static SampleCoTo toCoTo(SampleRequest sampleRequest) {
        NetworkCoTo networkCoTo = NetworkMapper.toCoTo(sampleRequest.getNetwork());
        CollectorCoTo collectorCoTo = CollectorMapper.toCoTo(sampleRequest.getCollector());
        SensorCoTo sensorCoTo = SensorMapper.toCoTo(sampleRequest.getSensor());
        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        return sampleCoTo;
    }

    public static List<SampleCoTo> toCoTo(List<SampleRequest> sampleRequestList) {
        List<SampleCoTo> sampleCoToList = new ArrayList<>();
        for(SampleRequest sampleRequest : sampleRequestList) {
            sampleCoToList.add(toCoTo(sampleRequest));
        }
        return sampleCoToList;
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

    public static List<SampleResponse> toResponse(List<SampleCoTo> sampleCoToList) {
        List<SampleResponse> sampleResponseList = new ArrayList<>();
        for(SampleCoTo sampleCoTo : sampleCoToList) {
            sampleResponseList.add(toResponse(sampleCoTo));
        }
        return sampleResponseList;
    }
}
