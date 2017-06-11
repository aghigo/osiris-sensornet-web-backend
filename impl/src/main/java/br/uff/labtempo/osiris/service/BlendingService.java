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
        InterfaceFnTo interfaceFnTo = this.functionModuleRepository.getInterface(blendingRequest.getFunctionName());
        if(interfaceFnTo == null) {
            throw new IllegalArgumentException(String.format("Failed to create Blending sensor: no interface found for function with name '%s'", blendingRequest.getFunctionName()));
        }
        URI createdFunctionVsnToUri = this.vsnFunctionRepository.create(new FunctionVsnTo(interfaceFnTo));
        long createdFunctionVsnToId = OmcpUtil.getIdFromUri(createdFunctionVsnToUri);
        FunctionVsnTo functionVsnTo = this.vsnFunctionRepository.getById(createdFunctionVsnToId);

        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        blendingVsnTo.setFunction(functionVsnTo.getId());
        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
        blendingVsnTo.setCallIntervalInMillis(blendingRequest.getCallIntervalInMillis());
        blendingVsnTo.createField(interfaceFnTo.getResponseParams().get(0).getName(), blendingRequest.getResponseDataTypeId());
        blendingVsnTo.addResponseParam(blendingVsnTo.getFields().get(0).getId(), blendingRequest.getResponseParamName());

        List<LinkVsnTo> linkVsnToList = this.linkRepository.getAll();
        for(LinkVsnTo linkVsnTo : linkVsnToList) {
            for(FieldTo fieldTo : linkVsnTo.getFields()) {
                if(fieldTo.getDataTypeId() == blendingRequest.getResponseDataTypeId()
                        && fieldTo.getName().equals(blendingRequest.getResponseParamName())) {
                    blendingVsnTo.createField(fieldTo.getName(), fieldTo.getDataTypeId());
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
                    blendingVsnTo.createField(fieldTo.getName(), fieldTo.getDataTypeId());
                    blendingVsnTo.addRequestParam(fieldTo.getId(), fieldTo.getName());
                    break;
                }
            }
        }
        List<BlendingVsnTo> blendingVsnToList = this.blendingRepository.getAll();
        for(BlendingVsnTo blendingSensor : blendingVsnToList) {
            for(FieldTo fieldTo : blendingSensor.getFields()) {
                if(fieldTo.getDataTypeId() == blendingRequest.getResponseDataTypeId()
                        && fieldTo.getName().equals(blendingRequest.getResponseParamName())) {
                    blendingVsnTo.createField(fieldTo.getName(), fieldTo.getDataTypeId());
                    blendingVsnTo.addRequestParam(fieldTo.getId(), fieldTo.getName());
                    break;
                }
            }
        }
        if(blendingVsnTo.getRequestParams().isEmpty()) {
            throw new BadRequestException(String.format("Failed to create blending sensor: there are no virtual sensors with field of type [%s]", blendingRequest.getResponseParamName()));
        }

        URI createdBlendingUri = this.blendingRepository.create(blendingVsnTo);
        return createdBlendingUri;
    }

    /**
     * Create a new Blending sensor on VirtualSensorNet module
     * @param blendingRequest
     * @return URI with the new Blending location
     * @throws URISyntaxException
     * @throws AbstractRequestException
     */
    public URI createWithSteps(BlendingRequest blendingRequest) throws URISyntaxException, AbstractRequestException {
        BlendingVsnTo blendingVsnTo = new BlendingVsnTo();
        blendingVsnTo.createField("temperature", 1);
        OmcpClient omcpClient = new RabbitClient("localhost", "guest", "guest");
        Response omcpResponse =  omcpClient.doPost("omcp://virtualsensornet.osiris/blending/", blendingVsnTo);
        if(omcpResponse.getStatusCode() == StatusCode.CREATED) {
            omcpResponse = omcpClient.doGet(omcpResponse.getLocation());
        }
        blendingVsnTo = omcpResponse.getContent(BlendingVsnTo.class);

        omcpResponse = omcpClient.doGet("omcp://sum.function/interface");
        InterfaceFnTo interfaceFnTo = null;
        if(omcpResponse.getStatusCode() == StatusCode.OK) {
            interfaceFnTo = omcpResponse.getContent(InterfaceFnTo.class);
        }

        FunctionVsnTo functionVsnTo = new FunctionVsnTo(interfaceFnTo);
        omcpResponse = omcpClient.doPost("omcp://virtualsensornet.osiris/function/", functionVsnTo);
        if(omcpResponse.getStatusCode() == StatusCode.CREATED) {
            omcpResponse = omcpClient.doGet(omcpResponse.getLocation());
            functionVsnTo = omcpResponse.getContent(FunctionVsnTo.class);
        }
        blendingVsnTo.setFunction(functionVsnTo);
        blendingVsnTo.setCallMode(FunctionOperation.SYNCHRONOUS);
        blendingVsnTo.setCallIntervalInMillis(6000);

        omcpResponse = omcpClient.doGet("omcp://virtualsensornet.osiris/vsensor/");
        if(omcpResponse.getStatusCode() == StatusCode.OK) {
            VirtualSensorVsnTo[] virtualSensorVsnTos = omcpResponse.getContent(VirtualSensorVsnTo[].class);
            for(VirtualSensorVsnTo virtualSensorVsnTo : virtualSensorVsnTos) {
                List<ValueVsnTo> valueVsnTos = virtualSensorVsnTo.getValuesTo();
                for(ValueVsnTo valueVsnTo : valueVsnTos) {
                    if(valueVsnTo.getName().equals("temperature")) {
                        blendingVsnTo.addRequestParam(valueVsnTo.getId(), "input");
                        break;
                    }
                }
            }
        }

        List<? extends FieldTo> fields = blendingVsnTo.getFields();
        FieldTo fieldTo = fields.get(0);
        blendingVsnTo.addResponseParam(fieldTo.getId(), "output");

        String address = "omcp://virtualsensornet.osiris/blending/" + blendingVsnTo.getId() + "/";
        omcpResponse = omcpClient.doPut(address, blendingVsnTo);
        if(omcpResponse.getStatusCode() == StatusCode.OK) {
            omcpResponse = omcpClient.doGet(address);
            blendingVsnTo = omcpResponse.getContent(BlendingVsnTo.class);
        }
        return new URI(address);
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