package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.model.request.SensorRequest;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.data.ConsumableRuleTo;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.common.definitions.State;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class SensorMapper {
    public static SensorCoTo toCoTo(@Valid SensorRequest sensorRequest) {
        SensorCoTo sensorCoTo = new SensorCoTo(sensorRequest.getId(),
                State.NEW, sensorRequest.getCaptureDateInMillis(),
                sensorRequest.getCaptureDateInNano(), sensorRequest.getAcquisitionDateInMillis());

        sensorCoTo.addInfo(sensorRequest.getInfo());

        for(String consumableName: sensorRequest.getConsumables().keySet()) {
            sensorCoTo.addConsumable(consumableName, sensorRequest.getConsumables().get(consumableName));
        }

        for(SensorConsumableRule rule: sensorRequest.getRules()) {
            sensorCoTo.addConsumableRule(rule.getName(), rule.getConsumableName(), rule.getOperator(),
                    rule.getLimitValue(), rule.getMessage());
        }

        for(SensorValue value : sensorRequest.getValues()) {
            sensorCoTo.addValue(value.getName(), value.getValue(), value.getUnit(), value.getSymbol());
        }

        return sensorCoTo;
    }

    public static List<SensorCoTo> toCoTo(List<SensorRequest> sensorRequestList) {
        List<SensorCoTo> sensorCoToList = new ArrayList<>();
        for(SensorRequest sensorRequest : sensorRequestList) {
            sensorCoToList.add(toCoTo(sensorRequest));
        }
        return sensorCoToList;
    }

    public static SensorResponse toResponse(SensorCoTo sensorCoTo) {
        SensorResponse sensorResponse = new SensorResponse();
        sensorResponse.setId(sensorCoTo.getId());
        sensorResponse.setAcquisitionDateInMillis(sensorCoTo.getAcquisitionTimestampInMillis());
        sensorResponse.setCaptureDateInMillis(sensorCoTo.getCaptureTimestampInMillis());
        sensorResponse.setCaptureDateInNano(sensorCoTo.getCapturePrecisionInNano());
        sensorResponse.setConsumables(sensorCoTo.getConsumables());

        List<SensorValue> sensorValueList = new ArrayList<>();
        for(ValueTo value : sensorCoTo.getValuesTo()) {
            sensorValueList.add(SensorValue.builder()
                    .name(value.getName())
                    .value(new Long(value.getValue()))
                    .unit(value.getValue())
                    .symbol(value.getSymbol())
                    .build());
        }
        sensorResponse.setValues(sensorValueList);

        sensorResponse.setInfo(sensorCoTo.getInfo());

        List<SensorConsumableRule> sensorConsumableRuleList = new ArrayList<>();
        for(ConsumableRuleTo rule : sensorCoTo.getConsumableRulesTo()) {
            sensorConsumableRuleList.add(
                    SensorConsumableRule.builder()
                    .consumableName(rule.getConsumableName())
                    .operator(rule.getOperator())
                    .name(rule.getName())
                    .message(rule.getMessage())
                    .limitValue(rule.getLimitValue())
                    .build()
            );
        }
        sensorResponse.setRules(sensorConsumableRuleList);

        return sensorResponse;
    }

    public static List<SensorResponse> toResponse(List<SensorCoTo> sensorCoToList) {
        List<SensorResponse> sensorRequestList = new ArrayList<>();
        for(SensorCoTo sensorCoTo : sensorCoToList) {
            sensorRequestList.add(toResponse(sensorCoTo));
        }
        return sensorRequestList;
    }

}
