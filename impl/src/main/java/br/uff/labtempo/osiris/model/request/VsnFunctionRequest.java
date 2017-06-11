package br.uff.labtempo.osiris.model.request;

import lombok.*;

/**
 * Model class to map VsnFunction request values
 * @see br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo
 * @author andre.ghigo
 * @since 10/07/2017
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VsnFunctionRequest {
    private String name;
}
