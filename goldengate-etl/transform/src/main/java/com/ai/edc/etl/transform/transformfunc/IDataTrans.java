package com.ai.edc.etl.transform.transformfunc;

import java.sql.SQLException;

import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;

public interface IDataTrans {
	public AutoScalingRowData toTransform(DBModel model, ExtractData data) throws SQLException;
}
