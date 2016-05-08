package com.ai.edc.etl.transform.group;

import java.io.IOException;

import javax.script.ScriptException;

import com.ai.edc.common.container.InstanceContainer;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptLoader;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;
import com.ai.edc.etl.transform.join.JoinConfigScript;

import junit.framework.TestCase;

public class GroupConfigScriptTest extends TestCase {
	protected void setUp() throws IOException {
		InstanceContainer.init("application-context.xml");
		
		GroovyScriptLoader.load();
	}
	
	public void testGetTransformDefine() throws IOException, GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion{
		System.out.println(GroupConfigScript.evalGroupFunc("AITOS_VOUCHER_ITEM_TICKET_JOIN", "1", "b"));
	}
}
