package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.domain.sensor.SensorConsumableRule;
import br.uff.labtempo.osiris.model.domain.sensor.SensorValue;
import br.uff.labtempo.osiris.model.request.collector.CollectorRequest;
import br.uff.labtempo.osiris.model.request.network.NetworkRequest;
import br.uff.labtempo.osiris.model.request.sample.SampleRequest;
import br.uff.labtempo.osiris.model.request.sensor.SensorRequest;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.definitions.State;

public class SampleMapper {

    public static SampleCoTo toCoTo(SampleRequest sampleRequest) {
        NetworkRequest networkRequest = sampleRequest.getNetwork();
        NetworkCoTo networkCoTo = new NetworkCoTo(networkRequest.getId());
        networkCoTo.addInfo(networkRequest.getInfo());

        CollectorRequest collectorRequest = sampleRequest.getCollector();
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorRequest.getId(), collectorRequest.getCaptureInterval(), collectorRequest.getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(collectorRequest.getInfo());

        SensorRequest sensorRequest = sampleRequest.getSensor();
        SensorCoTo sensorCoTo = new SensorCoTo(sensorRequest.getId(), State.NEW, sensorRequest.getCaptureTimestampInMillis(), sensorRequest.getCapturePrecisionInNano(),sensorRequest.getAcquisitionTimestampInMillis());
        sensorCoTo.addInfo(sensorRequest.getInfo());
        for(SensorValue value : sensorRequest.getValues()) {
            sensorCoTo.addValue(value.getName(), value.getValue(),value.getUnit(), value.getSymbol());
        }
        for(String key : sensorRequest.getConsumables().keySet()) {
            sensorCoTo.addConsumable(key, sensorRequest.getConsumables().get(key));
        }
        for(SensorConsumableRule rule : sensorRequest.getRules()) {
            sensorCoTo.addConsumableRule(rule.getName(), rule.getConsumableName(), rule.getOperator(), rule.getLimitValue(), rule.getMessage());
        }
        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        return sampleCoTo;
    }

}
