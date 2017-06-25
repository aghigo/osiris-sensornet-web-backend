package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.definitions.State;
import br.uff.labtempo.osiris.to.virtualsensornet.VirtualSensorType;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Model class to serve as response for VirtualSensorNet dashboard data
 * @version 1.0.0
 * @since 25/06/17.
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualSensorNetDashboardResponse {
    @Min(0)
    private long totalDataTypes;
    @Min(0)
    private long totalVirtualSensors;
    @NotNull @NotEmpty
    Map<VirtualSensorType, Map<State, Long>> totalByTypeAndState;
}
