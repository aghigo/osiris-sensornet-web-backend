package br.uff.labtempo.osiris.model.domain.sensor;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
