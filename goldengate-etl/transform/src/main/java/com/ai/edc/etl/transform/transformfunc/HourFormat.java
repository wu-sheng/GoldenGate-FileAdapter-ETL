package com.ai.edc.etl.transform.transformfunc;

import org.springframework.stereotype.Component;

@Component("HourFormat")
public class HourFormat implements IFunc {

	@Override
	public String transform(String originValue) {
		return originValue;
	}

}
