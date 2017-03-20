package br.uff.labtempo.osiris.model.response.collector;

import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CollectorResponse {
    private List<CollectorCoTo> collectors;
}
