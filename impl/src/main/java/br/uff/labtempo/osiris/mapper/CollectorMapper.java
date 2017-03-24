package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.CollectorRequest;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

public class CollectorMapper {

    public static CollectorCoTo toCoTo(CollectorRequest collectorRequest) {
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorRequest.getId(),
                collectorRequest.getCaptureInterval(), collectorRequest.getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(collectorRequest.getInfo());
        return collectorCoTo;
    }

    public static CollectorResponse toResponse(CollectorCoTo collectorCoTo) {
                 return CollectorResponse.builder()
                .id(collectorCoTo.getId())
                .captureInterval(collectorCoTo.getCaptureInterval())
                .captureIntervalTimeUnit(collectorCoTo.getCaptureIntervalTimeUnit())
                .info(collectorCoTo.getInfo())
                .build();
    }
}
