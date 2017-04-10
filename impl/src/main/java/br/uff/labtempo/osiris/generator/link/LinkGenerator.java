package br.uff.labtempo.osiris.generator.link;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.InternalServerErrorException;
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
        try {
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
        } catch (Exception e) {
            throw e;
        }
    }

    private String getId() {
        return "linkId-" + UUID.randomUUID().toString();
    }

    private String getNetworkId() throws AbstractRequestException {
        try {
            List<NetworkResponse> networkResponseList = this.networkService.getAll();
            int p = (int) (Math.random() * networkResponseList.size());
            return networkResponseList.get(p).getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to mock link sensor: Could not find any valid network from SensorNet module.");
        }
    }

    private String getCollectorId(String networkId) throws AbstractRequestException {
        try {
            List<CollectorResponse> collectorResponseList = this.collectorService.getAllByNetworkId(networkId);
            int p = (int) (Math.random() * collectorResponseList.size());
            return collectorResponseList.get(p).getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to mock link sensor: Could not find any valid collector from SensorNet module.");
        }
    }

    private String getSensorId(String collectorId, String networkId) throws AbstractRequestException {
        try {
            List<SensorResponse> sensorResponseList = this.sensorService.getAllByCollectorIdAndNetworkId(networkId, collectorId);
            int p = (int) (Math.random() * sensorResponseList.size());
            return sensorResponseList.get(p).getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to mock link sensor: Could not find any valid sensor from SensorNet module.");
        }
    }

    private Map<String, Long> getField() throws AbstractRequestException {
        try {
            List<DataTypeResponse> dataTypeResponseList = this.dataTypeService.getAll();
            Map<String, Long> fieldMap = new LinkedHashMap<>();

            int totalFields = (int) (Math.random() * MAX_FIELDS);

            for(int i = 0; i < totalFields; i++) {
                int p = (int) (Math.random() * (fieldMap.size()));
                DataTypeResponse dataTypeResponse = dataTypeResponseList.get(p);
                if(!fieldMap.containsKey(dataTypeResponse.getName())) {
                    fieldMap.put(dataTypeResponse.getName(), dataTypeResponse.getId());
                }
            }
            return fieldMap;
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to mock link sensor: Could not find any valid dataType from VirtualSensorNet module.");
        }
    }
}
