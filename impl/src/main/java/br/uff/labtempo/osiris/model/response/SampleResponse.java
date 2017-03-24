package br.uff.labtempo.osiris.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SampleResponse {
    private NetworkResponse network;
    private CollectorResponse collector;
    private SensorResponse sensor;
}
