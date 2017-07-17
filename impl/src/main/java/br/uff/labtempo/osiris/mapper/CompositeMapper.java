package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.CompositeRequest;
import br.uff.labtempo.osiris.model.response.CompositeResponse;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that maps and converts Composite sensor classes
 * CompositeRequest to CompositeVsnTo
 * CompositeVsnTo to CompositeResponse
 * @see CompositeRequest Composite data sent by POST/PUT requests
 * @see CompositeVsnTo Blending representation on VirtualSensorNet module
 * @see CompositeResponse Composite data sent by the API to the client
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class CompositeMapper {

    /**
     * Converts a CompositeRequest to a CompositeVsnTo
     * @see CompositeRequest
     * @see CompositeVsnTo
     * @param compositeRequest
     * @return CompositeVsnTo
     */
    public static CompositeVsnTo requestToVsnTo(CompositeRequest compositeRequest) {
        CompositeVsnTo compositeVsnTo = new CompositeVsnTo();
        for(Long fieldId : compositeRequest.getFieldIds()) {
            compositeVsnTo.bindToField(fieldId.longValue());
        }
        return compositeVsnTo;
    }

    /**
     * Converts a list of CompositeRequest to a list of CompositeVsnTo
     * @see CompositeRequest
     * @see CompositeVsnTo
     * @param compositeRequestList
     * @return List of CompositeVsnTo
     */
    public static List<CompositeVsnTo> requestToVsnTo(List<CompositeRequest> compositeRequestList) {
        List<CompositeVsnTo> compositeVsnToList = new ArrayList<>();
        for(CompositeRequest compositeRequest : compositeRequestList) {
            compositeVsnToList.add(requestToVsnTo(compositeRequest));
        }
        return compositeVsnToList;
    }

    /**
     * Converts a CompositeVsnTo to CompositeResponse
     * @see CompositeVsnTo
     * @see CompositeResponse
     * @param compositeVsnTo
     * @return CompositeResponse
     */
    public static CompositeResponse vsnToToResponse(CompositeVsnTo compositeVsnTo) {
        CompositeResponse compositeResponse = new CompositeResponse();
        compositeResponse.setFields((List<FieldTo>) compositeVsnTo.getBoundFields());
        compositeResponse.setId(compositeVsnTo.getId());
        compositeResponse.setLabel(compositeVsnTo.getLabel());
        return compositeResponse;
    }

    /**
     * Converts a list of CompositeVsnTo to a List of CompositeResponse
     * @see CompositeVsnTo
     * @see CompositeResponse
     * @param compositeVsnToList
     * @return List of CompositeResponse
     */
    public static List<CompositeResponse> vsnToToResponse(List<CompositeVsnTo> compositeVsnToList) {
        List<CompositeResponse> compositeResponseList = new ArrayList<>();
        for(CompositeVsnTo compositeVsnTo : compositeVsnToList) {
            compositeResponseList.add(vsnToToResponse(compositeVsnTo));
        }
        return compositeResponseList;
    }
}
