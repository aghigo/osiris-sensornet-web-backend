package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.definitions.State;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectorResponse {
    @Min(1)
    private long lastModified;

    @NotNull @NotEmpty
    private String networkId;

    @Min(1)
    private long totalSensors;

    @Min(1)
    private long captureInterval;

    @NotNull
    private TimeUnit captureIntervaltimeUnit;

    @NotNull @NotEmpty
    private String id;

    @NotNull
    private State state;

    @NotNull @NotEmpty
    private Map<String, String> info;
}
