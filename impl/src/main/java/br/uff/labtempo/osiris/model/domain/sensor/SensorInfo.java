package br.uff.labtempo.osiris.model.domain.sensor;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Class that represents a Sensor Info model
 * a Info contains name and description
 * @see br.uff.labtempo.osiris.to.common.data.InfoTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SensorInfo {

    @NotNull @NotEmpty
    private String infoName;

    @NotNull @NotEmpty
    private String infoDescription;
}
