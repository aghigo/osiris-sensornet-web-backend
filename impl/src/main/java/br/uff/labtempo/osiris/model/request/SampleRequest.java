package br.uff.labtempo.osiris.model.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Request that represents Sample data required to create(POST)/update(PUT) Samples on SensorNet module
 * @see br.uff.labtempo.osiris.to.collector.SampleCoTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SampleRequest {

    /**
     * @see br.uff.labtempo.osiris.to.collector.NetworkCoTo
     */
    @NotNull @Valid
    private NetworkRequest network;

    /**
     * @see br.uff.labtempo.osiris.to.collector.CollectorCoTo
     */
    @NotNull @Valid
    private CollectorRequest collector;

    /**
     * @see br.uff.labtempo.osiris.to.collector.SensorCoTo
     */
    @NotNull @Valid
    private SensorRequest sensor;
}
