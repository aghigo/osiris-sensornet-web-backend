package br.uff.labtempo.osiris.model.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Class that represents function data into Application domain
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
public class Function {
    @Min(1) @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
