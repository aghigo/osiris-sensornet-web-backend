package br.uff.labtempo.osiris.model.response;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    private Map<String, String> field;
}
