package com.ai.edc.etl.transform.dbassist;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ai.edc.etl.transform.dbmodel.DBModel.ColumnType;

public class PreparedStatementSetterAssist {
	public static void setParamByType(PreparedStatement ps, int index,
			ColumnType columnType, String strValue) throws SQLException {
		switch (columnType) {
		case STRING:
			ps.setString(index++, strValue);
			break;
		case NUMBER:
			ps.setString(index++, strValue);
			break;
		case DATE:
			ps.setTimestamp(index++, Timestamp.valueOf(strValue));
			break;
		case LONG:
			ps.setLong(index++, Long.parseLong(strValue));
			break;
		case INTEGER:
			ps.setInt(index++, Integer.parseInt(strValue));
			break;
		default:
			break;
		}
	}
}
