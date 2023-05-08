package lv.nixx.poc.sandbox.core;

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExecuteStatementUsingJsEngineSample {

    private static final ScriptEngine graalEngine = new ScriptEngineManager().getEngineByName("graal.js");

    @Test
    void executeStatementSample() throws ScriptException {
        graalEngine.eval("print('Hello World!');");

        String expr = "1 < 2";
        boolean eval = (boolean) graalEngine.eval(expr);
        System.out.println(expr + ": result:" + eval);

        System.out.println("true & true -> " + graalEngine.eval("true && true"));
        System.out.println("true | false ->" + graalEngine.eval("true || false"));

        int eval1 = (int) graalEngine.eval("12 + 18");
        System.out.println("12 + 18 ->" + eval1);

        BigDecimal eval2 = BigDecimal.valueOf((double) graalEngine.eval("12.01 + 19.25")).setScale(3, RoundingMode.HALF_UP);
        System.out.println("12.01 + 19.25 ->" + eval2);
    }

    @Test
    void complexExpressionSample() throws ScriptException {

        String ex1 = createExpression(false, true, "&&");
        System.out.println(ex1 + " -> " + graalEngine.eval(ex1));

        String ex2 = createExpression(true, true, "&&");
        System.out.println(ex2 + " -> " + graalEngine.eval(ex2));

        String ex3 = createExpression(true, false, "||");
        System.out.println(ex3 + " -> " + graalEngine.eval(ex3));
    }

    private String createExpression(boolean paramOne, boolean paramTwo, String operator) {
        return methodOneResult(paramOne) + operator + methodTwoResult(paramTwo);
    }

    private boolean methodOneResult(boolean req) {
        return req;
    }

    private boolean methodTwoResult(boolean reg) {
        return reg;
    }

}
