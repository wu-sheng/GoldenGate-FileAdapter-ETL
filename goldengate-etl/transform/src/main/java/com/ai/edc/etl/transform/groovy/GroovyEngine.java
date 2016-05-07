package com.ai.edc.etl.transform.groovy;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GroovyEngine {

	private static ScriptEngine engine;

	static {
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("groovy");
	}

	public static void eval(String scriptName, String... scriptLines)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		try {
			GroovyScriptEngine gse = new GroovyScriptEngine(GroovyEngine.class
					.getResource("/event_subscription/join").getPath());
			Binding binding = new Binding();
			gse.run("AITOS_VOUCHER_ITEM_TICKET.join.groovy", binding);
			if (binding.hasVariable("transform")) {
				Object o = binding.getVariable("transform");
				System.out.println(o);
			}
		} catch (Exception e) {
			throw new GroovyScriptExecuteExcetpion(e.getMessage(), e);
		}
	}

	public static Object get(String script) {
		return null;
	}
}
