package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;

import java.util.ArrayList;
import java.util.List;

public class DataTypeMapper {
    public static DataTypeVsnTo toVsnTo(DataTypeRequest dataTypeRequest) {
        DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(dataTypeRequest.getName(), dataTypeRequest.getType()
        , dataTypeRequest.getUnit(), dataTypeRequest.getSymbol());
        return dataTypeVsnTo;
    }

    public static List<DataTypeVsnTo> toVsnTo(List<DataTypeRequest> dataTypeRequestList) {
        List<DataTypeVsnTo> dataTypeVsnToList = new ArrayList<>();
        for(DataTypeRequest dataTypeRequest : dataTypeRequestList) {
            DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(dataTypeRequest.getName(), dataTypeRequest.getType()
                    , dataTypeRequest.getUnit(), dataTypeRequest.getSymbol());
            dataTypeVsnToList.add(dataTypeVsnTo);
        }
        return dataTypeVsnToList;
    }

    public static DataTypeResponse toResponse(DataTypeVsnTo dataTypeVsnTo) {
        DataTypeResponse dataTypeResponse = new DataTypeResponse(dataTypeVsnTo.getId(), dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getType(),
                dataTypeVsnTo.getUnit(), dataTypeVsnTo.getSymbol(), dataTypeVsnTo.getUsedBy());
        return dataTypeResponse;
    }

    public static List<DataTypeResponse> toResponse(List<DataTypeVsnTo> dataTypeVsnToList) {
        List<DataTypeResponse> dataTypeResponseList = new ArrayList<>();
        for(DataTypeVsnTo dataTypeVsnTo : dataTypeVsnToList) {
            dataTypeResponseList.add(toResponse(dataTypeVsnTo));
        }
        return dataTypeResponseList;
    }
}
