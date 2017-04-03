package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.to.virtualsensornet.CompositeVsnTo;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositeRequest {
    @NotNull @NotEmpty
    private List<Long> fields;
}
