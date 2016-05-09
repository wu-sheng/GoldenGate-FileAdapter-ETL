package com.ai.edc.etl.transform.join;

import java.sql.Connection;
import java.sql.SQLException;

import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;

public interface IJoin {
	public AutoScalingRowData join(Connection statisticsConn, DBModel model, AutoScalingRowData data) throws SQLException;
}
