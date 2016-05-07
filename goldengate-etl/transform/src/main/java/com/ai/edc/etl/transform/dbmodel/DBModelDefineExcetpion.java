package com.ai.edc.etl.transform.dbmodel;

import com.ai.edc.etl.transform.TransformException;

public class DBModelDefineExcetpion extends TransformException {
	private static final long serialVersionUID = 4054339525835747340L;

	public DBModelDefineExcetpion(String message) {
		super(message);
	}
	
	public DBModelDefineExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
