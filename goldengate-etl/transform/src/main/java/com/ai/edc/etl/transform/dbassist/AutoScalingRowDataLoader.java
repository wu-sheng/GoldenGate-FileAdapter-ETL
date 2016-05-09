package com.ai.edc.etl.transform.dbassist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowDataExcetpion;

public class AutoScalingRowDataLoader {
	public static AutoScalingRowData load(Connection statisticsConn,
			String tableName, String _id) throws SQLException {
		try (PreparedStatement ps = statisticsConn
				.prepareStatement("select * from " + tableName + " where _id=?")) {
			ps.setString(1, _id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					AutoScalingRowData data = new AutoScalingRowData(tableName,
							_id, rs.getString("_data"));
					return data;
				}
			}
		}
		throw new AutoScalingRowDataExcetpion("can't find _id=" + _id
				+ " from " + tableName);
	}
}
