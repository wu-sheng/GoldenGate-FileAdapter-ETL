package com.ai.edc.etl.transform.tag;

import java.util.ArrayList;

import javax.script.ScriptException;

import com.ai.edc.etl.transform.groovy.GroovyEngine;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class TagConfigScript {
	@SuppressWarnings("unchecked")
	static ArrayList<String> getColumneName(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".TAG", "columneName");
	}
	
	static String getTagTable(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (String) GroovyEngine.eval(tableName + ".GROUP", "tagTable");
	}
	
	static String getTagTarget(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (String) GroovyEngine.eval(tableName + ".GROUP", "tagTarget");
	}
	
	static Long evalTagFunc(String tableName, String... funcParams)
			throws GroovyScriptNotFoundExcetpion, ScriptException,
			GroovyScriptExecuteExcetpion {
		String param = "";
		if (funcParams.length > 0) {
			StringBuilder paramBuilder = new StringBuilder();
			boolean first = true;
			for (String funcParam : funcParams) {
				if (first) {
					first = false;
				} else {
					paramBuilder.append(",");
				}
				paramBuilder.append("'" + funcParam + "'");
			}
			param = paramBuilder.toString();
		}
		String evalLine = "_ret_tag=businessCheck(" + param + ")";
		return (Long) GroovyEngine.eval(tableName + ".GROUP", "_ret_tag", evalLine);
	}
}
