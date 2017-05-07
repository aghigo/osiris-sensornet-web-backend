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
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class responsible for generate random Link mock objets for the VirtualSensorNet module
 * A Link is a VirtualSensor type (from the VirtualSensorNet module) that is associated directly with one physical sensor from SensorNet module
 * @see LinkVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.8
 */
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

    public LinkVsnTo generateVsnTo(SensorSnTo sensorSnTo) throws AbstractRequestException {
        try {

            String id = getId();
            String networkId = sensorSnTo.getNetworkId();
            String collectorId = sensorSnTo.getCollectorId();
            String sensorId = sensorSnTo.getId();

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

    /**
     * Generates a random unique id for the Link sensor
     * pattern: "linkId-" + UUID
     * @see UUID
     * @return String with the unique Link id
     */
    private String getId() {
        return "linkId-" + UUID.randomUUID().toString();
    }

    /**
     * Get a random existing networkId from SensorNet module
     * @return String with an existing NetworkId randomly choosen
     * @throws AbstractRequestException
     */
    private String getNetworkId() throws AbstractRequestException {
        try {
            List<NetworkResponse> networkResponseList = this.networkService.getAll();
            int p = (int) (Math.random() * networkResponseList.size());
            return networkResponseList.get(p).getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to mock link sensor: Could not find any valid network from SensorNet module.");
        }
    }

    /**
     * Get a random existing networkId from SensorNet module, based on an existing network id
     * @param networkId
     * @return String with an existing CollectorId randomly choosen
     * @throws AbstractRequestException
     */
    private String getCollectorId(String networkId) throws AbstractRequestException {
        try {
            List<CollectorResponse> collectorResponseList = this.collectorService.getAllByNetworkId(networkId);
            int p = (int) (Math.random() * collectorResponseList.size());
            return collectorResponseList.get(p).getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to mock link sensor: Could not find any valid collector from SensorNet module.");
        }
    }

    /**
     * Get a random SensorId from SensorNet module, based on existing collector id and network id
     * @param collectorId
     * @param networkId
     * @return String with an existing SensorId randomly choosen
     * @throws AbstractRequestException
     */
    private String getSensorId(String collectorId, String networkId) throws AbstractRequestException {
        try {
            List<SensorResponse> sensorResponseList = this.sensorService.getAllByCollectorIdAndNetworkId(networkId, collectorId);
            int p = (int) (Math.random() * sensorResponseList.size());
            return sensorResponseList.get(p).getId();
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to mock link sensor: Could not find any valid sensor from SensorNet module.");
        }
    }

    /**
     * Get an random Map of Fields for a Link
     * a Field object contains its id and an DataType id
     * @see br.uff.labtempo.osiris.to.common.data.FieldTo
     * @see br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo
     * @return
     * @throws AbstractRequestException
     */
    private Map<String, Long> getField() throws AbstractRequestException {
        try {
            List<DataTypeResponse> dataTypeResponseList = this.dataTypeService.getAll();
            Map<String, Long> fieldMap = new LinkedHashMap<>();

            int totalFields = ((int) (Math.random() * MAX_FIELDS)) + 1;
            totalFields = totalFields > dataTypeResponseList.size() ? dataTypeResponseList.size() : totalFields;

            for(int i = 0; i < totalFields; i++) {
                int p = (int) (Math.random() * (dataTypeResponseList.size()));
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
