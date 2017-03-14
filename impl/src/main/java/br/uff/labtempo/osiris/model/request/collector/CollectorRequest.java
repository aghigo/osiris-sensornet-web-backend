package br.uff.labtempo.osiris.model.request.collector;

import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CollectorRequest {
    private CollectorCoTo collectorCoToList;
}
