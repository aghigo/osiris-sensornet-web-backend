package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SampleRequest {
    @NotNull @Valid
    private NetworkCoTo network;

    @NotNull @Valid
    private CollectorCoTo collector;

    @NotNull @Valid
    private SensorCoTo sensor;
}
