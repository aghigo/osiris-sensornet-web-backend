package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.request.SensorRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.common.definitions.State;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;

import java.util.*;

/**
 * Class that maps and converts Sensor classes
 * (SensorSnTo to SensorResponse)
 * (SensorRequest to SensorCoTo)
 * (SensorSnTo to SensorCoTo)
 * @see SensorRequest Sensor data sent by the client on HTTP POST/PUT requests
 * @see SensorCoTo Sensor representation to send to the SensorNet module
 * @see SensorSnTo Sensor representation sent by the SensorNet module
 * @see SensorResponse Sensor response data sent by the API to the Client
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class SensorMapper {

    /**
     * Converts SensorSnTo to SensorResponse
     * @see SensorSnTo
     * @see SensorResponse
     * @param sensorSnTo
     * @return SensorResponse
     */
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

    /**
     * Converts a List of SensorSnTo to a List of SensorResponse
     * @see SensorSnTo
     * @see SensorResponse
     * @param sensorSnToList
     * @return List of SensorResponse
     */
    public static List<SensorResponse> snToToResponse(List<SensorSnTo> sensorSnToList) {
        List<SensorResponse> sensorResponseList = new ArrayList<>();
        for(SensorSnTo sensorSnTo : sensorSnToList) {
            sensorResponseList.add(snToToResponse(sensorSnTo));
        }
        return sensorResponseList;
    }

    /**
     * Converts a SensorRequest to a SensorCoTo
     * @see SensorRequest
     * @see SensorCoTo
     * @param sensorRequest
     * @return SensorCoTo
     */
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

    /**
     * Converts SensorSnTo to SensorCoTo
     * @see SensorSnTo
     * @see SensorCoTo
     * @param sensorSnTo
     * @return SensorCoTo
     */
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

    /**
     * Converts a List of SensorSnTo to a List of SensorCoTo
     * @see SensorSnTo
     * @see SensorCoTo
     * @param sensorSnToList
     * @return List of SensorCoTo
     */
    public static List<SensorCoTo> snToToCoTo(List<SensorSnTo> sensorSnToList) {
        List<SensorCoTo> sensorCoToList = new ArrayList<>();
        for(SensorSnTo sensorSnTo : sensorSnToList) {
            sensorCoToList.add(snToToCoTo(sensorSnTo));
        }
        return sensorCoToList;
    }

    public static SensorSnTo responseToSnTo(SensorResponse sensorResponse) {
        Calendar lastModifiedCalendar = Calendar.getInstance();
        lastModifiedCalendar.setTimeInMillis(sensorResponse.getLastModified());
        SensorSnTo sensorSnTo = new SensorSnTo(sensorResponse.getId(), sensorResponse.getState(), sensorResponse.getCaptureTimestampInMillis(),
                (int) sensorResponse.getCapturePrecisionInNano(), sensorResponse.getAcquisitionTimestampInMillis(), sensorResponse.getStorageTimestampInMillis(), lastModifiedCalendar, sensorResponse.getNetworkId(), sensorResponse.getCollectorId());
        sensorSnTo.addInfo(sensorResponse.getInfo());
        for(String consumableName : sensorResponse.getConsumables().keySet()) {
            sensorSnTo.addConsumable(consumableName, sensorResponse.getConsumables().get(consumableName));
        }
        for(SensorValue sensorValue : sensorResponse.getValues()) {
            sensorSnTo.addValue(sensorValue.getName(), sensorValue.getType(), new Long(sensorValue.getValue()).toString(), sensorValue.getUnit(), sensorValue.getSymbol());
        }
        return sensorSnTo;
    }

    public static List<SensorSnTo> responseToSnTo(List<SensorResponse> sensorResponseList) {
        List<SensorSnTo> sensorSnToList = new ArrayList<>();
        for(SensorResponse sensorResponse : sensorResponseList) {
            SensorSnTo sensorSnTo = responseToSnTo(sensorResponse);
            sensorSnToList.add(sensorSnTo);
        }
        return sensorSnToList;
    }
}
