package com.ai.edc.etl.transform.sync2mirror;

import java.sql.Connection;
import java.sql.SQLException;

import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.transform.dbmodel.DBModel;

public interface ISync {
	public void toDB(Connection mirrorConn, DBModel model, ExtractData data) throws SQLException;
}
