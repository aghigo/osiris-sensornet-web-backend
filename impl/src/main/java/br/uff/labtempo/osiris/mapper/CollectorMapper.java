package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;

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
}
