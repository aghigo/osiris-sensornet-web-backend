package br.uff.labtempo.osiris.model.domain.network;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Class that represents the Network model
 * @see br.uff.labtempo.osiris.to.collector.NetworkCoTo
 * @see br.uff.labtempo.osiris.to.sensornet.NetworkSnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Network {

    @NotNull @NotEmpty
    private String id;

    @NotNull @NotEmpty
    private Map<String, String> info;
}
