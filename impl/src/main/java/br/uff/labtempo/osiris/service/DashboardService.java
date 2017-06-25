package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.response.SensorNetDashboardResponse;
import br.uff.labtempo.osiris.model.response.VirtualSensorNetDashboardResponse;
import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.to.common.definitions.State;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorType;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by osiris on 25/06/17.
 */
@Service
public class DashboardService {

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private CollectorRepository collectorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private VirtualSensorRepository virtualSensorRepository;

    public SensorNetDashboardResponse getSensornetDashboard() throws AbstractRequestException {
        List<NetworkSnTo> networkSnToList = this.networkRepository.getAll();
        List<CollectorSnTo> collectorSnToList = new ArrayList<>();

        for(NetworkSnTo networkSnTo : networkSnToList) {
            collectorSnToList.addAll(this.collectorRepository.getAllByNetworkId(networkSnTo.getId()));
        }

        List<SensorSnTo> sensorSnToList = new ArrayList<>();
        for(CollectorSnTo collectorSnTo : collectorSnToList) {
               sensorSnToList.addAll(this.sensorRepository.getAllByCollectorIdAndNetworkId(collectorSnTo.getNetworkId(), collectorSnTo.getId()));
        }

        Map<State, Long> totalSensorsByStateMap = new HashMap<>();
        for(State state : State.values()) {
            long totalSensorsOfCurrentState = 0;
            for(SensorSnTo sensorSnTo : sensorSnToList) {
                if(sensorSnTo.getState().equals(state)) {
                    totalSensorsOfCurrentState++;
                }
            }
            totalSensorsByStateMap.put(state, totalSensorsOfCurrentState);
        }

        SensorNetDashboardResponse sensorNetDashboardResponse = SensorNetDashboardResponse.builder()
                .totalNetworks(networkSnToList.size())
                .totalCollectors(collectorSnToList.size())
                .totalSensors(sensorSnToList.size())
                .totalSensorsByStateMap(totalSensorsByStateMap)
                .build();

        return sensorNetDashboardResponse;
    }

    public VirtualSensorNetDashboardResponse getVirtualSensornetDashboard() throws AbstractRequestException {
        List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
        Map<VirtualSensorType, Map<State, Long>> totalByTypeAndState = new HashMap<>();
        for(VirtualSensorType virtualSensorType : VirtualSensorType.values()) {
            Map<State, Long> stateLongMap = new HashMap<>();
            for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnToList) {
                if(virtualSensorVsnTo.getSensorType().equals(virtualSensorType)) {
                    State currentSensorState = virtualSensorVsnTo.getState();
                    long totalByState = stateLongMap.get(currentSensorState) == null ? 0 : stateLongMap.get(currentSensorState);
                    stateLongMap.put(currentSensorState, totalByState + 1);
                }
            }
            totalByTypeAndState.put(virtualSensorType, stateLongMap);
        }

        long totalDataTypes = this.dataTypeRepository.getAll().size();

        VirtualSensorNetDashboardResponse virtualSensorNetDashboardResponse = VirtualSensorNetDashboardResponse.builder()
                .totalDataTypes(totalDataTypes)
                .totalByTypeAndState(totalByTypeAndState)
                .totalVirtualSensors(virtualSensorVsnToList.size())
                .build();

        return virtualSensorNetDashboardResponse;
    }
}
