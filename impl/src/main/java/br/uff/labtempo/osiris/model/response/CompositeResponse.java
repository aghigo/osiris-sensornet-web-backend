package br.uff.labtempo.osiris.model.response;

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
    List<Long> fields;
}
