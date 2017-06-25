package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.definitions.State;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Response model class to serve as SensorNet dashboard data
 * @author andre.ghigo
 * @since 25/06/2017
 * @version 1.0.0
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SensorNetDashboardResponse {
    @Min(0)
    private long totalSensors;
    @Min(0)
    private long totalNetworks;
    @Min(0)
    private long totalCollectors;
    @NotNull @NotEmpty
    private Map<State, Long> totalSensorsByStateMap;
}
