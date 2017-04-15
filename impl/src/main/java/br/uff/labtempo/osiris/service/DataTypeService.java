package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.mapper.DataTypeMapper;
import br.uff.labtempo.osiris.generator.datatype.DataTypeGenerator;
import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Service class with business rules go select/create/update/remove DataTypes
 * @see DataTypeVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Service
public class DataTypeService {

    private DataTypeRepository dataTypeRepository;

    private DataTypeGenerator dataTypeGenerator;

    @Autowired
    public DataTypeService(DataTypeRepository dataTypeRepository, DataTypeGenerator dataTypeGenerator) {
        this.dataTypeRepository = dataTypeRepository;
        this.dataTypeGenerator = dataTypeGenerator;
    }

    /**
     * Get a random DataTypeVsnTo mocked object
     * @see DataTypeVsnTo
     * @return DataTypeVsnTo
     */
    public DataTypeVsnTo getRandom() {
        return this.dataTypeGenerator.getDataTypeVsnTo();
    }

    /**
     * Get a specific DataType from VirtualSensorNet based on its unique Id
     * @param id
     * @return
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    public DataTypeResponse getById(String id) throws AbstractClientRuntimeException, AbstractRequestException {
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(id);
        return DataTypeMapper.vsnToToResponse(dataTypeVsnTo);
    }

    /**
     * Get a list of all available DataTypes from VirtualSensorNet
     * @return
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     */
    public List<DataTypeResponse> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        return DataTypeMapper.vsnToToResponse(this.dataTypeRepository.getAll());
    }

    /**
     * Create a new DataType on VirtualSensorNet module
     * @param dataTypeRequest
     * @return URI with the new DataType location
     * @throws AbstractClientRuntimeException
     * @throws AbstractRequestException
     * @throws URISyntaxException
     */
    public URI create(DataTypeRequest dataTypeRequest) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException {
        DataTypeVsnTo dataTypeVsnTo = DataTypeMapper.requestToVsnTo(dataTypeRequest);
        return this.dataTypeRepository.insert(dataTypeVsnTo);
    }

    /**
     * Update an existing DataType on VirtualSensorNet
     * @param id
     * @param dataTypeRequest
     * @throws AbstractRequestException
     */
    public void update(String id, DataTypeRequest dataTypeRequest) throws AbstractRequestException {
        DataTypeVsnTo dataTypeVsnTo = DataTypeMapper.requestToVsnTo(dataTypeRequest);
        this.dataTypeRepository.update(id, dataTypeVsnTo);
    }

    /**
     * Removes an existing DataType from VirtualSensorNet
     * @param id
     * @throws AbstractRequestException
     */
    public void delete(String id) throws AbstractRequestException {
        this.dataTypeRepository.delete(id);
    }
}
