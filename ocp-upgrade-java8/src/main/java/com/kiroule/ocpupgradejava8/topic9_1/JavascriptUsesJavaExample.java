package com.kiroule.ocpupgradejava8.topic9_1;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Igor Baiborodine
 * @see characters-list.js
 */
public class JavascriptUsesJavaExample {

    public static void main(String... args)
            throws FileNotFoundException, ScriptException, NoSuchMethodException {

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

        engine.eval(new FileReader("ocp-upgrade-java8/src/main/java/com/kiroule/" +
                "ocpupgradejava8/topic9_1/characters-list.js"));
        Invocable invocable = (Invocable) engine;

        Frame frame	= (Frame) invocable.invokeFunction("createCharactersList");
        frame.setVisible(true);
    }
}
