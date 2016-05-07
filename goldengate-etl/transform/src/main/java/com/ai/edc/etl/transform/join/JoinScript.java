package com.ai.edc.etl.transform.join;

import javax.script.ScriptException;

import com.ai.edc.etl.transform.groovy.GroovyEngine;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class JoinScript {
	public static String[] getTransformDefine(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		GroovyEngine.eval(tableName + ".JOIN");
		return (String[]) GroovyEngine.get("transform");
	}
}
