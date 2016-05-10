package com.ai.edc.etl.transform.tag;

import java.sql.Connection;
import java.sql.SQLException;

import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;

public interface ITag {
	public AutoScalingRowData tag(Connection statisticsConn, DBModel model, AutoScalingRowData data) throws SQLException;

}
