package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;

public class NetworkMapper {
    public static NetworkResponse toResponse(NetworkSnTo networkSnTo) {
        NetworkResponse networkResponse = NetworkResponse.builder()
                .lastModified(networkSnTo.getLastModified().getTimeInMillis())
                .id(networkSnTo.getId())
                .info(networkSnTo.getInfo())
                .state(networkSnTo.getState())
                .totalCollectors(networkSnTo.getTotalCollectors())
                .totalSensors(networkSnTo.getTotalSensors())
                .build();

        return networkResponse;
    }
}
