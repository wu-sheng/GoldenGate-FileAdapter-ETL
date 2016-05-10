package com.ai.edc.etl.transform.tag;

import com.ai.edc.etl.transform.TransformException;

public class TableTagExcetpion extends TransformException {
	private static final long serialVersionUID = -8928537536199109471L;

	public TableTagExcetpion(String errmsg){
		super(errmsg);
	}
	
	public TableTagExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
