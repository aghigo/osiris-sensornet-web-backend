package br.uff.labtempo.osiris.generator.blending;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.domain.function.FunctionData;
import br.uff.labtempo.osiris.model.request.BlendingRequest;
import br.uff.labtempo.osiris.repository.DataTypeRepository;
import br.uff.labtempo.osiris.repository.FunctionDataRepository;
import br.uff.labtempo.osiris.schedule.sync.DataTypeFromSensorValueSyncSchedule;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created by osiris on 09/07/17.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BlendingRequestGenerator {

    private final long DEFAULT_CALL_INTERVAL_IN_MILLIS = 1000;

    @Autowired
    private DataTypeRepository dataTypeRepository;

    @Autowired
    private FunctionDataRepository functionDataRepository;

    public BlendingRequest generateBlendingRequest() throws AbstractRequestException {
        long callIntervalInMillis = this.generateCallIntervalInMillis();
        long dataTypeId = this.generateDataTypeId();
        String functionName = this.generateFunctionName();

        return BlendingRequest.builder()
                .callIntervalInMillis(callIntervalInMillis)
                .dataTypeId(dataTypeId)
                .functionName(functionName)
                .build();
    }

    private long generateCallIntervalInMillis() {
        return this.DEFAULT_CALL_INTERVAL_IN_MILLIS;
    }

    private long generateDataTypeId() throws AbstractRequestException {
        List<DataTypeVsnTo> dataTypeVsnToList = this.dataTypeRepository.getAll();
        return dataTypeVsnToList.get(0).getId();
    }

    private String generateFunctionName() {
        Iterable<FunctionData> functionDataIterable = this.functionDataRepository.findAll();
        Iterator<FunctionData> functionDataIterator = functionDataIterable.iterator();
        while(functionDataIterator.hasNext()) {
            FunctionData functionData = functionDataIterator.next();
            return functionData.getName();
        }
        return null;
    }
}
