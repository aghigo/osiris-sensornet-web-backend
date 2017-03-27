package br.uff.labtempo.osiris.model.response;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectorResponse {
    @NotNull @NotEmpty
    private String id;

    @Min(1)
    private long captureInterval;

    @NotNull
    private TimeUnit captureIntervalTimeUnit;

    @NotNull @NotEmpty
    private Map<String, String> info;
}
