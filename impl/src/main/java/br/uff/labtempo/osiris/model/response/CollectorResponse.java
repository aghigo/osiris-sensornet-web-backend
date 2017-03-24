package br.uff.labtempo.osiris.model.response;

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
public class CollectorResponse {
    private String id;
    private long captureInterval;
    private TimeUnit captureIntervalTimeUnit;
    private Map<String, String> info;
}
