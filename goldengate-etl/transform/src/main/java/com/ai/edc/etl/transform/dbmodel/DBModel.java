package com.ai.edc.etl.transform.dbmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.edc.common.container.InstanceContainer;
import com.ai.edc.etl.transform.transformfunc.IFunc;

public class DBModel {
	private String tableName;

	private List<String> pk;

	private Map<String, ColumnType> columns = new HashMap<String, ColumnType>();

	private Map<String, IFunc> valueTransform = new HashMap<String, IFunc>();

	private boolean mirror = false;

	private List<String> tagDefineList = new ArrayList<String>();

	private Map<String, Integer> tagDefineMap = new HashMap<String, Integer>();
	
	private boolean isOriginTable = true;

	public DBModel(String tableName) {
		super();
		this.tableName = tableName.toUpperCase().substring(0,
				tableName.length() - ".dmd".length());
	}

	public void setPK(String value) {
		pk = Arrays.asList(value.toUpperCase().split(","));
	}

	public void addColumn(String columnName, String dmdValue) {
		ColumnType columnType = ColumnType.valueOf(dmdValue.toUpperCase());
		columns.put(columnName, columnType);
	}

	public String getTableName() {
		return tableName;
	}

	public List<String> getPk() {
		return pk;
	}

	public void addColumnValueTransform(String columnName,
			String transformFuncName) {
		IFunc func = InstanceContainer.getBean(transformFuncName, IFunc.class);
		valueTransform.put(columnName, func);
	}

	public ColumnType getColumnType(String columnName) {
		if(columns.containsKey(columnName)){
			return columns.get(columnName);
		}else{
			throw new DBModelDefineExcetpion("columnName= " + columnName + " .type is not define.");
		}
	}

	public void setMirror(String mirror) {
		if ("Y".equals(mirror)) {
			this.mirror = true;
		}
	}

	public boolean isMirror() {
		return mirror;
	}

	public boolean isOriginTable() {
		return isOriginTable;
	}

	public void setOrigin(String isOrigin) {
		if ("N".equals(isOrigin)) {
			this.isOriginTable = false;
		}
	}

	public void addTagDefine(String dmdKey, String index, String defineName,
			String defineDesc) throws DBModelDefineExcetpion {
		int idx = 0;
		try {
			idx = Integer.parseInt(index);
		} catch (NumberFormatException e) {
			throw new DBModelDefineExcetpion(
					"table.tag define should be table.tag.index.name. index is not a Integer. table.tag.key="
							+ dmdKey);
		}
		
		while(tagDefineList.size() <= idx){
			tagDefineList.add("");
		}
		
		tagDefineList.set(idx, defineName);
		tagDefineMap.put(defineName, idx);
	}
	
	public int findTagIndex(String tagName){
		if(!tagDefineMap.containsKey(tagName)){
			throw new DBModelDefineExcetpion("tag=" + tagName + " is not define.");
		}
		return tagDefineMap.get(tagName);
	}

	public Map<String, IFunc> getValueTransform() {
		return valueTransform;
	}

	public static enum ColumnType {
		STRING, DATE, LONG, INTEGER, NUMBER
	}
}
