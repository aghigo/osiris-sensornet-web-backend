package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.client.rabbitmq.RabbitClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.BadRequestException;
import br.uff.labtempo.omcp.common.exceptions.InternalServerErrorException;
import br.uff.labtempo.osiris.generator.BlendingGenerator;
import br.uff.labtempo.osiris.mapper.BlendingMapper;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
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
import java.util.ArrayList;
import java.util.Iterator;
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

    @Autowired
    private FunctionDataRepository functionDataRepository;

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
     * based on a datatype, one function module and all virtual sensors field of the correspondent dataType
     * @param blendingRequest
     * @return URI with the new Blending location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI create(BlendingRequest blendingRequest) throws Exception {
        List<FunctionData> functionDataList = this.functionDataRepository.findByName(blendingRequest.getFunctionName());
        if(functionDataList.isEmpty()) {
            throw new BadRequestException(String.format("Could not create Blending sensor: Function module with name '%s' doest no exist.", blendingRequest.getFunctionName()));
        }

        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(blendingRequest.getDataTypeId());
        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(blendingRequest.getFunctionName());

        blendingVsnTo.createField(interfaceFnTo.getResponseParams().get(0).getName(), blendingRequest.getDataTypeId());
        URI blendingUri = this.blendingRepository.create(blendingVsnTo);
        long blendingId = OmcpUtil.getIdFromUri(blendingUri);

        try {
            blendingVsnTo = this.blendingRepository.getById(blendingId);

            FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
            URI functionVsnuri = this.vsnFunctionRepository.create(functionVsnTo);
            long functionVsnId = OmcpUtil.getIdFromUri(functionVsnuri);
            functionVsnTo = this.vsnFunctionRepository.getById(functionVsnId);

            blendingVsnTo.setFunction(functionVsnTo);
            blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
            blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());

            List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
            for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnToList) {
                for(ValueVsnTo valueVsnTo : virtualSensorVsnTo.getValuesTo()) {
                    if(valueVsnTo.getName().equals(dataTypeVsnTo.getDisplayName()) && valueVsnTo.getUnit().equals(interfaceFnTo.getRequestParams().get(0).getUnit())) {
                        blendingVsnTo.addRequestParam(valueVsnTo.getId(), interfaceFnTo.getRequestParams().get(0).getName());
                        break;
                    }
                }
            }
            if(blendingVsnTo.getRequestParams().isEmpty()) {
                throw new BadRequestException(String.format("Could not create Blending sensor: no virtual sensor with field of type '%s' and unit '%s' was found for the request parameters", dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getUnit()));
            }

            List<? extends FieldTo> fields = blendingVsnTo.getFields();
            FieldTo fieldTo = fields.get(0);
            blendingVsnTo.addResponseParam(fieldTo.getId(), interfaceFnTo.getResponseParams().get(0).getName());

            this.blendingRepository.update(blendingVsnTo.getId(), blendingVsnTo);

            return blendingUri;
        } catch (Exception exc) {
            try {
                this.blendingRepository.delete(blendingId);
                this.vsnFunctionRepository.delete(blendingVsnTo.getFunctionId());
                throw new InternalServerErrorException("Failed to create blending sensor. rollback performed.");
            } catch (Exception e) {
                   throw new InternalServerErrorException("Failed to create blending sensor. Failed to perform rollback.");
            }
        }
    }

    /**
     * Creates a new Blending sensor on VirtualSensorNet module
     * based on an existing and running function module,
     * one datatype and all composite sensors with fields of the
     * corresponding datatype id.
     *
     * @param blendingRequest
     * @return
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI createByComposite(BlendingRequest blendingRequest) throws Exception {
        List<FunctionData> functionDataList = this.functionDataRepository.findByName(blendingRequest.getFunctionName());
        if(functionDataList.isEmpty()) {
            throw new BadRequestException(String.format("Could not create Blending sensor: Function module with name '%s' doest no exist.", blendingRequest.getFunctionName()));
        }

        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(blendingRequest.getDataTypeId());
        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(blendingRequest.getFunctionName());

        blendingVsnTo.createField(interfaceFnTo.getResponseParams().get(0).getName(), blendingRequest.getDataTypeId());
        URI blendingUri = this.blendingRepository.create(blendingVsnTo);
        long blendingId = OmcpUtil.getIdFromUri(blendingUri);

        try {
            blendingVsnTo = this.blendingRepository.getById(blendingId);

            FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
            URI functionVsnuri = this.vsnFunctionRepository.create(functionVsnTo);
            long functionVsnId = OmcpUtil.getIdFromUri(functionVsnuri);
            functionVsnTo = this.vsnFunctionRepository.getById(functionVsnId);

            blendingVsnTo.setFunction(functionVsnTo);
            blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
            blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());

            List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
            for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnToList) {
                if(virtualSensorVsnTo.getSensorType().equals(VirtualSensorType.COMPOSITE)) {
                    for(ValueVsnTo valueVsnTo : virtualSensorVsnTo.getValuesTo()) {
                        if(valueVsnTo.getName().equals(dataTypeVsnTo.getDisplayName()) && valueVsnTo.getUnit().equals(interfaceFnTo.getRequestParams().get(0).getUnit())) {
                            blendingVsnTo.addRequestParam(valueVsnTo.getId(), interfaceFnTo.getRequestParams().get(0).getName());
                            break;
                        }
                    }
                }
            }
            if(blendingVsnTo.getRequestParams().isEmpty()) {
                throw new BadRequestException(String.format("Could not create Blending sensor: no virtual sensor with field of type '%s' and unit '%s' was found for the request parameters", dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getUnit()));
            }

            List<? extends FieldTo> fields = blendingVsnTo.getFields();
            FieldTo fieldTo = fields.get(0);
            blendingVsnTo.addResponseParam(fieldTo.getId(), interfaceFnTo.getResponseParams().get(0).getName());

            this.blendingRepository.update(blendingVsnTo.getId(), blendingVsnTo);

            return blendingUri;
        } catch (Exception exc) {
            try {
                this.blendingRepository.delete(blendingId);
                this.vsnFunctionRepository.delete(blendingVsnTo.getFunctionId());
                throw new InternalServerErrorException("Failed to create blending sensor. rollback performed.");
            } catch (Exception e) {
                throw new InternalServerErrorException("Failed to create blending sensor. Failed to perform rollback.");
            }
        }
    }

    /**
     * Update an existing Blending sensor from VirtualSensorNet module
     * @param blendingId
     * @param blendingRequest
     * @throws AbstractRequestException
     */
    public void update(long blendingId, BlendingRequest blendingRequest) throws Exception {
        List<FunctionData> functionDataList = this.functionDataRepository.findByName(blendingRequest.getFunctionName());
        if(functionDataList.isEmpty()) {
            throw new BadRequestException(String.format("Could not create Blending sensor: Function module with name '%s' doest no exist.", blendingRequest.getFunctionName()));
        }

        BlendingVsnTo blendingVsnTo = this.blendingRepository.getById(blendingId);
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(blendingRequest.getDataTypeId());
        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(blendingRequest.getFunctionName());

        for(BlendingBondVsnTo blendingBondVsnTo : blendingVsnTo.getRequestParams()) {
            blendingVsnTo.removeRequestParam(blendingBondVsnTo.getFieldId());
        }
        for(BlendingBondVsnTo blendingBondVsnTo : blendingVsnTo.getResponseParams()) {
            blendingVsnTo.removeResponseParam(blendingBondVsnTo.getFieldId());
        }
        blendingVsnTo.removeFields();

        List<VirtualSensorVsnTo> virtualSensorVsnToList = this.virtualSensorRepository.getAll();
        for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnToList) {
            for(ValueVsnTo valueVsnTo : virtualSensorVsnTo.getValuesTo()) {
                if(valueVsnTo.getName().equals(dataTypeVsnTo.getDisplayName()) && valueVsnTo.getUnit().equals(dataTypeVsnTo.getUnit())) {
                    blendingVsnTo.addRequestParam(valueVsnTo.getId(), interfaceFnTo.getRequestParams().get(0).getName());
                    break;
                }
            }
        }
        if(blendingVsnTo.getRequestParams().isEmpty()) {
            throw new BadRequestException(String.format("Could not update Blending sensor with id '%s': no virtual sensor with field of type '%s' and unit '%s' was found for the request parameters", blendingId, dataTypeVsnTo.getDisplayName(), dataTypeVsnTo.getUnit()));
        }

        blendingVsnTo.createField(interfaceFnTo.getResponseParams().get(0).getName(), dataTypeVsnTo.getId());
        blendingVsnTo.addResponseParam(blendingVsnTo.getFields().get(0).getId(), interfaceFnTo.getResponseParams().get(0).getName());

        FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
        URI vsnFunctionUri = this.vsnFunctionRepository.create(functionVsnTo);
        long vsnFunctionId = OmcpUtil.getIdFromUri(vsnFunctionUri);
        functionVsnTo = this.vsnFunctionRepository.getById(vsnFunctionId);

        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());
        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
        blendingVsnTo.setFunction(functionVsnTo);

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