package br.uff.labtempo.osiris.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SampleRequest {
    private NetworkRequest network;
    private CollectorRequest collector;
    private SensorRequest sensor;
}
