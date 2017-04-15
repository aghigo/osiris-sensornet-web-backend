package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.InternalServerErrorException;
import br.uff.labtempo.omcp.common.exceptions.NotFoundException;
import br.uff.labtempo.osiris.generator.BlendingGenerator;
import br.uff.labtempo.osiris.mapper.BlendingMapper;
import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.repository.BlendingRepository;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.repository.VirtualSensorRepository;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.ValueVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorVsnTo;
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
    private VirtualSensorRepository virtualSensorRepository;

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
        try {
            //GET FUNCTION INTERFACE FROM FUNCTION MODULE
            InterfaceFnTo interfaceFnTo = this.functionRepository.getInterface(blendingRequest.getFunctionName());
            if(interfaceFnTo == null) {
                throw new NotFoundException(String.format("Failed to create Blending sensor: could not found %s function interface from Function module.", blendingRequest.getFunctionName()));
            }

            //CREATE A FUNCTION ONTO VIRTUALSENSORNET WITH THE FUNCTION INTERFACE
            FunctionVsnTo functionVsnTo = this.functionRepository.getFromVirtualSensorNet(blendingRequest.getFunctionName());
            if(functionVsnTo == null) {
                URI createdVsnFunctionUri = this.functionRepository.createOnVirtualSensorNet(interfaceFnTo);
                if(createdVsnFunctionUri == null) {
                    throw new NotFoundException(String.format("Failed to create Blending sensor: could not found/create %s function interface from/on VirtualSensorNet module.", blendingRequest.getFunctionName()));
                }
            }

            //CRETE BLENDING AND UPDATE IT WITH FUNCTION DATA
            BlendingVsnTo blendingVsnTo = BlendingMapper.requestToVsnTo(blendingRequest);

            blendingVsnTo.setFunction(functionVsnTo);
            blendingVsnTo.setCallMode(blendingRequest.getFunctionOperation());
            blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());

            //ADD REQUEST PARAMS
            List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
            for(VirtualSensorVsnTo virtualSensorVsnTo: virtualSensorVsnToList) {
                List<ValueVsnTo> valueVsnToList = virtualSensorVsnTo.getValuesTo();
                for(ValueVsnTo valueVsnTo : valueVsnToList) {
                    if(blendingRequest.getFields().keySet().contains(valueVsnTo.getName())) {
                        blendingVsnTo.addRequestParam(valueVsnTo.getId(), blendingRequest.getRequestParamName());
                        break;
                    }
                }
            }

            //ADD RESPONSE PARAMS
            List<? extends FieldTo> fieldToList = blendingVsnTo.getFields();
            FieldTo fieldTo = fieldToList.get(0);
            blendingVsnTo.addResponseParam(fieldTo.getId(), blendingRequest.getResponseParamName());

            //UPDATE COMPLETE BLENDING WITH FUNCTION DATA
            URI uri = this.blendingRepository.create(blendingVsnTo);

            //RETURN NEW BLENDING URI
            return uri;
        } catch (Exception e) {
            throw e;
        }
    }

    public void update(long blendingId, BlendingRequest blendingRequest) throws AbstractRequestException {
        BlendingVsnTo blendingVsnTo = BlendingMapper.requestToVsnTo(blendingRequest);
        this.blendingRepository.update(blendingId, blendingVsnTo);
    }

    public void delete(long blendingId) throws AbstractRequestException {
        this.blendingRepository.delete(blendingId);
    }

    public BlendingVsnTo getRandom() throws AbstractRequestException {
        return this.blendingGenerator.generateBlendingVsnTo();
    }
}