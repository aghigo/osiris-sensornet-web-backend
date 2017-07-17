package br.uff.labtempo.osiris.model.request;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Composite sensor data required to Create/Update Composite Sensors on VirtualSensorNet module
 * @see br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo
 * @see br.uff.labtempo.osiris.mapper.CompositeMapper
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositeRequest {
    /**
     * List of Field ids to be binded with CompositeVsnTo.bindToField method
     * A field contains its unique long id and related DataType id
     * @see br.uff.labtempo.osiris.to.common.data.FieldTo
     * @see br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo
     */
    @NotNull @NotEmpty
    private List<Long> fieldIds;
}
