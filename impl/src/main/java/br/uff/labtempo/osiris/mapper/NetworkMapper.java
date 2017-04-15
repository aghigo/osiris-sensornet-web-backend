package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.NetworkRequest;
import br.uff.labtempo.osiris.model.response.NetworkResponse;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.sensornet.NetworkSnTo;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that maps and converts Network classes
 * NetworkSnTo to NetworkResponse
 * NetworkRequest to NetworkCoTo
 * NetworkSnTo to NetworkCoTo
 * @see NetworkRequest Network data sent by the client on POST/PUT HTTP requests
 * @see NetworkCoTo Network representation to send to the SensorNet module
 * @see NetworkSnTo Network representation sent by the SensorNet module
 * @see NetworkResponse Network response data sent by the API to the Client
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class NetworkMapper {

    /**
     * Converts a NetworkSnTo to a NetworkResponse
     * @see NetworkSnTo
     * @see NetworkResponse
     * @param networkSnTo
     * @return NetworkResponse
     */
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

    /**
     * Converts a List of NetworkSnTo to a List of NetworkResponse
     * @see NetworkSnTo
     * @see NetworkResponse
     * @param networkSnToList
     * @return List NetworkResponse
     */
    public static List<NetworkResponse> snToToResponse(List<NetworkSnTo> networkSnToList) {
        List<NetworkResponse> networkResponseList = new ArrayList<>();
        for(NetworkSnTo networkSnTo : networkSnToList) {
            networkResponseList.add(snToToResponse(networkSnTo));
        }
        return networkResponseList;
    }

    /**
     * Converts a NetworkRequest to a NetworkCoTo
     * @see NetworkRequest
     * @see NetworkCoTo
     * @param networkRequest
     * @return NetworkCoTo
     */
    public static NetworkCoTo requestToCoTo(NetworkRequest networkRequest) {
        NetworkCoTo networkCoTo = new NetworkCoTo(networkRequest.getId());
        networkCoTo.addInfo(networkRequest.getInfo());
        return networkCoTo;
    }

    /**
     * Converts a List of NetworkRequest to a List of NetworkCoTo
     * @see NetworkRequest
     * @see NetworkCoTo
     * @param networkRequestList
     * @return List of NetworkCoTo
     */
    public static List<NetworkCoTo> requestToCoTo(List<NetworkRequest> networkRequestList) {
        List<NetworkCoTo> networkCoToList = new ArrayList<>();
        for(NetworkRequest networkRequest : networkRequestList) {
            networkCoToList.add(requestToCoTo(networkRequest));
        }
        return networkCoToList;
    }

    /**
     * Converts a NetworkSnTo to a NetworkCoTo
     * @see NetworkSnTo
     * @see NetworkCoTo
     * @param networkSnTo
     * @return NetworkCoTo
     */
    public static NetworkCoTo snToToCoTo(NetworkSnTo networkSnTo) {
        NetworkCoTo networkCoTo = new NetworkCoTo(networkSnTo.getId());
        networkCoTo.addInfo(networkSnTo.getInfo());
        return networkCoTo;
    }

    /**
     * Converts a List of NetworkSnTo to a List of NetworkCoTo
     * @param networkSnToList
     * @return List of NetworkCoTo
     */
    public static List<NetworkCoTo> snToToCoTo(List<NetworkSnTo> networkSnToList) {
        List<NetworkCoTo> networkCoToList = new ArrayList<>();
        for(NetworkSnTo networkSnTo : networkSnToList) {
            networkCoToList.add(snToToCoTo(networkSnTo));
        }
        return networkCoToList;
    }
}
