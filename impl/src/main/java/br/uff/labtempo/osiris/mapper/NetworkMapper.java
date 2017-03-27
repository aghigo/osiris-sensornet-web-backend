package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.request.NetworkRequest;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class NetworkMapper {

    public static NetworkCoTo toCoTo(@Valid NetworkRequest networkRequest) {
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

    public static NetworkResponse toResponse(NetworkCoTo networkCoTo) {
        return NetworkResponse.builder()
                .id(networkCoTo.getId())
                .info(networkCoTo.getInfo())
                .build();
    }

    public static List<NetworkResponse> toResponse(List<NetworkCoTo> networkCoToList) {
        List<NetworkResponse> networkResponseList = new ArrayList<>();
        for(NetworkCoTo networkCoTo : networkCoToList) {
            networkResponseList.add(toResponse(networkCoTo));
        }
        return networkResponseList;
    }
}
