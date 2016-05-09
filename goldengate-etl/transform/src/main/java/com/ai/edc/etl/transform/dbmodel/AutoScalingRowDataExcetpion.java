package com.ai.edc.etl.transform.dbmodel;

import com.ai.edc.etl.transform.TransformException;

public class AutoScalingRowDataExcetpion extends TransformException {
	private static final long serialVersionUID = -2106669058870655357L;

	public AutoScalingRowDataExcetpion(String message) {
		super(message);
	}
	
	public AutoScalingRowDataExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
