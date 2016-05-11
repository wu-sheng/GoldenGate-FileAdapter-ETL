package com.ai.edc.etl.transform.dbmodel;

import com.ai.edc.common.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AutoScalingRowData {
	private String tableName;

	private String _id;

	private JsonObject _data;

	private JsonArray _tag;

	private boolean beenInserted = false;

	public AutoScalingRowData(String tableName, String _id, boolean beenInserted) {
		this.tableName = tableName;
		this._id = _id;
		this._data = new JsonObject();
		this._tag = new JsonArray();
		this.beenInserted = beenInserted;
	}

	public AutoScalingRowData(String tableName, String _id, String _data,
			String _tag) {
		this.tableName = tableName;
		this._id = _id;
		Gson gson = new Gson();
		this._data = gson.fromJson(_data, JsonObject.class);
		this.beenInserted = true;
		if (StringUtil.isBlank(_tag)) {
			this._tag = new JsonArray();
		} else {
			this._tag = gson.fromJson(_tag, JsonArray.class);
		}
	}

	public boolean isBeenInsert() {
		return beenInserted;
	}

	public String getTableName() {
		return tableName;
	}

	public String getId() {
		return _id;
	}

	public JsonObject getData() {
		return _data;
	}

	public JsonArray getTag() {
		return _tag;
	}
	
	public void insertComplete(){
		this.beenInserted = true;
	}

	public void setColumnValue(String columnName, String columnValue) {
		_data.addProperty(columnName, columnValue);
	}

	public String getColumnValue(String columnName) {
		if (_data.has(columnName)) {
			return _data.get(columnName).getAsString();
		}
		throw new AutoScalingRowDataExcetpion("column[" + columnName
				+ "] don't have a value");
	}
	
	public String try2GetColumnValue(String columnName) {
		if (_data.has(columnName)) {
			return _data.get(columnName).getAsString();
		}
		return null;
	}
}
