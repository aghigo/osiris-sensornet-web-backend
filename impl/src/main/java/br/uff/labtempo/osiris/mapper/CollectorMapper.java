package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.request.CollectorRequest;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;

import java.util.ArrayList;
import java.util.List;

public class CollectorMapper {
    public static CollectorResponse toResponse(CollectorSnTo collectorSnTo) {
        CollectorResponse collectorResponse = CollectorResponse.builder()
                .info(collectorSnTo.getInfo())
                .captureInterval(collectorSnTo.getCaptureInterval())
                .captureIntervaltimeUnit(collectorSnTo.getCaptureIntervalTimeUnit())
                .id(collectorSnTo.getId())
                .lastModified(collectorSnTo.getLastModified().getTimeInMillis())
                .networkId(collectorSnTo.getNetworkId())
                .state(collectorSnTo.getState())
                .totalSensors(collectorSnTo.getTotalSensors())
                .build();
        return collectorResponse;
    }
    public static List<CollectorResponse> toResponse(List<CollectorSnTo> collectorSnToList) {
        List<CollectorResponse> collectorResponseList = new ArrayList<>();
        for(CollectorSnTo collectorSnTo : collectorSnToList) {
            collectorResponseList.add(toResponse(collectorSnTo));
        }
        return collectorResponseList;
    }

    public static CollectorCoTo toCoTo(CollectorRequest collectorRequest) {
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorRequest.getId(),
                collectorRequest.getCaptureInterval(), collectorRequest.getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(collectorRequest.getInfo());
        return collectorCoTo;
    }

    public static List<CollectorCoTo> toCoTo(List<CollectorRequest> collectorRequestList) {
        List<CollectorCoTo> collectorCoToList = new ArrayList<>();
        for(CollectorRequest collectorRequest : collectorRequestList) {
            collectorCoToList.add(toCoTo(collectorRequest));
        }
        return collectorCoToList;
    }
}
