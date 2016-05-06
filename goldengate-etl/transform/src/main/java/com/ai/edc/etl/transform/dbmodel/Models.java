package com.ai.edc.etl.transform.dbmodel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Models {
	private ConcurrentHashMap<String, DBModel> CONTAINER = new ConcurrentHashMap<String, DBModel>();

	public void addModel(String dmdFileName, InputStream is) throws IOException {
		Properties prop = new Properties();
		prop.load(is);

		DBModel model = new DBModel(dmdFileName);
		Enumeration<Object> keys = prop.keys();
		while (keys.hasMoreElements()) {
			String dmdKey = (String) keys.nextElement();
			String dmdValue = prop.getProperty(dmdKey);
			this.set(model, dmdKey, dmdValue);
		}
		CONTAINER.put(model.getTableName(), model);
	}

	private void set(DBModel model, String dmdKey, String dmdValue) {
		dmdKey = dmdKey.toUpperCase();
		if ("TABLE.PK".equals(dmdKey)) {
			model.setPK(dmdValue);
		}else if(dmdKey.startsWith("TABLE.COLUMN.") && dmdKey.endsWith(".TYPE")){
			String columnName = dmdKey.substring("TABLE.COLUMN.".length(), dmdKey.length() - ".TYPE".length());
			model.addColumn(columnName, dmdValue);
		}
	}
}
