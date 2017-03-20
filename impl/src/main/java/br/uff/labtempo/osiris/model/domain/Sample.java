package br.uff.labtempo.osiris.model.domain;

import br.uff.labtempo.osiris.model.request.collector.CollectorRequest;
import br.uff.labtempo.osiris.model.request.network.NetworkRequest;
import br.uff.labtempo.osiris.model.request.sensor.SensorRequest;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sample {
    private NetworkRequest network;
    private CollectorRequest collector;
    private SensorRequest sensor;
}
