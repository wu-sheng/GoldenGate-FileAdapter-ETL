package com.ai.edc.etl.transform.groovy;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GroovyEngine {

	private static ScriptEngine engine;

	static {
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("groovy");
	}

	public static Object eval(String scriptName, String _retName,
			String... appendScriptLines) throws GroovyScriptNotFoundExcetpion,
			GroovyScriptExecuteExcetpion {
		Bindings binding = engine.createBindings();
		try {
			engine.eval(
					GroovyScriptLoader.getScript(scriptName, appendScriptLines),
					binding);
		} catch (ScriptException e) {
			throw new GroovyScriptExecuteExcetpion(e.getMessage(), e);
		}

		return binding.get(_retName);
	}

	public static Object get(String script) {
		return engine.get("transform");
	}
}
