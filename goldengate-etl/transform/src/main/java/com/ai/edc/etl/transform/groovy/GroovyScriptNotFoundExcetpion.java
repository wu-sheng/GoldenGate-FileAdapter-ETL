package com.ai.edc.etl.transform.groovy;

import com.ai.edc.etl.transform.TransformException;

public class GroovyScriptNotFoundExcetpion extends TransformException {
	private static final long serialVersionUID = 4430920881248546649L;

	public GroovyScriptNotFoundExcetpion(String message) {
		super(message);
	}
	
	public GroovyScriptNotFoundExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
