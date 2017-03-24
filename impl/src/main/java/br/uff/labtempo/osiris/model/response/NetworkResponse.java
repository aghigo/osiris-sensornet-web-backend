package br.uff.labtempo.osiris.model.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class NetworkResponse {
    private String id;
    private Map<String, String> info;
}
