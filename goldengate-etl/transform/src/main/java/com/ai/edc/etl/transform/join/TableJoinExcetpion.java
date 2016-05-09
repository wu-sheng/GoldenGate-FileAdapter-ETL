package com.ai.edc.etl.transform.join;

import com.ai.edc.etl.transform.TransformException;

public class TableJoinExcetpion extends TransformException {
	private static final long serialVersionUID = -8928537536199109471L;

	public TableJoinExcetpion(String errmsg){
		super(errmsg);
	}
	
	public TableJoinExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
