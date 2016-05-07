package com.ai.edc.etl.transform.transformfunc;

import org.springframework.stereotype.Component;

@Component("Zero2Y")
public class Zero2Y implements IFunc {

	@Override
	public String transform(String originValue) throws TransformFuncExcetpion{
		if("0".equals(originValue)){
			return "Y";
		}else if("1".equals(originValue)){
			return "Y";
		}
		throw new TransformFuncExcetpion("unexpected value:" + originValue);
	}

}
