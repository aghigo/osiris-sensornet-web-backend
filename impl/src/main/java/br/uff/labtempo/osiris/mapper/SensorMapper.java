package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;

import java.util.ArrayList;
import java.util.List;

public class SensorMapper {
    public static SensorResponse toResponse(SensorSnTo sensorSnTo) {
        SensorResponse sensorResponse = SensorResponse.builder()
                .acquisitionTimestampInMillis(sensorSnTo.getAcquisitionTimestampInMillis())
                .capturePrecisionInNano(sensorSnTo.getCapturePrecisionInNano())
                .captureTimestampInMillis(sensorSnTo.getCaptureTimestampInMillis())
                .collectorId(sensorSnTo.getCollectorId())
                .consumables(sensorSnTo.getConsumables())
                .id(sensorSnTo.getId())
                .info(sensorSnTo.getInfo())
                .lastModified(sensorSnTo.getLastModified().getTimeInMillis())
                .networkId(sensorSnTo.getNetworkId())
                .state(sensorSnTo.getState())
                .storageTimestampInMillis(sensorSnTo.getStorageTimestampInMillis())
                .build();

        List<SensorValue> valueList = new ArrayList<>();
        for(ValueTo value : sensorSnTo.getValuesTo()) {
            valueList.add(SensorValue.builder()
            .name(value.getName())
                    .type(value.getType())
                    .unit(value.getUnit())
                    .symbol(value.getSymbol())
                    .value(new Long(value.getValue())).build());
        }
        sensorResponse.setValues(valueList);

        return sensorResponse;
    }

    public static List<SensorResponse> toResponse(List<SensorSnTo> sensorSnToList) {
        List<SensorResponse> sensorResponseList = new ArrayList<>();
        for(SensorSnTo sensorSnTo : sensorSnToList) {
            sensorResponseList.add(toResponse(sensorSnTo));
        }
        return sensorResponseList;
    }
}
