package br.uff.labtempo.osiris.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Authorities to spring security jdbc authentication
 * @see br.uff.labtempo.osiris.security.WebSecurityConfig
 * @see br.uff.labtempo.osiris.model.domain.UserAccount
 * @author andre.ghigo
 * @version 1.0
 * @since 28/04/17.
 */
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Authorities")
@Table(name = "AUTHORITIES",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "role"})
})
public class Authorities {
    @Min(1)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;
}
