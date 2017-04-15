package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.CollectorRequest;
import br.uff.labtempo.osiris.model.response.CollectorResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.sensornet.CollectorSnTo;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that maps and converts Collectors classes
 * (CollectorSnTo to CollectorResponse and vice-versa)
 * (CollectorRequest to CollectorCoTo and vice-versa)
 * (CollectorCoTo to CollectorSnTo and vice-versa)
 * Request = data sent on HTTP POST/PUT requests
 * CoTo = Collector representation to send to SensorNet module
 * *SnTo =  Collector representation sent by SensorNet module
 * Response = data sent as response by the API to the client
 * @see CollectorRequest
 * @see CollectorCoTo
 * @see CollectorSnTo
 * @see CollectorResponse
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class CollectorMapper {

    /**
     * Converts a CollectorSnTo to a CollectorResponse
     * @see CollectorResponse
     * @see CollectorSnTo
     * @param collectorSnTo
     * @return CollectorResponse
     */
    public static CollectorResponse snToToResponse(CollectorSnTo collectorSnTo) {
        CollectorResponse collectorResponse = CollectorResponse.builder()
                .info(collectorSnTo.getInfo())
                .captureInterval(collectorSnTo.getCaptureInterval())
                .captureIntervaltimeUnit(collectorSnTo.getCaptureIntervalTimeUnit())
                .id(collectorSnTo.getId())
                .lastModified(collectorSnTo.getLastModified().getTimeInMillis())
                .networkId(collectorSnTo.getNetworkId())
                .state(collectorSnTo.getState())
                .totalSensors(collectorSnTo.getTotalSensors())
                .build();
        return collectorResponse;
    }

    /**
     * Converts a list of CollectorResponse to a list of CollectorSnTo
     * @see CollectorSnTo
     * @see CollectorResponse
     * @param collectorSnToList
     * @return List of CollectorResponse
     */
    public static List<CollectorResponse> snToToResponse(List<CollectorSnTo> collectorSnToList) {
        List<CollectorResponse> collectorResponseList = new ArrayList<>();
        for(CollectorSnTo collectorSnTo : collectorSnToList) {
            collectorResponseList.add(snToToResponse(collectorSnTo));
        }
        return collectorResponseList;
    }

    /**
     * Converts a CollectorRequest to a CollectorCoTo
     * @see CollectorRequest
     * @see CollectorCoTo
     * @param collectorRequest
     * @return CollectorCoTo
     */
    public static CollectorCoTo requestToCoTo(CollectorRequest collectorRequest) {
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorRequest.getId(),
                collectorRequest.getCaptureInterval(), collectorRequest.getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(collectorRequest.getInfo());
        return collectorCoTo;
    }

    /**
     * Converts a list of CollectorRequest to a list of CollectorCoTo
     * @see CollectorRequest
     * @see CollectorCoTo
     * @param collectorRequestList
     * @return
     */
    public static List<CollectorCoTo> requestToCoTo(List<CollectorRequest> collectorRequestList) {
        List<CollectorCoTo> collectorCoToList = new ArrayList<>();
        for(CollectorRequest collectorRequest : collectorRequestList) {
            collectorCoToList.add(requestToCoTo(collectorRequest));
        }
        return collectorCoToList;
    }

    /**
     * Converts a CollectorSnTo to a CollectorCoTo
     * @see CollectorSnTo
     * @see CollectorCoTo
     * @param collectorSnTo
     * @return
     */
    public static CollectorCoTo snToToCoTo(CollectorSnTo collectorSnTo) {
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorSnTo.getId(), collectorSnTo.getCaptureInterval(),
                collectorSnTo.getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(collectorSnTo.getInfo());
        return collectorCoTo;
    }

    /**
     * Converts a list of CollectorSnTo to a list of CollectorCoTo
     * @see CollectorSnTo
     * @see CollectorCoTo
     * @param collectorSnToList
     * @return List of CollectorCoTo
     */
    public static List<CollectorCoTo> snToToCoTo(List<CollectorSnTo> collectorSnToList) {
        List<CollectorCoTo> collectorCoToList = new ArrayList<>();
        for(CollectorSnTo collectorSnTo : collectorSnToList) {
            collectorCoToList.add(snToToCoTo(collectorSnTo));
        }
        return collectorCoToList;
    }

    /**
     * Converts a CollectorResponse to a CollectorCoTo
     * @see CollectorResponse
     * @see CollectorCoTo
     * @param collectorResponse
     * @return
     */
    public static CollectorCoTo responseToCoTo(CollectorResponse collectorResponse) {
        CollectorCoTo collectorCoTo = new CollectorCoTo(collectorResponse.getId(), collectorResponse.getCaptureInterval(), collectorResponse.getCaptureIntervaltimeUnit());
        collectorCoTo.addInfo(collectorResponse.getInfo());
        return collectorCoTo;
    }

    /**
     * Converts a list of CollectorResponse to a List of CollectorCoTo
     * @see CollectorResponse
     * @see CollectorCoTo
     * @param collectorResponseList
     * @return
     */
    public static List<CollectorCoTo> responseToCoTo(List<CollectorResponse> collectorResponseList) {
        List<CollectorCoTo> collectorCoToList = new ArrayList<>();
        for(CollectorResponse collectorResponse : collectorResponseList) {
            collectorCoToList.add(responseToCoTo(collectorResponse));
        }
        return collectorCoToList;
    }
}
