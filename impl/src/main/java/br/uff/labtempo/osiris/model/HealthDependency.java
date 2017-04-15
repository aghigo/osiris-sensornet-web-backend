package br.uff.labtempo.osiris.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HealthDependency {
    private String name;
    private String ip;
    private int port;
    private String uri;
    private String status;
}
