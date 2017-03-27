package br.uff.labtempo.osiris.model.response;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NetworkResponse {
    @NotNull @NotEmpty
    private String id;

    @NotNull @NotEmpty
    private Map<String, String> info;
}
