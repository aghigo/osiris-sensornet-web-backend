package br.uff.labtempo.osiris.model.domain.function;

import bsh.EvalError;
import bsh.Interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osiris on 10/06/17.
 */
public class TestBeanShell {
    public static void main(String[] args) {
        try {
            List<Double> values = new ArrayList<>();
            values.add(2.0);
            values.add(3.0);

            Interpreter interpreter = new Interpreter();
            interpreter.set("values", values);
            interpreter.set("total", values.size());
            String implementation = "" +
                    "result = 0;" +
                    "for(Double value : values) {" +
                    "   result = Math.pow(5,2);" +
                    "}" +
                    "result";
            double result = (Double) interpreter.eval(implementation);
            System.out.println("result = " + result);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }

    }
}
