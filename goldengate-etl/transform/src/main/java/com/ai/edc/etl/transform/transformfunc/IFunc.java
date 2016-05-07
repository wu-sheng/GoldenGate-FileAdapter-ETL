package com.ai.edc.etl.transform.transformfunc;

public interface IFunc {
	public String transform(String originValue) throws TransformFuncExcetpion;
}
