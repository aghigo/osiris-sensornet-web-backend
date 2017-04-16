package br.uff.labtempo.osiris.model.request;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Class that represents a UserAccount request for creation/update purposes
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountRequest {
    @NotNull @NotEmpty
    private String firstName;

    @NotNull @NotEmpty
    private String middleName;

    @NotNull @NotEmpty
    private String lastName;
    @NotNull @NotEmpty @Email
    private String email;

    @NotNull @NotEmpty
    private String username;

    @NotNull @NotEmpty
    private String password;

    @NotNull
    private Date birthDate;
}
