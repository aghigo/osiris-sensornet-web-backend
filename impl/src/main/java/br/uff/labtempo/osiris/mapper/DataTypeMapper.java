package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;

import java.util.ArrayList;
import java.util.List;

public class DataTypeMapper {
    public static DataTypeVsnTo requestToVsnTo(DataTypeRequest dataTypeRequest) {
        DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(dataTypeRequest.getName(), dataTypeRequest.getType()
        , dataTypeRequest.getUnit(), dataTypeRequest.getSymbol());
        return dataTypeVsnTo;
    }

    public static List<DataTypeVsnTo> requestToVsnTo(List<DataTypeRequest> dataTypeRequestList) {
        List<DataTypeVsnTo> dataTypeVsnToList = new ArrayList<>();
        for(DataTypeRequest dataTypeRequest : dataTypeRequestList) {
            DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(dataTypeRequest.getName(), dataTypeRequest.getType()
                    , dataTypeRequest.getUnit(), dataTypeRequest.getSymbol());
            dataTypeVsnToList.add(dataTypeVsnTo);
        }
        return dataTypeVsnToList;
    }

    public static DataTypeResponse vsnToToResponse(DataTypeVsnTo dataTypeVsnTo) {
        DataTypeResponse dataTypeResponse = new DataTypeResponse(dataTypeVsnTo.getId(), dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getType(),
                dataTypeVsnTo.getUnit(), dataTypeVsnTo.getSymbol(), dataTypeVsnTo.getUsedBy());
        return dataTypeResponse;
    }

    public static List<DataTypeResponse> vsnToToResponse(List<DataTypeVsnTo> dataTypeVsnToList) {
        List<DataTypeResponse> dataTypeResponseList = new ArrayList<>();
        for(DataTypeVsnTo dataTypeVsnTo : dataTypeVsnToList) {
            dataTypeResponseList.add(vsnToToResponse(dataTypeVsnTo));
        }
        return dataTypeResponseList;
    }
}
