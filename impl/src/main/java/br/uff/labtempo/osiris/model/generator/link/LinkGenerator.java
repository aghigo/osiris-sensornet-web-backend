package br.uff.labtempo.osiris.model.generator.link;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.service.CollectorService;
import br.uff.labtempo.osiris.service.DataTypeService;
import br.uff.labtempo.osiris.service.NetworkService;
import br.uff.labtempo.osiris.service.SensorService;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class LinkGenerator {

    private final int MAX_FIELDS = 3;

    @Autowired
    private CollectorService collectorService;

    @Autowired
    private NetworkService networkService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private DataTypeService dataTypeService;

    public LinkVsnTo generateVsnTo() throws AbstractRequestException {
        String id = getId();
        String networkId = getNetworkId();
        String collectorId = getCollectorId(networkId);
        String sensorId = getSensorId(collectorId, networkId);
        Map<String, Long> fieldMap = getField();

        LinkVsnTo linkVsnTo = new LinkVsnTo(id, sensorId, collectorId, networkId);

        for(String fieldName : fieldMap.keySet()) {
            linkVsnTo.createField(fieldName, fieldMap.get(fieldName));
        }
        return linkVsnTo;
    }

    private String getId() {
        return "linkId-" + UUID.randomUUID().toString();
    }

    private String getNetworkId() throws AbstractRequestException {
        NetworkResponse networkResponse = this.networkService.getAll().get(0);
        return networkResponse.getId();
    }

    private String getCollectorId(String networkId) throws AbstractRequestException {
         CollectorResponse collectorResponse = this.collectorService.getAllByNetworkId(networkId).get(0);
         return collectorResponse.getId();
    }

    private String getSensorId(String collectorId, String networkId) throws AbstractRequestException {
        SensorResponse sensorResponse = this.sensorService.getAll().get(0);
        return sensorResponse.getId();
    }

    private Map<String, Long> getField() throws AbstractRequestException {
        List<DataTypeResponse> dataTypeResponseList = this.dataTypeService.getAll();
        Map<String, Long> fieldMap = new LinkedHashMap<>();

        int totalFields = (int) (Math.random() * MAX_FIELDS) + 1;

        for(int i = 0; i < totalFields; i++) {
            int p = (int) (Math.random() * (fieldMap.size()));
            DataTypeResponse dataTypeResponse = dataTypeResponseList.get(p);
            if(!fieldMap.containsKey(dataTypeResponse.getName())) {
                fieldMap.put(dataTypeResponse.getName(), dataTypeResponse.getId());
            }
        }

        return fieldMap;
    }
}
