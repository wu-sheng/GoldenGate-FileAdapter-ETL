package com.ai.edc.etl.transform.dbmodel;

import java.io.IOException;

import com.ai.edc.common.container.InstanceContainer;

import junit.framework.TestCase;

public class ModelFileLoaderTest extends TestCase {
	protected void setUp() {
		InstanceContainer.init("application-context.xml");
	}
	
	public void testInit() throws IOException, DBModelDefineExcetpion{
		ModelFileLoader.load();
	}
}
