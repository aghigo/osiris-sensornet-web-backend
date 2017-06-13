package br.uff.labtempo.osiris.model.domain.function;

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
    private String operationName;

    @NotNull @NotEmpty
    private String omcpInterfaceUri;

    @NotNull @NotEmpty
    private String restInterfaceUri;

    @NotNull @NotEmpty
    private String dataUri;

    @NotNull @NotEmpty
    private String omcpUri;

    @NotNull @NotEmpty
    private String description;

    @NotNull @NotEmpty
    private String implementation;

    @NotNull
    private ValueType paramType;

    @NotNull
    private FunctionOperation operation;

    @Min(0)
    private long dataTypeId;

    @NotNull @NotEmpty
    private String dataTypeUnit;

    @NotNull @NotEmpty
    private String dataTypeName;
}
