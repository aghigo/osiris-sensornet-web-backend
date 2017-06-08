package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that maps and converts Blending sensors classes
 * (Request to VsnTo), (VsnTo to Response) and vice-versa
 * Request = data sent on HTTP POST/PUT requests
 * VsnTo = Blending representation in the OSIRIS API
 * Response = data sent as response by the API to the client
 * @see BlendingVsnTo
 * @see BlendingRequest
 * @see BlendingResponse
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class BlendingMapper {

    /**
     * Converts BlendingRequest to BlendingVsnTo
     * @see BlendingVsnTo
     * @see BlendingRequest
     * @param blendingRequest
     * @return BlendingVsnTo
     */
    public static BlendingVsnTo requestToVsnTo(BlendingRequest blendingRequest) {
        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());
        blendingVsnTo.createField(blendingRequest.getResponseParamName(), blendingRequest.getResponseDataTypeId());
        return blendingVsnTo;
    }

    /**
     * Converts a list of BlendingRequest to a list of BlendingVsnTo
     * @param blendingRequestList
     * @return List of BlendingVsnTo
     */
    public static List<BlendingVsnTo> requestToVsnTo(List<BlendingRequest> blendingRequestList) {
        List<BlendingVsnTo> blendingVsnToList = new ArrayList<>();
        for(BlendingRequest blendingRequest : blendingRequestList) {
            blendingVsnToList.add(requestToVsnTo(blendingRequest));
        }
        return blendingVsnToList;
    }

    /**
     * Converts a BlendingVsnTo to a BlendingResponse
     * @see BlendingVsnTo
     * @see BlendingResponse
     * @param blendingVsnTo
     * @return BlendingResponse
     */
    public static BlendingResponse vsnToToResponse(BlendingVsnTo blendingVsnTo) {
        return BlendingResponse.builder()
                .callIntervalInMillis(blendingVsnTo.getCallIntervalInMillis())
                .fields(blendingVsnTo.getFields())
                .functionId(blendingVsnTo.getFunctionId())
                .id(blendingVsnTo.getId())
                .label(blendingVsnTo.getLabel())
                .requestParams(blendingVsnTo.getRequestParams())
                .responseParams(blendingVsnTo.getResponseParams())
                .build();
    }

    /**
     * Converts a list of BlendingVsnTo to a list of BlendingResponse
     * @see BlendingVsnTo
     * @see BlendingResponse
     * @param blendingVsnToList
     * @return List of BlendingResponse
     */
    public static List<BlendingResponse> vsnToToResponse(List<BlendingVsnTo> blendingVsnToList) {
        List<BlendingResponse> blendingResponseList = new ArrayList<>();
        for(BlendingVsnTo blendingVsnTo : blendingVsnToList) {
            blendingResponseList.add(vsnToToResponse(blendingVsnTo));
        }
        return blendingResponseList;
    }
}
