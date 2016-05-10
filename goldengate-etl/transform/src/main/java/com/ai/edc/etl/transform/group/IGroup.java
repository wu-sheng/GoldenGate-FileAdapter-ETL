package com.ai.edc.etl.transform.group;

import java.sql.Connection;
import java.sql.SQLException;

import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;

public interface IGroup {
	public AutoScalingRowData group(Connection statisticsConn, DBModel model,
			ExtractData data, AutoScalingRowData autoScalingRowData,
			AutoScalingRowData afterTagAutoScalingRowData) throws SQLException;
}
