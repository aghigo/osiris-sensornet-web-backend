package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.definitions.State;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NetworkResponse {
    @Min(1)
    private long lastModified;

    @Min(0)
    private long totalCollectors;

    @Min(0)
    private long totalSensors;

    @NotNull @NotEmpty
    private String id;

    @NotNull
    private State state;

    @NotNull @NotEmpty
    private Map<String, String> info;
}
