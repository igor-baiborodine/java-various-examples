package com.kiroule.ocpupgradejava8.topic9_2;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_DATE;

/**
 * @author Igor Baiborodine
 * @see quebec-gst-tax-calculator.js
 */
public class JavaEvaluatesJavascriptExample {

    public static void main(String... args)
            throws FileNotFoundException, ScriptException, NoSuchMethodException {

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

        engine.eval(new FileReader("ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic9_2/quebec-gst-tax-calculator.js"));
        Invocable invocable = (Invocable) engine;

        BigDecimal purchaseAmount = new BigDecimal("100.00");
        String format = "Quebec GST tax is $%s for the amount of $%s on %s %n";

        LocalDate purchaseDate = LocalDate.of(2005, 1, 1);
        BigDecimal taxAmount = new BigDecimal((String) invocable.invokeFunction("calculateGstTax",
                purchaseDate.format(ISO_DATE), purchaseAmount));
        System.out.printf(format, taxAmount, purchaseAmount, purchaseDate);

        purchaseDate = LocalDate.now();
        taxAmount = new BigDecimal((String) invocable.invokeFunction("calculateGstTax",
                purchaseDate.format(ISO_DATE), purchaseAmount));
        System.out.printf(format, taxAmount, purchaseAmount, purchaseDate);
    }
}
