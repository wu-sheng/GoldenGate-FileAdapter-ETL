package com.ai.edc.etl.transform.group;

import java.util.ArrayList;

import javax.script.ScriptException;

import com.ai.edc.etl.transform.groovy.GroovyEngine;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class GroupConfigScript {
	@SuppressWarnings("unchecked")
	static ArrayList<String> getSubscribeTag(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".GROUP", "subscribeTag");
	}
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> getSubscribeData(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".GROUP", "subscribeData");
	}
	
	static String getGroupTarget(String tableName)
			throws GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion {
		return (String) GroovyEngine.eval(tableName + ".GROUP", "groupTarget");
	}
	
	static Long evalGroupFunc(String tableName, String... funcParams)
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
		String evalLine = "_ret_group=group(" + param + ")";
		return (Long) GroovyEngine.eval(tableName + ".GROUP", "_ret_group", evalLine);
	}
}
