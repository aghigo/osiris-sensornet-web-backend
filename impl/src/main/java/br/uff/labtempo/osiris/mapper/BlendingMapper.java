package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;

import java.util.List;

public class BlendingMapper {
    public static BlendingVsnTo requestToVsnTo(BlendingRequest blendingRequest) {
        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        return null;
    }

    public static BlendingResponse vsnToToResponse(BlendingVsnTo blendingVsnTo) {
        return null;
    }

    public static List<BlendingResponse> vsnToToResponse(List<BlendingVsnTo> blendingVsnToList) {
        return null;
    }
}
