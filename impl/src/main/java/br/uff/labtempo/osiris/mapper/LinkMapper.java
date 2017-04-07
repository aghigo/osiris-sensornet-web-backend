package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.LinkRequest;
import br.uff.labtempo.osiris.model.response.LinkResponse;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LinkMapper {
    public static LinkVsnTo toVsnTo(LinkRequest linkRequest) {
        LinkVsnTo linkVsnTo = new LinkVsnTo(linkRequest.getSensorId(), linkRequest.getCollectorId(), linkRequest.getNetworkId());
        Map<String, Long> fields = linkRequest.getField();
        for(String fieldName : fields.keySet()) {
            linkVsnTo.createField(fieldName, fields.get(fieldName));
        }
        return linkVsnTo;
    }

    public static List<LinkVsnTo> toVsnTo(List<LinkRequest> linkRequestList) {
        List<LinkVsnTo> linkVsnToList = new ArrayList<>();
        for(LinkRequest linkRequest : linkRequestList) {
            linkVsnToList.add(toVsnTo(linkRequest));
        }
        return linkVsnToList;
    }

    public static LinkResponse toResponse(LinkVsnTo linkVsnTo) {
        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setId(linkVsnTo.getId());
        linkResponse.setSensorId(linkVsnTo.getSensorId());
        linkResponse.setCollectorId(linkVsnTo.getCollectorId());
        linkResponse.setNetworkId(linkVsnTo.getNetworkId());
        linkResponse.setLabel(linkVsnTo.getLabel());
        linkResponse.setField(new LinkedHashMap<>());
        for(FieldTo fieldTo : linkVsnTo.getFields()) {
            linkResponse.getField().put(fieldTo.getName(), fieldTo.getDataTypeId());
        }
        return linkResponse;
    }

    public static List<LinkResponse> toResponse(List<LinkVsnTo> linkVsnToList) {
        List<LinkResponse> linkResponseList = new ArrayList<>();
        for(LinkVsnTo linkVsnTo : linkVsnToList) {
            linkResponseList.add(toResponse(linkVsnTo));
        }
        return linkResponseList;
    }
}
