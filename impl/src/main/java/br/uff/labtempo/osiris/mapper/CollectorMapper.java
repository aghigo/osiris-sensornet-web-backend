package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.CollectorRequest;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class CollectorMapper {

    public static CollectorCoTo toCoTo(@Valid CollectorRequest collectorRequest) {
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

    public static CollectorResponse toResponse(CollectorCoTo collectorCoTo) {
                 return CollectorResponse.builder()
                .id(collectorCoTo.getId())
                .captureInterval(collectorCoTo.getCaptureInterval())
                .captureIntervalTimeUnit(collectorCoTo.getCaptureIntervalTimeUnit())
                .info(collectorCoTo.getInfo())
                .build();
    }

    public static List<CollectorResponse> toResponse(List<CollectorCoTo> collectorCoToList) {
        List<CollectorResponse> collectorResponseList = new ArrayList<>();
        for(CollectorCoTo collectorCoTo : collectorCoToList) {
            collectorResponseList.add(toResponse(collectorCoTo));
        }
        return collectorResponseList;
    }
}
