package com.ai.edc.etl.transform.join;

import java.util.ArrayList;

import javax.script.ScriptException;

import com.ai.edc.etl.transform.groovy.GroovyEngine;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class JoinScript {
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getTransformDefine(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".JOIN", "transform");
	}
	
	public static String getPkMappingDefine(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (String) GroovyEngine.eval(tableName + ".JOIN", "pkmapping");
	}
}
