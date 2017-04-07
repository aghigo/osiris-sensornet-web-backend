package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.mapper.DataTypeMapper;
import br.uff.labtempo.osiris.model.generator.datatype.DataTypeGenerator;
import br.uff.labtempo.osiris.model.request.DataTypeRequest;
import br.uff.labtempo.osiris.model.response.DataTypeResponse;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class DataTypeService {

    private DataTypeRepository dataTypeRepository;

    private DataTypeGenerator dataTypeGenerator;

    @Autowired
    public DataTypeService(DataTypeRepository dataTypeRepository, DataTypeGenerator dataTypeGenerator) {
        this.dataTypeRepository = dataTypeRepository;
        this.dataTypeGenerator = dataTypeGenerator;
    }

    public DataTypeVsnTo getRandom() {
        return this.dataTypeGenerator.getDataTypeVsnTo();
    }

    public DataTypeResponse getById(String id) throws AbstractClientRuntimeException, AbstractRequestException {
        DataTypeVsnTo dataTypeVsnTo = this.dataTypeRepository.getById(id);
        return DataTypeMapper.vsnToToResponse(dataTypeVsnTo);
    }

    public List<DataTypeResponse> getAll() throws AbstractClientRuntimeException, AbstractRequestException {
        return DataTypeMapper.vsnToToResponse(this.dataTypeRepository.getAll());
    }

    public URI create(DataTypeRequest dataTypeRequest) throws AbstractClientRuntimeException, AbstractRequestException, URISyntaxException {
        DataTypeVsnTo dataTypeVsnTo = DataTypeMapper.requestToVsnTo(dataTypeRequest);
        return this.dataTypeRepository.insert(dataTypeVsnTo);
    }

    public void update(String id, DataTypeRequest dataTypeRequest) {}

    public void delete(String id, DataTypeRequest dataTypeRequest) {}
}
