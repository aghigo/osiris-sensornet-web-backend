package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.model.enums.FunctionEnum;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingVsnTo;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BlendingRequest {
    @NotNull @NotEmpty
    private String function;

    @NotNull @NotEmpty
    private Map<String, Long> fields;
}
