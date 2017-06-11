package br.uff.labtempo.osiris.model.domain.function;

import br.uff.labtempo.osiris.factory.function.FunctionFactory;
import br.uff.labtempo.osiris.to.common.definitions.FunctionOperation;
import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * FunctionData module configuration representation on Osiris Web application
 * to manage FunctionData modules on OSIRIS.
 * @see FunctionFactory
 * @see br.uff.labtempo.osiris.to.function.InterfaceFnTo
 * @see br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo
 * @author andre.ghigo
 * @version 1.0.0
 * @since 10/07/2017
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "FunctionData")
@Table(name = "FUNCTION")
public class FunctionData {
    @Id @NotNull @NotEmpty
    private String name;

    @NotNull @NotEmpty
    private String fullName;

    @NotNull @NotEmpty
    private String moduleUri;

    @NotNull @NotEmpty
    private String description;

    @NotNull @NotEmpty
    private String implementation;

    @NotNull
    private FunctionOperation operation;

    @NotNull
    private ValueType type;

    @NotNull @NotEmpty
    private String unit;

    @NotNull @NotEmpty
    private String requestParamName;

    @NotNull @NotEmpty
    private String responseParamName;
}
