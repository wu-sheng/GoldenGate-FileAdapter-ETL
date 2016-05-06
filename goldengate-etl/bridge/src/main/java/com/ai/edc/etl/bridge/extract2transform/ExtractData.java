package com.ai.edc.etl.bridge.extract2transform;

import java.util.ArrayList;
import java.util.List;

public class ExtractData {
	private String owner;
	
	private String db;
	
	private String tableName;
	
	private String datasid;
	
	private DML_TYPE opType;
	
	private List<ColumnData> columns = new ArrayList<ColumnData>();
	
	public ExtractData(String owner, String db, String tableName,
			String datasid, DML_TYPE opType) {
		super();
		this.owner = owner;
		this.db = db;
		this.tableName = tableName;
		this.datasid = datasid;
		this.opType = opType;
	}
	
	public void appendColumn(ColumnData column){
		this.columns.add(column);
	}

	public String getOwner() {
		return owner;
	}

	public String getDb() {
		return db;
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

	public static enum DML_TYPE{
		INSERT,UPDATE,DELETE
	}
	
	public static class ColumnData{
		private String name;
		
		private String oldValue;
		
		private String newValue;
		
		private VALUE_TYPE valueType;
		
		public ColumnData(String name, VALUE_TYPE valueType, String oldValue, String newValue) {
			super();
			this.name = name;
			this.valueType = valueType;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		public VALUE_TYPE getValueType() {
			return valueType;
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
		
		public static enum VALUE_TYPE{
			VARCHAR, TIMESTAMP
		}
	}
}
