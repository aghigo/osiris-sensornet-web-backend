package br.uff.labtempo.osiris.model.request;

import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * FunctionModuleFactory data required to do HTTP requests on FunctionModuleFactory module
 * @see br.uff.labtempo.osiris.to.virtualsensornet.FunctionVsnTo
 * @see br.uff.labtempo.osiris.to.function.InterfaceFnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FunctionRequest {
    /**
     * Function operation name
     * example: sum, average, min, max
     */
    @NotNull @NotEmpty
    private String operationName;

    /**
     * Function module brief description
     * Example:
     * FunctionModuleFactory that calculates the average/sum/min/max of temperatures
     */
    @NotNull @NotEmpty
    private String description;

    /**
     * valid DataTypeId that represents the unit of the values to be calculated by the function
     * e.g.: temperature, pressure, etc.
     */
    private long dataTypeId;

    /**
     * Basic Java syntax code of the formula implementation
     * Basic operations: + - / %
     * Use java.lang.Math to perform more mathematical operations
     * @see Math
     * Iterate over values using for, while and if Java syntax
     *
     * Use the following built-in variables:
     * values: List of values (Double type each) passed as paremter of the request
     * total: the number of values of the list (int type)
     * result : handles the final result (double type)
     *
     * Caution:
     * You must initialize 'result' variable with a default (of your choice) value at the beginning
     * You must return 'result' varible at the end
     *
     * Example for the average function:
     *
     * result = 0;
     * for(Double value : values) {
     *     result = result + value;
     * }
     * result = result / total;
     * return result;
     *
     * Example for the min function:
     *
     * result = 0;
     * for(Double value : values) {
     *     result = Math.min(value, result);
     * }
     * return result;
     */
    @NotNull @NotEmpty
    private String implementation;
}
