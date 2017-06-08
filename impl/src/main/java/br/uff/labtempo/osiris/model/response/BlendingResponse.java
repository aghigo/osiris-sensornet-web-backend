package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.osiris.to.common.data.FieldTo;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.virtualsensornet.BlendingBondVsnTo;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BlendingResponse {
    private long id;
    private String label;
    private long functionId;
    private long callIntervalInMillis;
    private List<? extends FieldTo> fields;
    private List<BlendingBondVsnTo> requestParams;
    private List<BlendingBondVsnTo> responseParams;
}
