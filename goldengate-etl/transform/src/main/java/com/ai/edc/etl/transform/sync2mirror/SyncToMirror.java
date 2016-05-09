package com.ai.edc.etl.transform.sync2mirror;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.bridge.extract2transform.ExtractData.ColumnData;
import com.ai.edc.etl.transform.dbassist.PreparedStatementSetterAssist;
import com.ai.edc.etl.transform.dbmodel.DBModel;
import com.ai.edc.etl.transform.dbmodel.DBModel.ColumnType;

@Component
public class SyncToMirror implements ISync {
	private static Logger logger = LogManager.getLogger(SyncToMirror.class);

	@Override
	public void toDB(Connection mirrorConn, DBModel model, ExtractData data)
			throws SQLException {
		if (!model.isOriginTable()) {
			throw new Sync2MirrorExcetpion("table[" + model.getTableName()
					+ "] is not origin table. Can't be mirror.");
		}
		switch (data.getOpType()) {
		case INSERT:
			this.insert(mirrorConn, model, data);
			break;
		case UPDATE:
			this.update(mirrorConn, model, data);
			break;
		case DELETE:
			this.delete(mirrorConn, model, data);
			break;
		default:
			throw new Sync2MirrorExcetpion("illegal row op type:"
					+ data.getOpType());
		}
	}

	public void insert(Connection mirrorConn, DBModel model, ExtractData data)
			throws SQLException {
		StringBuilder insertSQL = new StringBuilder("insert into "
				+ data.getTableName() + "_MIRROR(");
		boolean firstLoop = true;
		for (ColumnData column : data.getColumns()) {
			if (firstLoop) {
				firstLoop = false;
			} else {
				insertSQL.append(",");
			}
			insertSQL.append(column.getName());
		}
		insertSQL.append(") values( ");

		List<ColumnData> columns = data.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			if (i == 0) {
				firstLoop = false;
			} else {
				insertSQL.append(",");
			}
			insertSQL.append("?");
		}
		insertSQL.append(")");

		logger.debug("begin to execute:{}", insertSQL);
		try (PreparedStatement ps = mirrorConn.prepareStatement(insertSQL
				.toString())) {
			int index = 1;
			for (ColumnData column : data.getColumns()) {
				ColumnType columnType = model.getColumnType(column.getName());
				PreparedStatementSetterAssist.setParamByType(ps, index++,
						columnType, column.getNewValue());
			}
			ps.executeUpdate();
		}
	}

	public void update(Connection mirrorConn, DBModel model, ExtractData data)
			throws SQLException {
		StringBuilder updateSQL = new StringBuilder("update "
				+ data.getTableName() + "_MIRROR set ");

		List<String> pks = model.getPk();
		boolean firstLoop = true;
		for (ColumnData column : data.getColumns()) {
			if (firstLoop) {
				firstLoop = false;
			} else {
				updateSQL.append(",");
			}
			updateSQL.append(column.getName() + " = ?");
		}
		updateSQL.append(" where ");
		firstLoop = true;
		for (String pk : pks) {
			if (firstLoop) {
				firstLoop = false;
			} else {
				updateSQL.append(",");
			}
			updateSQL.append(pk + " = ?");
		}

		logger.debug("begin to execute:{}", updateSQL);
		try (PreparedStatement ps = mirrorConn.prepareStatement(updateSQL
				.toString())) {
			int index = 1;
			firstLoop = true;
			for (ColumnData column : data.getColumns()) {
				ColumnType columnType = model.getColumnType(column.getName());
				PreparedStatementSetterAssist.setParamByType(ps, index++,
						columnType, column.getNewValue());
			}

			for (String pk : pks) {
				ColumnType columnType = model.getColumnType(pk);
				PreparedStatementSetterAssist.setParamByType(ps, index++,
						columnType, data.getColumn(pk).getNewValue());
			}
			ps.executeUpdate();
		}
	}

	public void delete(Connection mirrorConn, DBModel model, ExtractData data)
			throws SQLException {
		StringBuilder deleteSQL = new StringBuilder("delete from "
				+ data.getTableName() + "_MIRROR where");

		boolean firstLoop = true;
		for (ColumnData column : data.getColumns()) {
			if (firstLoop) {
				firstLoop = false;
			} else {
				deleteSQL.append(",");
			}
			deleteSQL.append(column.getName() + " = ?");
		}

		logger.debug("begin to execute:{}", deleteSQL);
		try (PreparedStatement ps = mirrorConn.prepareStatement(deleteSQL
				.toString())) {
			int index = 1;
			firstLoop = true;
			for (ColumnData column : data.getColumns()) {
				ColumnType columnType = model.getColumnType(column.getName());
				PreparedStatementSetterAssist.setParamByType(ps, index++,
						columnType, column.getOldValue());
			}
			ps.executeUpdate();
		}
	}

}
