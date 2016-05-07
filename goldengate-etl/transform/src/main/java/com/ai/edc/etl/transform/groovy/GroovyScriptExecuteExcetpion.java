package com.ai.edc.etl.transform.groovy;

import com.ai.edc.etl.transform.TransformException;

public class GroovyScriptExecuteExcetpion extends TransformException {
	private static final long serialVersionUID = 4430920881248546649L;

	public GroovyScriptExecuteExcetpion(String message) {
		super(message);
	}
	
	public GroovyScriptExecuteExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
