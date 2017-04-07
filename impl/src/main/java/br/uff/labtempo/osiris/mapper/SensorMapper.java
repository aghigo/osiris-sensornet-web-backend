package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.model.request.SensorRequest;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.common.definitions.State;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SensorMapper {
    public static SensorResponse snToToResponse(SensorSnTo sensorSnTo) {
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

    public static List<SensorResponse> snToToResponse(List<SensorSnTo> sensorSnToList) {
        List<SensorResponse> sensorResponseList = new ArrayList<>();
        for(SensorSnTo sensorSnTo : sensorSnToList) {
            sensorResponseList.add(snToToResponse(sensorSnTo));
        }
        return sensorResponseList;
    }

    public static SensorCoTo requestToCoTo(SensorRequest sensorRequest) {
        SensorCoTo sensorCoTo = new SensorCoTo(sensorRequest.getId(), State.NEW, sensorRequest.getCaptureDateInMillis()
        , sensorRequest.getCaptureDateInNano(), sensorRequest.getAcquisitionDateInMillis());

        sensorCoTo.addInfo(sensorRequest.getInfo());

        for(SensorValue value : sensorRequest.getValues()) {
            sensorCoTo.addValue(value.getName(), value.getValue(), value.getUnit(), value.getSymbol());
        }

        for(String consumable : sensorRequest.getConsumables().keySet()) {
            sensorCoTo.addConsumable(consumable, sensorRequest.getConsumables().get(consumable));
        }

        for(SensorConsumableRule rule : sensorRequest.getRules()) {
            sensorCoTo.addConsumableRule(rule.getName(), rule.getConsumableName(), rule.getOperator(), rule.getLimitValue(), rule.getMessage());
        }

        return sensorCoTo;
    }

    public static SensorCoTo snToToCoTo(SensorSnTo sensorSnTo) {
        SensorCoTo sensorCoTo = new SensorCoTo(sensorSnTo.getId(), sensorSnTo.getState(), sensorSnTo.getCaptureTimestampInMillis(), sensorSnTo.getCapturePrecisionInNano(), sensorSnTo.getAcquisitionTimestampInMillis());
        sensorCoTo.addInfo(sensorSnTo.getInfo());
        for(ValueTo valueTo : sensorSnTo.getValuesTo()) {
            sensorCoTo.addValue(valueTo.getName(), valueTo.getValue(), valueTo.getUnit(), valueTo.getSymbol());
        }
        Map<String, Integer> consumableMap = sensorSnTo.getConsumables();
        for(String consumableName : consumableMap.keySet()) {
            sensorCoTo.addConsumable(consumableName, consumableMap.get(consumableName));
        }
        return sensorCoTo;
    }

    public static List<SensorCoTo> snToToCoTo(List<SensorSnTo> sensorSnToList) {
        List<SensorCoTo> sensorCoToList = new ArrayList<>();
        for(SensorSnTo sensorSnTo : sensorSnToList) {
            sensorCoToList.add(snToToCoTo(sensorSnTo));
        }
        return sensorCoToList;
    }
}
