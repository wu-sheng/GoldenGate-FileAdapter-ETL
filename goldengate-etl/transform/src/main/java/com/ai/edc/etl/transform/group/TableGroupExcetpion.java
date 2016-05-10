package com.ai.edc.etl.transform.group;

import com.ai.edc.etl.transform.TransformException;

public class TableGroupExcetpion extends TransformException {
	private static final long serialVersionUID = 6066765223036687438L;

	public TableGroupExcetpion(String errmsg){
		super(errmsg);
	}
	
	public TableGroupExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
