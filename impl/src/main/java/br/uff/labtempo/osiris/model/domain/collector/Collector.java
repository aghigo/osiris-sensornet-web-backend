package br.uff.labtempo.osiris.model.domain.collector;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class that represents a Collector
 * @see br.uff.labtempo.osiris.to.collector.CollectorCoTo
 * @see br.uff.labtempo.osiris.to.sensornet.CollectorSnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Collector {
    @NotNull @NotEmpty
    private String id;

    @Min(1)
    private long captureInterval;

    @NotNull
    private TimeUnit captureIntervalTimeUnit;

    @NotNull @NotEmpty
    private Map<String, String> info;
}
