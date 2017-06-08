package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
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
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
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

/**
 * Service with business rules to create/update/get Blending sensors from VirtualSensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class BlendingService {
    @Autowired
    private BlendingRepository blendingRepository;

    @Autowired
    private VirtualSensorRepository virtualSensorRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private BlendingGenerator blendingGenerator;

    /**
     * Get a list of all available Blending sensors from VirtualSensorNet module
     * @see BlendingResponse
     * @return List of BlendingResponse
     * @throws AbstractRequestException
     */
    public List<BlendingResponse> getAll() throws AbstractRequestException {
        List<BlendingVsnTo> blendingVsnToList = this.blendingRepository.getAll();
        List<BlendingResponse> blendingResponseList = BlendingMapper.vsnToToResponse(blendingVsnToList);
        return blendingResponseList;
    }

    /**
     * Get a specific Blending sensor from VirtualSensorNet by its unique Id
     * @param blendingId
     * @return BlendingResponse
     * @throws AbstractRequestException
     */
    public BlendingResponse getById(long blendingId) throws AbstractRequestException {
        BlendingVsnTo blendingVsnTo = this.blendingRepository.getById(blendingId);
        BlendingResponse blendingResponse = BlendingMapper.vsnToToResponse(blendingVsnTo);
        return blendingResponse;
    }

    /**
     * Create a new Blending sensor on VirtualSensorNet module
     * @param blendingRequest
     * @return URI with the new Blending location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI create(BlendingRequest blendingRequest) throws URISyntaxException, AbstractRequestException {
        try {
            //CREATE BLENDING RESOURCE
            BlendingVsnTo blendingVsnTo = BlendingMapper.requestToVsnTo(blendingRequest);
            URI newBlendingUri = this.blendingRepository.create(blendingVsnTo);

            //RETRIEVE CREATED BLENDING
            String path = newBlendingUri.getPath();
            String idStr = path.substring(path.lastIndexOf('/') + 1);
            long newBlendingId = Integer.parseInt(idStr);
            blendingVsnTo = this.blendingRepository.getById(newBlendingId);

            //GET FUNCTION INTERFACE FROM FUNCTION MODULE
            InterfaceFnTo interfaceFnTo = this.functionRepository.getInterface(blendingRequest.getFunctionName());
            if(interfaceFnTo == null) {
                throw new NotFoundException(String.format("Failed to create Blending sensor: could not found %s function interface from Function module.", blendingRequest.getFunctionName()));
            }

            //CREATE A FUNCTION INTO VIRTUALSENSORNET BASED ON THE FUNCTION INTERFACE
            URI createdVsnFunctionUri = this.functionRepository.createOnVirtualSensorNet(interfaceFnTo);
            if(createdVsnFunctionUri == null) {
                throw new NotFoundException(String.format("Failed to create Blending sensor: could not found/create %s function interface from/on VirtualSensorNet module.", blendingRequest.getFunctionName()));
            }

            //GET CREATED FUNCTION FROM VIRTUALSENSORNET
            path = createdVsnFunctionUri.getPath();
            idStr = path.substring(path.lastIndexOf('/') + 1);
            long functionVsnId = Integer.parseInt(idStr);
            FunctionVsnTo functionVsnTo = this.functionRepository.getFromVirtualSensorNet(functionVsnId);

            //UPDATE BLENDING WITH FUNCTION DATA
            blendingVsnTo.setFunction(functionVsnTo);
            blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
            blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());

            //ADD REQUEST PARAMETERS
            List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
            for(VirtualSensorVsnTo virtualSensorVsnTo: virtualSensorVsnToList) {
                List<ValueVsnTo> valueVsnToList = virtualSensorVsnTo.getValuesTo();
                for(ValueVsnTo valueVsnTo : valueVsnToList) {
                    if(blendingRequest.getResponseParamName().equals(valueVsnTo.getName())){
                        blendingVsnTo.addRequestParam(valueVsnTo.getId(), valueVsnTo.getName());
                        break;
                    }
                }
            }
            if(blendingVsnTo.getRequestParams().isEmpty()) {
                throw new BadRequestException("Failed to create new Blending sensor: there are no fields associated with request parameters.");
            }

            //ADD RESPONSE PARAM (associated with the Blending first field)
            List<? extends FieldTo> fieldToList = blendingVsnTo.getFields();
            FieldTo fieldTo = fieldToList.get(0);
            blendingVsnTo.addResponseParam(fieldTo.getId(), fieldTo.getName());

            //UPDATE BLENDING WITH FUNCTION DATA
            this.blendingRepository.update(newBlendingId, blendingVsnTo);

            //RETURN NEW BLENDING URI
            return newBlendingUri;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Update an existing Blending sensor from VirtualSensorNet module
     * @param blendingId
     * @param blendingRequest
     * @throws AbstractRequestException
     */
    public void update(long blendingId, BlendingRequest blendingRequest) throws AbstractRequestException {
        BlendingVsnTo blendingVsnTo = BlendingMapper.requestToVsnTo(blendingRequest);
        this.blendingRepository.update(blendingId, blendingVsnTo);
    }

    /**
     * Removes an existing Blending sensor from VirtualSensorNet module
     * @param blendingId
     * @throws AbstractRequestException
     */
    public void delete(long blendingId) throws AbstractRequestException {
        this.blendingRepository.delete(blendingId);
    }

    /**
     * Get a random mock Blending sensor object
     * @see BlendingVsnTo
     * @return BlendingVsnTo
     * @throws AbstractRequestException
     */
    public BlendingVsnTo getRandom() throws AbstractRequestException {
        return this.blendingGenerator.generateBlendingVsnTo();
    }
}