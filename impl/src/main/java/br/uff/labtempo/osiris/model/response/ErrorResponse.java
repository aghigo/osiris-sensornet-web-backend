package br.uff.labtempo.osiris.model.response;

import br.uff.labtempo.omcp.common.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Model class that represent generic
 * well formated Error data for the application
 */
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
    private StatusCode statusCode;
}
