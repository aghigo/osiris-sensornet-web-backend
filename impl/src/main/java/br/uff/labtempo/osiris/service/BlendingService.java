package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.image.ImageWatched;

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
    private FunctionModuleRepository functionModuleRepository;

    @Autowired
    private VsnFunctionRepository vsnFunctionRepository;

    @Autowired
    private VirtualSensorRepository virtualSensorRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;

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
        return BlendingMapper.vsnToToResponse(blendingVsnToList);
    }

    /**
     * Get a specific Blending sensor from VirtualSensorNet by its unique Id
     * @param blendingId
     * @return BlendingResponse
     * @throws AbstractRequestException
     */
    public BlendingResponse getById(long blendingId) throws AbstractRequestException {
        BlendingVsnTo blendingVsnTo = this.blendingRepository.getById(blendingId);
        return BlendingMapper.vsnToToResponse(blendingVsnTo);
    }

    /**
     * Create a new Blending sensor on VirtualSensorNet module
     * @param blendingRequest
     * @return URI with the new Blending location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI create(BlendingRequest blendingRequest) throws URISyntaxException, AbstractRequestException {
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(blendingRequest.getDataTypeId());

        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(blendingRequest.getFunctionName());
        if(interfaceFnTo == null) {
            throw new IllegalArgumentException(String.format("Failed to create Blending sensor: no interface found for function with name '%s'.", blendingRequest.getFunctionName()));
        }

        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        blendingVsnTo.createField(interfaceFnTo.getResponseParams().get(0).getName(), blendingRequest.getDataTypeId());
        URI blendingUri = this.blendingRepository.create(blendingVsnTo);
        long blendingId = OmcpUtil.getIdFromUri(blendingUri);
        blendingVsnTo = this.blendingRepository.getById(blendingId);

        FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
        URI functionVsnuri = this.vsnFunctionRepository.create(functionVsnTo);
        long functionVsnId = OmcpUtil.getIdFromUri(functionVsnuri);
        functionVsnTo = this.vsnFunctionRepository.getById(functionVsnId);

        blendingVsnTo.setFunction(functionVsnTo);
        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());

        String functionRequestParameterName = interfaceFnTo.getRequestParams().get(0).getName();
        List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
        for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnToList) {
            for(ValueVsnTo valueVsnTo : virtualSensorVsnTo.getValuesTo()) {
                if(valueVsnTo.getName().equals(dataTypeVsnTo.getDisplayName())) {
                    blendingVsnTo.addRequestParam(valueVsnTo.getId(), interfaceFnTo.getRequestParams().get(0).getName());
                    break;
                }
            }
        }
        if(blendingVsnTo.getRequestParams().isEmpty()) {
            throw new BadRequestException(String.format("Could not create Blending sensor: no virtual sensor with field of type '%s' was found for the request parameters", dataTypeVsnTo.getDisplayName()));
        }

        List<? extends FieldTo> fields = blendingVsnTo.getFields();
        FieldTo fieldTo = fields.get(0);
        blendingVsnTo.addResponseParam(fieldTo.getId(), interfaceFnTo.getResponseParams().get(0).getName());

        this.blendingRepository.update(blendingVsnTo.getId(), blendingVsnTo);

        return blendingUri;
    }

    /**
     * Update an existing Blending sensor from VirtualSensorNet module
     * @param blendingId
     * @param blendingRequest
     * @throws AbstractRequestException
     */
    public void update(long blendingId, BlendingRequest blendingRequest) throws AbstractRequestException, URISyntaxException {
        BlendingVsnTo blendingVsnTo = this.blendingRepository.getById(blendingId);
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(blendingRequest.getDataTypeId());
        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(blendingRequest.getFunctionName());
        FunctionVsnTo functionVsnTo = this.vsnFunctionRepository.getById(blendingVsnTo.getFunctionId());

        this.vsnFunctionRepository.delete(functionVsnTo.getId());
        functionVsnTo = new FunctionVsnTo(interfaceFnTo);
        URI vsnFunctionUri = this.vsnFunctionRepository.create(functionVsnTo);
        long vsnFunctionId = OmcpUtil.getIdFromUri(vsnFunctionUri);
        functionVsnTo = this.vsnFunctionRepository.getById(vsnFunctionId);

        for(BlendingBondVsnTo blendingBondVsnTo : blendingVsnTo.getRequestParams()) {
            blendingVsnTo.removeRequestParam(blendingBondVsnTo.getFieldId());
        }
        for(BlendingBondVsnTo blendingBondVsnTo : blendingVsnTo.getResponseParams()) {
            blendingVsnTo.removeResponseParam(blendingBondVsnTo.getFieldId());
        }
        blendingVsnTo.removeFields();

        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());
        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
        blendingVsnTo.setFunction(functionVsnTo);

        blendingVsnTo.createField(interfaceFnTo.getResponseParams().get(0).getName(), dataTypeVsnTo.getId());
        blendingVsnTo.addResponseParam(blendingVsnTo.getFields().get(0).getId(), interfaceFnTo.getResponseParams().get(0).getName());

        List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
        for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnToList) {
            for(ValueVsnTo valueVsnTo : virtualSensorVsnTo.getValuesTo()) {
                if(valueVsnTo.getName().equals(dataTypeVsnTo.getDisplayName())) {
                    blendingVsnTo.addRequestParam(valueVsnTo.getId(), interfaceFnTo.getRequestParams().get(0).getName());
                    break;
                }
            }
        }

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