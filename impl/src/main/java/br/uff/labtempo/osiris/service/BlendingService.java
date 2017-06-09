package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.omcp.common.exceptions.InternalServerErrorException;
import br.uff.labtempo.omcp.common.exceptions.NotFoundException;
import br.uff.labtempo.osiris.generator.BlendingGenerator;
import br.uff.labtempo.osiris.mapper.BlendingMapper;
import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.model.response.BlendingResponse;
import br.uff.labtempo.osiris.repository.*;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.function.InterfaceFnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.*;
import br.uff.labtempo.osiris.util.OmcpUtil;
import lombok.AllArgsConstructor;
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
    private LinkRepository linkRepository;

    @Autowired
    private CompositeRepository compositeRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private VirtualSensorRepository virtualSensorRepository;

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
        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        blendingVsnTo.createField(blendingRequest.getResponseParamName(), blendingRequest.getResponseDataTypeId());
        URI createdBlendingUri = this.blendingRepository.create(blendingVsnTo);
        long createdBlendingId = OmcpUtil.getIdFromUri(createdBlendingUri);
        blendingVsnTo = this.blendingRepository.getById(createdBlendingId);
        InterfaceFnTo interfaceFnTo = this.functionRepository.getInterface(blendingRequest.getFunctionName());
        URI createdFunctionVsnToUri = this.functionRepository.createOnVirtualSensorNet(interfaceFnTo);
        long createdFunctionVsnToId = OmcpUtil.getIdFromUri(createdFunctionVsnToUri);
        FunctionVsnTo functionVsnTo = this.functionRepository.getFromVirtualSensorNet(createdFunctionVsnToId);
        blendingVsnTo.setFunction(functionVsnTo);
        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());
        blendingVsnTo.addResponseParam(blendingVsnTo.getFields().get(0).getId(), blendingRequest.getResponseParamName());

        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        for(LinkVsnTo linkVsnTo : linkVsnToList) {
            for(FieldTo fieldTo : linkVsnTo.getFields()) {
                if(fieldTo.getDataTypeId() == blendingRequest.getResponseDataTypeId()
                        && fieldTo.getName().equals(blendingRequest.getResponseParamName())) {
                    blendingVsnTo.addRequestParam(fieldTo.getId(), fieldTo.getName());
                    break;
                }
            }
        }
        List<CompositeVsnTo> compositeVsnToList = this.compositeRepository.getAll();
        for(CompositeVsnTo compositeVsnTo : compositeVsnToList) {
            for(FieldTo fieldTo : compositeVsnTo.getBoundFields()) {
                if(fieldTo.getDataTypeId() == blendingRequest.getResponseDataTypeId()
                        && fieldTo.getName().equals(blendingRequest.getResponseParamName())) {
                    blendingVsnTo.addRequestParam(fieldTo.getId(), fieldTo.getName());
                    break;
                }
            }
        }
        List<BlendingVsnTo> blendingVsnToList = this.blendingRepository.getAll();
        for(BlendingVsnTo blendingVsnTo1 : blendingVsnToList) {
            for(FieldTo fieldTo : blendingVsnTo1.getFields()) {
                if(fieldTo.getDataTypeId() == blendingRequest.getResponseDataTypeId()
                        && fieldTo.getName().equals(blendingRequest.getResponseParamName())) {
                    blendingVsnTo.addRequestParam(fieldTo.getId(), fieldTo.getName());
                    break;
                }
            }
        }
        if(blendingVsnTo.getRequestParams().isEmpty()) {
            throw new BadRequestException(String.format("Failed to create blending sensor: there are no virtual sensors with field of type [%s]", blendingRequest.getResponseParamName()));
        }

        this.blendingRepository.update(createdBlendingId, blendingVsnTo);
        return createdBlendingUri;

//        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
//        InterfaceFnTo interfaceFnTo = this.functionRepository.getInterface(blendingRequest.getFunctionName());
//        URI createdVsnFunctionUri = this.functionRepository.createOnVirtualSensorNet(interfaceFnTo);
//        long functionId = OmcpUtil.getIdFromUri(createdVsnFunctionUri);
//        blendingVsnTo.setFunction(functionId);
//        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());
//        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
//        blendingVsnTo.createField(blendingRequest.getResponseParamName(), blendingRequest.getResponseDataTypeId());
//        blendingVsnTo.addResponseParam(blendingVsnTo.getFields().get(0).getId(), blendingVsnTo.getFields().get(0).getName());

//        List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
//        for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnToList) {
//            for(ValueVsnTo valueVsnTo : virtualSensorVsnTo.getValuesTo()) {
//                if(valueVsnTo.getName().equals(blendingRequest.getResponseParamName())) {
//                    blendingVsnTo.addRequestParam(valueVsnTo.getId(), blendingRequest.getResponseParamName());
//                    break;
//                }
//            }
//        }
    }

    /**
     * Update an existing Blending sensor from VirtualSensorNet module
     * @param blendingId
     * @param blendingRequest
     * @throws AbstractRequestException
     */
    public void update(long blendingId, BlendingRequest blendingRequest) throws AbstractRequestException {
        BlendingVsnTo blendingVsnTo = this.blendingRepository.getById(blendingId);
//        blendingVsnTo.removeFields();
//        blendingVsnTo.setCallIntervalInMillis();
//        blendingVsnTo.setFunction();
//        blendingVsnTo.setLabel();
//        blendingVsnTo.createField();
//        blendingVsnTo.addRequestParam();
//        blendingVsnTo.addResponseParam();
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