package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.data.FieldTo;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LinkResponse {
    @Min(0)
    private long id;

    @NotNull @NotEmpty
    private String label;

    @NotNull @NotEmpty
    private String sensorId;

    @NotNull @NotEmpty
    private String collectorId;

    @NotNull @NotEmpty
    private String networkId;

    @NotNull @NotEmpty
    private List<FieldTo> fields;
}
