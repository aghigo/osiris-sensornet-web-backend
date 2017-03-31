package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.NetworkRequest;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;

import java.util.ArrayList;
import java.util.List;

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
    public static List<NetworkResponse> toResponse(List<NetworkSnTo> networkSnToList) {
        List<NetworkResponse> networkResponseList = new ArrayList<>();
        for(NetworkSnTo networkSnTo : networkSnToList) {
            networkResponseList.add(toResponse(networkSnTo));
        }
        return networkResponseList;
    }

    public static NetworkCoTo toCoTo(NetworkRequest networkRequest) {
        NetworkCoTo networkCoTo = new NetworkCoTo(networkRequest.getId());
        networkCoTo.addInfo(networkRequest.getInfo());
        return networkCoTo;
    }

    public static List<NetworkCoTo> toCoTo(List<NetworkRequest> networkRequestList) {
        List<NetworkCoTo> networkCoToList = new ArrayList<>();
        for(NetworkRequest networkRequest : networkRequestList) {
            networkCoToList.add(toCoTo(networkRequest));
        }
        return networkCoToList;
    }
}
