package com.ai.edc.etl.transform.join;

import java.util.ArrayList;

import com.ai.edc.etl.transform.groovy.GroovyEngine;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptLoader;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class JoinConfigScript {
	@SuppressWarnings("unchecked")
	static ArrayList<String> getTransformDefine(String tableName)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		return (ArrayList<String>) GroovyEngine.eval(tableName + ".JOIN", "transform");
	}
	
	static String getColumn4FindTargetDefine(String tableName)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		return ((String) GroovyEngine.eval(tableName + ".JOIN", "column4FindTarget")).toUpperCase();
	}
	
	static Boolean getSaveJoinDefine(String tableName)
			throws GroovyScriptNotFoundExcetpion, GroovyScriptExecuteExcetpion {
		return ((Boolean) GroovyEngine.eval(tableName + ".JOIN", "saveJoin"));
	}
	
	static boolean hasScript(String tableName){
		return GroovyScriptLoader.hasScript(tableName + ".JOIN");
	}
}
