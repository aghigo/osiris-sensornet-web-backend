package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class DataTypeRequest {
    @NotNull @NotEmpty
    private String name;

    @NotNull
    private ValueType type;

    @NotNull @NotEmpty
    private String unit;

    @NotNull @NotEmpty
    private String symbol;
}
