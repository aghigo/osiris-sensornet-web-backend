package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class DataTypeResponse {
    @Min(0)
    private long id;

    @NotNull @NotEmpty
    private String name;

    @NotNull
    private ValueType type;

    @NotNull @NotEmpty
    private String unit;

    @NotNull @NotEmpty
    private String symbol;

    @Min(0)
    private long usedBy;
}
