package com.ai.edc.etl.transform.transformfunc;

import com.ai.edc.etl.transform.TransformException;

public class TransformFuncExcetpion extends TransformException {
	private static final long serialVersionUID = 1992770223874631569L;

	
	public TransformFuncExcetpion(String message) {
		super(message);
	}
	
	public TransformFuncExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
