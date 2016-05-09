package com.ai.edc.etl.bridge.extract2transform;

import java.sql.SQLException;

public interface IGotoTransform {
	public void transform(ExtractData data) throws SQLException;
}
