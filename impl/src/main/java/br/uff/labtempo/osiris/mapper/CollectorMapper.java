package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.CollectorRequest;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;

import java.util.ArrayList;
import java.util.List;

public class CollectorMapper {
    public static CollectorResponse snToToResponse(CollectorSnTo collectorSnTo) {
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
    public static List<CollectorResponse> snToToResponse(List<CollectorSnTo> collectorSnToList) {
        List<CollectorResponse> collectorResponseList = new ArrayList<>();
        for(CollectorSnTo collectorSnTo : collectorSnToList) {
            collectorResponseList.add(snToToResponse(collectorSnTo));
        }
        return collectorResponseList;
    }

    public static CollectorCoTo requestToCoTo(CollectorRequest collectorRequest) {
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorRequest.getId(),
                collectorRequest.getCaptureInterval(), collectorRequest.getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(collectorRequest.getInfo());
        return collectorCoTo;
    }

    public static List<CollectorCoTo> requestToCoTo(List<CollectorRequest> collectorRequestList) {
        List<CollectorCoTo> collectorCoToList = new ArrayList<>();
        for(CollectorRequest collectorRequest : collectorRequestList) {
            collectorCoToList.add(requestToCoTo(collectorRequest));
        }
        return collectorCoToList;
    }

    public static CollectorCoTo snToToCoTo(CollectorSnTo collectorSnTo) {
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorSnTo.getId(), collectorSnTo.getCaptureInterval(),
                collectorSnTo.getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(collectorSnTo.getInfo());
        return collectorCoTo;
    }

    public static List<CollectorCoTo> snToToCoTo(List<CollectorSnTo> collectorSnToList) {
        List<CollectorCoTo> collectorCoToList = new ArrayList<>();
        for(CollectorSnTo collectorSnTo : collectorSnToList) {
            collectorCoToList.add(snToToCoTo(collectorSnTo));
        }
        return collectorCoToList;
    }
}
