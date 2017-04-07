package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.NetworkRequest;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;

import java.util.ArrayList;
import java.util.List;

public class NetworkMapper {
    public static NetworkResponse snToToResponse(NetworkSnTo networkSnTo) {
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
    public static List<NetworkResponse> snToToResponse(List<NetworkSnTo> networkSnToList) {
        List<NetworkResponse> networkResponseList = new ArrayList<>();
        for(NetworkSnTo networkSnTo : networkSnToList) {
            networkResponseList.add(snToToResponse(networkSnTo));
        }
        return networkResponseList;
    }

    public static NetworkCoTo requestToCoTo(NetworkRequest networkRequest) {
        NetworkCoTo networkCoTo = new NetworkCoTo(networkRequest.getId());
        networkCoTo.addInfo(networkRequest.getInfo());
        return networkCoTo;
    }

    public static List<NetworkCoTo> requestToCoTo(List<NetworkRequest> networkRequestList) {
        List<NetworkCoTo> networkCoToList = new ArrayList<>();
        for(NetworkRequest networkRequest : networkRequestList) {
            networkCoToList.add(requestToCoTo(networkRequest));
        }
        return networkCoToList;
    }

    public static NetworkCoTo snToToCoTo(NetworkSnTo networkSnTo) {
        NetworkCoTo networkCoTo = new NetworkCoTo(networkSnTo.getId());
        networkCoTo.addInfo(networkSnTo.getInfo());
        return networkCoTo;
    }

    public static List<NetworkCoTo> snToToCoTo(List<NetworkSnTo> networkSnToList) {
        List<NetworkCoTo> networkCoToList = new ArrayList<>();
        for(NetworkSnTo networkSnTo : networkSnToList) {
            networkCoToList.add(snToToCoTo(networkSnTo));
        }
        return networkCoToList;
    }
}
