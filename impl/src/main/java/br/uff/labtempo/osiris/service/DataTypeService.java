package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.mapper.DataTypeMapper;
import br.uff.labtempo.osiris.generator.datatype.DataTypeGenerator;
import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.common.data.ValueTo;
import br.uff.labtempo.osiris.to.sensornet.SensorSnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public DataTypeResponse getById(long id) throws AbstractClientRuntimeException, AbstractRequestException {
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
        List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
        this.sortById(dataTypeVsnToList);
        List<DataTypeResponse> dataTypeResponseList = DataTypeMapper.vsnToToResponse(dataTypeVsnToList);
        return dataTypeResponseList;
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

    /**
     * Get a list of the best appropiate DataTypes for a SensorNet Sensor ValueTos
     * @param sensorSnTo
     * @return List<DataTypeVsnTo>
     * @throws AbstractRequestException
     * @throws AbstractClientRuntimeException
     */
    public List<DataTypeVsnTo> getAppropiateList(SensorSnTo sensorSnTo) throws AbstractRequestException, AbstractClientRuntimeException {
        List<DataTypeVsnTo> appropiateDataTypeList = new ArrayList<>();
        List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
        for(ValueTo valueTo : sensorSnTo.getValuesTo()) {
            for(DataTypeVsnTo dataTypeVsnTo : dataTypeVsnToList) {
                if(valueTo.getName().trim().toLowerCase().equals(dataTypeVsnTo.getDisplayName().trim().toLowerCase()) && valueTo.getSymbol().trim().toLowerCase().equals(dataTypeVsnTo.getSymbol().trim().toLowerCase())
                        && valueTo.getUnit().trim().toLowerCase().equals(dataTypeVsnTo.getUnit().trim().toLowerCase()) && valueTo.getType().equals(dataTypeVsnTo.getType()) ) {
                    appropiateDataTypeList.add(dataTypeVsnTo);
                    break;
                }
            }
        }
        return appropiateDataTypeList;
    }

    private void sortById(List<DataTypeVsnTo> unsortedList) {
        Comparator<DataTypeVsnTo> comparator = (o1, o2) -> {
            long id1 = o1.getId();
            long id2 = o2.getId();
            if(id1 < id2) {
                return -1;
            }
            if(id2 > id1) {
                return 1;
            }
            return 0;
        };
        Collections.sort(unsortedList, comparator);
    }
}
