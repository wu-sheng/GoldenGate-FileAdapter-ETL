package com.ai.edc.etl.transform.dbmodel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBModel {
	private String tableName;
	
	public List<String> pk;
	
	public Map<String, ColumnType> columns = new HashMap<String, ColumnType>();

	public DBModel(String tableName) {
		super();
		this.tableName = tableName.toUpperCase().substring(0, tableName.length() - ".dmd".length());
	}
	
	public void setPK(String value){
		pk = Arrays.asList(value.toUpperCase().split(","));
	}
	
	public void addColumn(String columnName, String dmdValue){
		ColumnType columnType = ColumnType.valueOf(dmdValue.toUpperCase());
		columns.put(columnName, columnType);
	}

	public String getTableName() {
		return tableName;
	}

	public List<String> getPk() {
		return pk;
	}
	
	
	public static enum ColumnType{
		STRING, DATE, NUMBER
	}
}
