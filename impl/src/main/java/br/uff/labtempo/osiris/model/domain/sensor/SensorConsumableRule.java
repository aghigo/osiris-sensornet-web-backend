package br.uff.labtempo.osiris.model.domain.sensor;

import br.uff.labtempo.osiris.to.common.definitions.LogicalOperator;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class SensorConsumableRule {
    private String name;
    private String consumableName;
    private LogicalOperator operator;
    private int limitValue;
    private String message;
}
