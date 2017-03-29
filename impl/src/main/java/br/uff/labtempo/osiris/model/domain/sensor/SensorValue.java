package br.uff.labtempo.osiris.model.domain.sensor;

import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class SensorValue {
    @NotNull @NotEmpty
    private String name;

    @NotNull
    private ValueType type;

    @Min(1)
    private long value;

    @NotNull @NotEmpty
    private String unit;

    @NotNull @NotEmpty
    private String symbol;
}
