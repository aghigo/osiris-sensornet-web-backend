package br.uff.labtempo.osiris.model.domain;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Network {
    private String id;
    private Map<String, String> info;
}
