package com.ai.edc.etl.transform.dbmodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Models {
	private ConcurrentHashMap<String, DBModel> CONTAINER = new ConcurrentHashMap<String, DBModel>();

	public DBModel findModel(String tableName) throws DBModelDefineExcetpion {
		if (CONTAINER.containsKey(tableName)) {
			return CONTAINER.get(tableName);
		} else{
			throw new DBModelDefineExcetpion("table=" + tableName + " is not define.");
		}
	}

	public void addModel(String dmdFileName, InputStream is)
			throws IOException, DBModelDefineExcetpion {
		Properties prop = new Properties();
		prop.load(new InputStreamReader(is, "UTF-8"));

		DBModel model = new DBModel(dmdFileName);
		Enumeration<Object> keys = prop.keys();
		while (keys.hasMoreElements()) {
			String dmdKey = (String) keys.nextElement();
			String dmdValue = prop.getProperty(dmdKey);
			this.set(model, dmdKey, dmdValue);
		}
		CONTAINER.put(model.getTableName(), model);
	}

	private void set(DBModel model, String dmdKey, String dmdValue)
			throws DBModelDefineExcetpion {
		dmdKey = dmdKey.toUpperCase();
		if ("TABLE.PK".equals(dmdKey)) {
			model.setPK(dmdValue);
		} else if (dmdKey.startsWith("TABLE.COLUMN.")) {
			if (dmdKey.endsWith(".TYPE")) {
				String columnName = dmdKey.substring("TABLE.COLUMN.".length(),
						dmdKey.length() - ".TYPE".length());
				model.addColumn(columnName, dmdValue);
			} else if (dmdKey.endsWith(".TRANSFORM")) {
				String columnName = dmdKey.substring("TABLE.COLUMN.".length(),
						dmdKey.length() - ".TRANSFORM".length());
				model.addColumnValueTransform(columnName, dmdValue);
			}
		} else if ("TABLE.MIRROR".equals(dmdKey)) {
			model.setMirror(dmdValue);
		} else if (dmdKey.startsWith("TABLE.TAG.")) {
			String tagDefine = dmdKey.substring("TABLE.TAG.".length());
			String[] tagDefineParts = tagDefine.split("\\.");
			if (tagDefineParts.length == 2) {
				model.addTagDefine(dmdKey, tagDefineParts[0],
						tagDefineParts[1], dmdValue);
			} else {
				throw new DBModelDefineExcetpion(
						"table.tag define illegal. table.tag.key=" + dmdKey);
			}
		}
	}
}
