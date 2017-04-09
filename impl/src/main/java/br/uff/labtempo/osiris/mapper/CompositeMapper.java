package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.CompositeRequest;
import br.uff.labtempo.osiris.model.response.CompositeResponse;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;

import java.util.ArrayList;
import java.util.List;

public class CompositeMapper {
    public static CompositeVsnTo requestToVsnTo(CompositeRequest compositeRequest) {
        CompositeVsnTo compositeVsnTo = new CompositeVsnTo();
        for(Long fieldId : compositeRequest.getFields()) {
            compositeVsnTo.bindToField(fieldId);
        }
        return compositeVsnTo;
    }

    public static List<CompositeVsnTo> requestToVsnTo(List<CompositeRequest> compositeRequestList) {
        List<CompositeVsnTo> compositeVsnToList = new ArrayList<>();
        for(CompositeRequest compositeRequest : compositeRequestList) {
            compositeVsnToList.add(requestToVsnTo(compositeRequest));
        }
        return compositeVsnToList;
    }

    public static CompositeResponse vsnToToResponse(CompositeVsnTo compositeVsnTo) {
        CompositeResponse compositeResponse = new CompositeResponse();
        List<Long> fieldIdList = new ArrayList<>();
        for (FieldTo fieldTo : compositeVsnTo.getBoundFields()) {
            fieldIdList.add(fieldTo.getId());
        }
        compositeResponse.setFields(fieldIdList);
        return compositeResponse;
    }

    public static List<CompositeResponse> vsnToToResponse(List<CompositeVsnTo> compositeVsnToList) {
        List<CompositeResponse> compositeResponseList = new ArrayList<>();
        for(CompositeVsnTo compositeVsnTo : compositeVsnToList) {
            compositeResponseList.add(vsnToToResponse(compositeVsnTo));
        }
        return compositeResponseList;
    }
}
