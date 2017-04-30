package br.uff.labtempo.osiris.model.domain;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Class that represents the application User Accounts
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "UserAccount")
@Table(name = "USER_ACCOUNT")
public class UserAccount {
    @Min(1) @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    private boolean enabled;

    @NotNull @NotEmpty
    private String role;
}