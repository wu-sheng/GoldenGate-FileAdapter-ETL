package com.ai.edc.etl.transform.group;

import java.util.ArrayList;

import com.ai.edc.etl.transform.groovy.GroovyEngine;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptLoader;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class GroupConfigScript {
	@SuppressWarnings("unchecked")
	static ArrayList<String> getSubscribeTag(String tableName)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".GROUP", "subscribeTag");
	}
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> getSubscribeData(String tableName)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".GROUP", "subscribeData");
	}
	
	static boolean hasScript(String tableName){
		return GroovyScriptLoader.hasScript(tableName + ".GROUP");
	}
	
	static String[] evalGroupFunc(String tableName, String... funcParams)
			throws GroovyScriptNotFoundExcetpion,
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
		return (String[]) GroovyEngine.eval(tableName + ".GROUP", "_ret_group", evalLine);
	}
}
