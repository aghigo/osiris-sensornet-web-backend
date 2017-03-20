package br.uff.labtempo.osiris.model.domain.sensor;

import br.uff.labtempo.osiris.to.common.definitions.LogicalOperator;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;


@Data
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class SensorConsumableRule {
    private String name;
    private int currentValue;
    private String consumableRule;
    private LogicalOperator logicalOperator;
    private int limitValue;
    private String message;
}
