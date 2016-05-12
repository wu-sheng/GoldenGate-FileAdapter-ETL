package com.ai.edc.etl.transform.tag;

import java.util.ArrayList;

import com.ai.edc.etl.transform.groovy.GroovyEngine;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptLoader;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class TagConfigScript {
	@SuppressWarnings("unchecked")
	static ArrayList<String> getColumneName(String tableName)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".TAG", "columneName");
	}
	
	static Boolean getSaveTagDefine(String tableName)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		return (Boolean) GroovyEngine.eval(tableName + ".TAG", "saveTag");
	}
	
	static boolean hasScript(String tableName){
		return GroovyScriptLoader.hasScript(tableName + ".TAG");
	}
	
	static String[] evalTagFunc(String tableName, String... funcParams)
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
				if(funcParam == null){
					funcParam = "";
				}
				paramBuilder.append("'" + funcParam + "'");
			}
			param = paramBuilder.toString();
		}
		String evalLine = "_ret_tag=businessCheck(" + param + ")";
		return (String[]) GroovyEngine.eval(tableName + ".TAG", "_ret_tag", evalLine);
	}
}
