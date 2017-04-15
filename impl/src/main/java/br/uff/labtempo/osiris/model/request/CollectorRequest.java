package br.uff.labtempo.osiris.model.request;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Collector data required to Create/Update Collectors on SensorNet module
 * @see br.uff.labtempo.osiris.to.collector.CollectorCoTo
 * @see br.uff.labtempo.osiris.to.sensornet.CollectorSnTo
 * @see br.uff.labtempo.osiris.mapper.CollectorMapper
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectorRequest {

    /**
     * Collector id
     */
    @NotEmpty @NotNull
    private String id;

    /**
     * Capture interval in milliseconds
     */
    @Min(1)
    private long captureInterval;

    /**
     * Capture Interval TimeUnit
     * @see TimeUnit
     */
    @NotNull
    private TimeUnit captureIntervalTimeUnit;

    /**
     * Info Map containing Name (key) and description (value)
     * @see br.uff.labtempo.osiris.to.common.data.InfoTo
     */
    @NotEmpty @NotNull
    private Map<String, String> info;
}
