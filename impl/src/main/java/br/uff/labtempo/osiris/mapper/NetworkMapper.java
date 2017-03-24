package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.request.NetworkRequest;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;

public class NetworkMapper {

    public static NetworkCoTo toCoTo(NetworkRequest networkRequest) {
        NetworkCoTo networkCoTo = new NetworkCoTo(networkRequest.getId());
        networkCoTo.addInfo(networkCoTo.getInfo());
        return networkCoTo;
    }

    public static NetworkResponse toResponse(NetworkCoTo networkCoTo) {
        return NetworkResponse.builder()
                .id(networkCoTo.getId())
                .info(networkCoTo.getInfo())
                .build();
    }

}
