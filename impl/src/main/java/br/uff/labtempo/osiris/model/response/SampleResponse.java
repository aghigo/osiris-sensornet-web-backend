package br.uff.labtempo.osiris.model.response;

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
public class SampleResponse {
    @NotNull @Valid
    private NetworkResponse network;

    @NotNull @Valid
    private CollectorResponse collector;

    @NotNull @Valid
    private SensorResponse sensor;
}
