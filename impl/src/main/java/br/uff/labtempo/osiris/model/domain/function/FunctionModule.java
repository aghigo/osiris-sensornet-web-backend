package br.uff.labtempo.osiris.model.domain.function;

import br.uff.labtempo.omcp.server.OmcpServer;
import lombok.*;

/**
 * Encapsulate function module data
 * @author andre.ghigo
 * @version 1.0
 * @since 11/06/17.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FunctionModule {
    private FunctionData functionData;
    private boolean running;
    private OmcpServer omcpServer;
}
