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

	private Map<String, String> tagDefineMap = new HashMap<String, String>();

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

	public Map<String, ColumnType> getColumns() {
		return columns;
	}

	public void setMirror(String mirror) {
		if ("Y".equals(mirror)) {
			this.mirror = true;
		}
	}

	public boolean isMirror() {
		return mirror;
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
		tagDefineMap.put(defineName, defineDesc);
	}

	public static enum ColumnType {
		STRING, DATE, NUMBER
	}
}
