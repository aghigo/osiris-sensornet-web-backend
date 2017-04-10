package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlendingMapper {
    public static BlendingVsnTo requestToVsnTo(BlendingRequest blendingRequest) {
        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        blendingVsnTo.setCallMode(blendingRequest.getFunctionOperation());
        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());
        Map<String, Long> fieldMap = blendingRequest.getFields();
        for(String fieldName : fieldMap.keySet()) {
            blendingVsnTo.createField(fieldName, fieldMap.get(fieldName));
        }
        return blendingVsnTo;
    }

    public static List<BlendingVsnTo> requestToVsnTo(List<BlendingRequest> blendingRequestList) {
        List<BlendingVsnTo> blendingVsnToList = new ArrayList<>();
        for(BlendingRequest blendingRequest : blendingRequestList) {
            blendingVsnToList.add(requestToVsnTo(blendingRequest));
        }
        return blendingVsnToList;
    }

    public static BlendingResponse vsnToToResponse(BlendingVsnTo blendingVsnTo) {
        return null;
    }

    public static List<BlendingResponse> vsnToToResponse(List<BlendingVsnTo> blendingVsnToList) {
        return null;
    }
}
