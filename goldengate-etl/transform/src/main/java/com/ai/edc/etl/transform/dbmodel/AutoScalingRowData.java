package com.ai.edc.etl.transform.dbmodel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AutoScalingRowData {
	private String tableName;
	
	private String _id;

	private JsonObject _data;

	public AutoScalingRowData(String tableName, String _id) {
		this.tableName = tableName;
		this._id = _id;
		this._data = new JsonObject();
	}

	public AutoScalingRowData(String tableName, String _id, String _data) {
		this.tableName = tableName;
		this._id = _id;
		Gson gson = new Gson();
		this._data = gson.fromJson(_data, JsonObject.class);
	}

	public String getTableName() {
		return tableName;
	}

	public String get_id() {
		return _id;
	}

	public JsonObject get_data() {
		return _data;
	}

	public void setColumnValue(String columnName, String columnValue) {
		_data.addProperty(columnName, columnValue);
	}
	
	public String getColumnValue(String columnName){
		if(_data.has(columnName)){
			return _data.get(columnName).getAsString();
		}
		throw new AutoScalingRowDataExcetpion("column[" + columnName + "] don't have a value");
	}
}
