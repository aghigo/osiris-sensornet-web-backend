package br.uff.labtempo.osiris.model.response;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SampleResponse {
    @NotNull
    private NetworkResponse network;

    @NotNull
    private CollectorResponse collector;

    @NotNull
    private SensorResponse sensor;
}
