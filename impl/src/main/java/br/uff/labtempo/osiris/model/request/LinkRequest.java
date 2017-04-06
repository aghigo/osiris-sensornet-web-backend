package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.to.virtualsensornet.LinkVsnTo;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LinkRequest {
    @NotNull @NotEmpty
    private String sensorId;

    @NotNull @NotEmpty
    private String collectorId;

    @NotNull @NotEmpty
    private String networkId;

    @NotNull @NotEmpty
    private Map<String, Long> field;
}
