package br.uff.labtempo.osiris.generator;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.InternalServerErrorException;
import br.uff.labtempo.osiris.repository.FunctionRepository;
import br.uff.labtempo.osiris.repository.LinkRepository;
import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class BlendingGenerator {

    private final int CALL_INTERVAL_INMILLIS_MAX_RANGE = 99999;
    private FunctionOperation[] functionOperationArray = FunctionOperation.values();

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private LinkRepository linkRepository;

    public BlendingVsnTo generateBlendingVsnTo() throws AbstractRequestException {
        long id = getId();
        String label = getLabel(id);
        BlendingVsnTo blendingVsnTo = new BlendingVsnTo(id, label);
        blendingVsnTo.setCallIntervalInMillis(getCallIntervalInMillis());
        FunctionVsnTo functionVsnTo = getFunction();
        blendingVsnTo.setFunction(functionVsnTo);
        blendingVsnTo.setCallMode(getFunctionOperation());
        List<FieldTo> fieldToList = getFieldToList();
        for(FieldTo fieldTo : fieldToList) {
            blendingVsnTo.addRequestParam(fieldTo.getId(), fieldTo.getName());
        }
        blendingVsnTo.addResponseParam(fieldToList.get(0).getId(), functionVsnTo.getName());
        return blendingVsnTo;
    }

    private long getId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    private String getLabel(long id) {
        return "blendingId-" + id;
    }

    private FunctionVsnTo getFunction() throws AbstractRequestException {
        List<FunctionVsnTo> functionVsnToList = this.functionRepository.getAll();
        if(functionVsnToList == null || functionVsnToList.isEmpty()) {
            throw new InternalServerErrorException("Failed to mock Blending Sensor: could not find any function.");
        }
        int p = (int) (Math.random() * functionVsnToList.size());
        return functionVsnToList.get(p);
    }

    private FunctionOperation getFunctionOperation() {
        int p = (int) (Math.random() * this.functionOperationArray.length);
        return this.functionOperationArray[p];
    }

    private long getCallIntervalInMillis() {
        return (long) (Math.random() * CALL_INTERVAL_INMILLIS_MAX_RANGE);
    }

    private List<FieldTo> getFieldToList() throws AbstractRequestException {
        List<FieldTo> fieldToList = this.linkRepository.getAllFields();
        if(fieldToList == null || fieldToList.isEmpty()) {
            throw new InternalServerErrorException("Failed to mock Blending Sensor: could not find any Field for Request Params.");
        }
        int min = (int) (Math.random() * fieldToList.size());
        int max = min + (int) (Math.random() * (fieldToList.size() - min));
        return fieldToList.subList(min, max);
    }
}
