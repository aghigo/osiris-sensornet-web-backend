package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.data.FieldTo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositeResponse {
    private long id;
    private String label;
    private List<FieldTo> fields;
}