package com.ai.edc.etl.transform.sync2mirror;

import com.ai.edc.etl.transform.TransformException;

public class Sync2MirrorExcetpion extends TransformException {
	private static final long serialVersionUID = 1627981920817800401L;

	public Sync2MirrorExcetpion(String errmsg){
		super(errmsg);
	}
	
	public Sync2MirrorExcetpion(String errmsg, Throwable t){
		super(errmsg, t);
	}
}
