package br.uff.labtempo.osiris.model.request;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NetworkRequest {
    private String id;
    private Map<String, String> info;
}
