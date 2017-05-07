package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.ValueVsnTo;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that maps and converts DataType classes
 * DataTypeRequest to DataTypeVsnTo
 * DataTypeVsnTo to DataTypeResponse
 * @see DataTypeRequest DataType data sent by the client on POST/PUT HTTP requests
 * @see DataTypeVsnTo DataType representation on the VirtualSensorNet module domain
 * @see DataTypeResponse DataType data sent by the API to the Client
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class DataTypeMapper {

    /**
     * Converts a DataTypeRequest to a DataTypeVsnTo
     * @see DataTypeRequest
     * @see DataTypeVsnTo
     * @param dataTypeRequest
     * @return DataTypeVsnTo
     */
    public static DataTypeVsnTo requestToVsnTo(DataTypeRequest dataTypeRequest) {
        DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(dataTypeRequest.getName(), dataTypeRequest.getType()
        , dataTypeRequest.getUnit(), dataTypeRequest.getSymbol());
        return dataTypeVsnTo;
    }

    /**
     * Converts a List of DataTypeRequest to a List of DataTypeVsnTo
     * @see DataTypeRequest
     * @see DataTypeVsnTo
     * @param dataTypeRequestList
     * @return List of DataTypeVsnTo
     */
    public static List<DataTypeVsnTo> requestToVsnTo(List<DataTypeRequest> dataTypeRequestList) {
        List<DataTypeVsnTo> dataTypeVsnToList = new ArrayList<>();
        for(DataTypeRequest dataTypeRequest : dataTypeRequestList) {
            DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(dataTypeRequest.getName(), dataTypeRequest.getType()
                    , dataTypeRequest.getUnit(), dataTypeRequest.getSymbol());
            dataTypeVsnToList.add(dataTypeVsnTo);
        }
        return dataTypeVsnToList;
    }

    /**
     * Converts a DataTypeVsnTo to a DataTypeResponse
     * @see DataTypeVsnTo
     * @see DataTypeResponse
     * @param dataTypeVsnTo
     * @return DataTypeResponse
     */
    public static DataTypeResponse vsnToToResponse(DataTypeVsnTo dataTypeVsnTo) {
        DataTypeResponse dataTypeResponse = new DataTypeResponse(dataTypeVsnTo.getId(), dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getType(),
                dataTypeVsnTo.getUnit(), dataTypeVsnTo.getSymbol(), dataTypeVsnTo.getUsedBy());
        return dataTypeResponse;
    }

    /**
     * Converts a List of DataTypeVsnTo to a List of DataTypeResponse
     * @see DataTypeVsnTo
     * @see DataTypeResponse
     * @param dataTypeVsnToList
     * @return List of DataTypeResponse
     */
    public static List<DataTypeResponse> vsnToToResponse(List<DataTypeVsnTo> dataTypeVsnToList) {
        List<DataTypeResponse> dataTypeResponseList = new ArrayList<>();
        for(DataTypeVsnTo dataTypeVsnTo : dataTypeVsnToList) {
            dataTypeResponseList.add(vsnToToResponse(dataTypeVsnTo));
        }
        return dataTypeResponseList;
    }

    /**
     * Converts a ValueTo into a DataTypeVsnTo object
     * @param valueTo
     * @return DataTYpeVsnTo
     */
    public static DataTypeVsnTo valueToToVsnTo(ValueTo valueTo) {
        DataTypeVsnTo dataTypeVsnTo = new DataTypeVsnTo(valueTo.getName(), valueTo.getType(), valueTo.getUnit(), valueTo.getSymbol());
        dataTypeVsnTo.setUsedBy(0);
        return dataTypeVsnTo;
    }
}
