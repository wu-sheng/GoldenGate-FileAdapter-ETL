package com.ai.edc.etl.transform.dbassist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;

public class AutoScalingRowDataProcessor {
	private static Logger logger = LogManager
			.getLogger(AutoScalingRowDataProcessor.class);

	public static AutoScalingRowData load(Connection statisticsConn,
			String tableName, String _id) throws SQLException {
		try (PreparedStatement ps = statisticsConn
				.prepareStatement("select * from " + tableName + " where _id=?")) {
			logger.debug("select * from " + tableName + " where _id=?");
			ps.setString(1, _id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					AutoScalingRowData data = new AutoScalingRowData(tableName,
							_id, rs.getString("_data"), rs.getString("_tag"));
					return data;
				}
			}
		}
		AutoScalingRowData data = new AutoScalingRowData(tableName, _id, false);
		return data;
	}

	public static void save(Connection statisticsConn, AutoScalingRowData data)
			throws SQLException {
		if (data.isBeenInsert()) {
			try (PreparedStatement ps = statisticsConn
					.prepareStatement("update " + data.getTableName()
							+ " set _data=?, _tag=? where _id=?")) {
				logger.debug("update " + data.getTableName()
						+ " set _data=?, _tag=? where _id=?");
				ps.setString(1, data.getData().toString());
				ps.setString(2, data.getTag().toString());
				ps.setString(3, data.getId());

				ps.executeUpdate();
			}
		} else {
			try (PreparedStatement ps = statisticsConn
					.prepareStatement("insert into " + data.getTableName()
							+ "(_id, _data, _tag) values(?, ?, ?)")) {
				logger.debug("insert into " + data.getTableName()
						+ "(_id, _data, _tag) values(?, ?, ?)");
				ps.setString(1, data.getId());
				ps.setString(2, data.getData().toString());
				ps.setString(3, data.getTag().toString());

				ps.executeUpdate();
				data.insertComplete();
			}
		}
	}
}
