package com.ai.edc.etl.transform.sync2mirror;

import com.ai.edc.etl.bridge.extract2transform.ExtractData;

public interface ISync {
	public void toDB(ExtractData data);
}
