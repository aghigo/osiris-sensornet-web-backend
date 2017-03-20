package br.uff.labtempo.osiris.model.domain.sensor;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SensorInfo {
    private String infoName;
    private String infoDescription;
}
