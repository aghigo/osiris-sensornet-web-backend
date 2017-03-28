package br.uff.labtempo.osiris.model.request;

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
    private NetworkRequest network;

    @NotNull @Valid
    private CollectorRequest collector;

    @NotNull @Valid
    private SensorRequest sensor;
}
