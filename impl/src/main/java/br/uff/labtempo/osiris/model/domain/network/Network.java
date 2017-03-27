package br.uff.labtempo.osiris.model.domain.network;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Map;

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
