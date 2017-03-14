package br.uff.labtempo.osiris.model.request;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NetworkRequest {
    private String id;
    private Map<String, String> info;
}
