package br.uff.labtempo.osiris.model.request;

import lombok.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectorRequest {
    private String id;
    private long captureInterval;
    private TimeUnit captureIntervalTimeUnit;
    private Map<String, String> info;
}
