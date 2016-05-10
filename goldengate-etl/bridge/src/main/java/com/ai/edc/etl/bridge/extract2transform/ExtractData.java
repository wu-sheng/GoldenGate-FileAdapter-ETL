package com.ai.edc.etl.bridge.extract2transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractData {
	private String tableName;
	
	private String datasid;
	
	private DML_TYPE opType;
	
	private List<ColumnData> columns = new ArrayList<ColumnData>();
	
	public Map<String, ColumnData> columnsMap = new HashMap<String, ExtractData.ColumnData>();
	
	public ExtractData(String tableName,
			String datasid, DML_TYPE opType) {
		super();
		this.tableName = tableName;
		this.datasid = datasid;
		this.opType = opType;
	}
	
	public void appendColumn(ColumnData column){
		this.columns.add(column);
		this.columnsMap.put(column.getName(), column);
	}

	public String getTableName() {
		return tableName;
	}

	public String getDatasid() {
		return datasid;
	}

	public DML_TYPE getOpType() {
		return opType;
	}

	public List<ColumnData> getColumns() {
		return columns;
	}
	
	public boolean hasColumn(String columnName){
		return columnsMap.containsKey(columnName);
	}
	
	public ColumnData getColumn(String columnName){
		if(!columnsMap.containsKey(columnName)){
			throw new RuntimeException("tableName=" + tableName + " don't define column=" + columnName);
		}
		return columnsMap.get(columnName);
	}

	public static enum DML_TYPE{
		INSERT,UPDATE,DELETE
	}
	
	public static class ColumnData{
		private String name;
		
		private String oldValue;
		
		private String newValue;
		
		public ColumnData(String name, String oldValue, String newValue) {
			super();
			this.name = name;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		public String getName() {
			return name;
		}

		public String getOldValue() {
			return oldValue;
		}

		public String getNewValue() {
			return newValue;
		}
	}
}
