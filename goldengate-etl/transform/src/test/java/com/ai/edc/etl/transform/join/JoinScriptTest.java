package com.ai.edc.etl.transform.join;

import java.io.IOException;

import javax.script.ScriptException;

import junit.framework.TestCase;

import com.ai.edc.common.container.InstanceContainer;
import com.ai.edc.etl.transform.groovy.GroovyScriptExecuteExcetpion;
import com.ai.edc.etl.transform.groovy.GroovyScriptLoader;
import com.ai.edc.etl.transform.groovy.GroovyScriptNotFoundExcetpion;

public class JoinScriptTest extends TestCase {
	protected void setUp() throws IOException {
		InstanceContainer.init("application-context.xml");
		
		GroovyScriptLoader.load();
	}
	
	public void testGetTransformDefine() throws IOException, GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion{
		System.out.println(JoinScript.getTransformDefine("AITOS_VOUCHER_ITEM_TICKET"));
	}
	
	public void testPkMappingDefine() throws IOException, GroovyScriptNotFoundExcetpion, ScriptException, GroovyScriptExecuteExcetpion{
		System.out.println(JoinScript.getPkMappingDefine("AITOS_VOUCHER_ITEM_TICKET"));
	}
}
