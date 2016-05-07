package com.ai.edc.etl.transform;

public class TransformException extends Exception {
	private static final long serialVersionUID = 3019630795742120419L;

	public TransformException(String errmsg){
		super(errmsg);
	}
	
	public TransformException(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
