package br.uff.labtempo.osiris.model.request;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Network data required to create/update Networks on SensorNet module
 * @see br.uff.labtempo.osiris.to.sensornet.NetworkSnTo
 * @see br.uff.labtempo.osiris.to.collector.NetworkCoTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NetworkRequest {
    @NotEmpty @NotNull
    private String id;

    /**
     * Infos Map containing Name (key) and description (value)
     * @see br.uff.labtempo.osiris.to.common.data.InfoTo
     */
    @NotNull @NotEmpty
    private Map<String, String> info;
}
