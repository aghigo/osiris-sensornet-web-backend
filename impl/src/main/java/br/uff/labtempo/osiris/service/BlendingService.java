package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.generator.BlendingGenerator;
import br.uff.labtempo.osiris.mapper.BlendingMapper;
import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.repository.BlendingRepository;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class BlendingService {
    @Autowired
    private BlendingRepository blendingRepository;

    @Autowired
    private FunctionRepository functionRepository;
    private BlendingGenerator blendingGenerator;

    public List<BlendingResponse> getAll() throws AbstractRequestException {
        List<BlendingVsnTo> blendingVsnToList = this.blendingRepository.getAll();
        List<BlendingResponse> blendingResponseList = BlendingMapper.vsnToToResponse(blendingVsnToList);
        return blendingResponseList;
    }

    public BlendingResponse getById(long blendingId) throws AbstractRequestException {
        BlendingVsnTo blendingVsnTo = this.blendingRepository.getById(blendingId);
        BlendingResponse blendingResponse = BlendingMapper.vsnToToResponse(blendingVsnTo);
        return blendingResponse;
    }

    public URI create(BlendingRequest blendingRequest) throws URISyntaxException, AbstractRequestException {
        //CREATE BASIC BLENDING
        BlendingVsnTo blendingVsnTo = BlendingMapper.requestToVsnTo(blendingRequest);
        URI uri = this.blendingRepository.create(blendingVsnTo);

        //GET FUNCTION INTERFACE FROM FUNCTION MODULE
        InterfaceFnTo interfaceFnTo = this.functionRepository.getInterface(blendingRequest.getFunctionName());

        //CREATE A FUNCTION ONTO VIRTUALSENSORNET WITH THE FUNCTION INTERFACE
        this.functionRepository.createOnVirtualSensorNet(interfaceFnTo);
        FunctionVsnTo functionVsnTo = this.functionRepository.getFromVirtualSensorNet(blendingRequest.getFunctionName());

        //UPDATE BLENDING WITH FUNCTION DATA
        this.blendingRepository.update(blendingVsnTo.getId(), blendingVsnTo);

        return uri;
    }

    public void update(long blendingId, BlendingRequest blendingRequest) throws AbstractRequestException {
        BlendingVsnTo blendingVsnTo = BlendingMapper.requestToVsnTo(blendingRequest);
        this.blendingRepository.update(blendingId, blendingVsnTo);
    }

    public void delete(long blendingId) throws AbstractRequestException {
        this.blendingRepository.delete(blendingId);
    }

    public BlendingVsnTo getRandom() {
        return this.blendingGenerator.generateBlendingVsnTo();
    }
}