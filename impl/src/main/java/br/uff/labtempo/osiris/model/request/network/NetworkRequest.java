package br.uff.labtempo.osiris.model.request.network;

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

    public boolean isValid() {
        return this.id != null && !this.id.isEmpty()
                && this.info != null && !this.info.isEmpty();
    }
}
