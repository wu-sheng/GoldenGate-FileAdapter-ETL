package com.ai.edc.etl.transform.groovy;

import java.io.IOException;

import junit.framework.TestCase;

import com.ai.edc.common.container.InstanceContainer;

public class GroovyScriptLoaderTest extends TestCase {
	protected void setUp() {
		InstanceContainer.init("application-context.xml");
	}
	
	public void testInit() throws IOException, GroovyScriptNotFoundExcetpion{
		GroovyScriptLoader.load();
		System.out.println(GroovyScriptLoader.getScript("AITOS_VOUCHER_ITEM_TICKET.JOIN"));
	}
}
