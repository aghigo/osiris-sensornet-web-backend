package br.uff.labtempo.osiris.model.domain.sensor;

import br.uff.labtempo.osiris.to.common.definitions.LogicalOperator;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Class that represents a ComsumableRule model request
 * @see br.uff.labtempo.osiris.to.common.data.ConsumableRuleTo
 * @see br.uff.labtempo.osiris.to.common.data.ConsumableTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class SensorConsumableRule {

    @NotNull @NotEmpty
    private String name;

    @NotNull @NotEmpty
    private String consumableName;

    @NotNull
    private LogicalOperator operator;

    @Min(1)
    private int limitValue;

    @NotNull @NotEmpty
    private String message;
}
