package br.uff.labtempo.osiris.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SampleRequest {
    @NotNull
    private NetworkRequest network;

    @NotNull
    private CollectorRequest collector;

    @NotNull
    private SensorRequest sensor;
}
