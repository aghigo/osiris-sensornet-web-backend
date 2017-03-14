package br.uff.labtempo.osiris.model.domain.sensor;

import br.uff.labtempo.osiris.to.common.data.ConsumableRuleTo;
import br.uff.labtempo.osiris.to.common.definitions.LogicalOperator;
import lombok.*;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class SensorConsumableRule {
    private String name;
    private String consumableRule;
    private LogicalOperator logicalOperator;
    private int limitValue;
    private String message;
}
