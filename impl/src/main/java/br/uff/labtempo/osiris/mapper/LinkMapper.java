package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that maps and converts Link sensors classes
 * LinkRequest to LinkVsnTo
 * LinkVsnTo to LinkResponse
 * @see LinkRequest Link sensor data sent by the client on HTTP POST/PUT requests
 * @see LinkVsnTo Link representation on VirtualSensorNet module
 * @see LinkResponse Link sensor data sent by the API to the client
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class LinkMapper {

    /**
     * Converts LinkRequest to LinkVsnTo
     * @see LinkRequest
     * @see LinkVsnTo
     * @param linkRequest
     * @return LinkVsnTo
     */
    public static LinkVsnTo requestToVsnTo(LinkRequest linkRequest) {
        LinkVsnTo linkVsnTo = new LinkVsnTo(linkRequest.getSensorId(), linkRequest.getCollectorId(), linkRequest.getNetworkId());
        linkVsnTo.setLabel(linkRequest.getLabel());
        Map<String, Long> fields = linkRequest.getFields();
        for(String fieldName : fields.keySet()) {
            linkVsnTo.createField(fieldName, fields.get(fieldName));
        }
        return linkVsnTo;
    }

    public static LinkVsnTo requestToVsnTo(long id, LinkRequest linkRequest) {
        LinkVsnTo linkVsnTo = new LinkVsnTo(id, linkRequest.getLabel(), linkRequest.getSensorId(), linkRequest.getCollectorId(), linkRequest.getNetworkId());
        Map<String, Long> fields = linkRequest.getFields();
        for(String fieldName : fields.keySet()) {
            linkVsnTo.createField(fieldName, fields.get(fieldName));
        }
        return linkVsnTo;
    }

    /**
     * Converts a List of LinkRequest to a List of LinkVsnTo
     * @see LinkRequest
     * @see LinkVsnTo
     * @param linkRequestList
     * @return
     */
    public static List<LinkVsnTo> requestToVsnTo(List<LinkRequest> linkRequestList) {
        List<LinkVsnTo> linkVsnToList = new ArrayList<>();
        for(LinkRequest linkRequest : linkRequestList) {
            linkVsnToList.add(requestToVsnTo(linkRequest));
        }
        return linkVsnToList;
    }

    /**
     * Converts a LinkVsnTo to a LinkResponse
     * @see LinkVsnTo
     * @see LinkResponse
     * @param linkVsnTo
     * @return LinkResponse
     */
    public static LinkResponse vsnToToResponse(LinkVsnTo linkVsnTo) {
        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setId(linkVsnTo.getId());
        linkResponse.setSensorId(linkVsnTo.getSensorId());
        linkResponse.setCollectorId(linkVsnTo.getCollectorId());
        linkResponse.setNetworkId(linkVsnTo.getNetworkId());
        linkResponse.setLabel(linkVsnTo.getLabel());
        linkResponse.setFields((List<FieldTo>) linkVsnTo.getFields());
        return linkResponse;
    }

    /**
     * Converts a List of LinkVsnTo to a List of LinkResponse
     * @param linkVsnToList
     * @return List of LinkResponse
     */
    public static List<LinkResponse> vsnToToResponse(List<LinkVsnTo> linkVsnToList) {
        List<LinkResponse> linkResponseList = new ArrayList<>();
        for(LinkVsnTo linkVsnTo : linkVsnToList) {
            linkResponseList.add(vsnToToResponse(linkVsnTo));
        }
        return linkResponseList;
    }
}
