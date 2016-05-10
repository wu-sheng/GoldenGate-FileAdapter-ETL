package com.ai.edc.etl.transform.tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ai.edc.etl.transform.dbassist.AutoScalingRowDataProcessor;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

@Component
public class TagTable implements ITag {
	private static Logger logger = LogManager
			.getLogger(TagTable.class);

	@Override
	public AutoScalingRowData tag(Connection statisticsConn, DBModel model,
			AutoScalingRowData data) throws SQLException {
		String tableName = model.getTableName();
		if (!TagConfigScript.hasScript(tableName)) {
			return data;
		}
		logger.debug("table " + model.getTableName() + " begin to tag");
		
		ArrayList<String> columnNames = TagConfigScript.getColumneName(tableName);
		String[] columnValues = new String[columnNames.size()];
		int index = 0;
		for(String columnName : columnNames){
			columnValues[index++] = data.getColumnValue(columnName.toUpperCase());
		}
		boolean status = TagConfigScript.evalTagFunc(tableName, columnValues);
		
		String tagName = TagConfigScript.getTagTarget(tableName);
		int idx = model.findTagIndex(tagName);
		JsonArray tagArray = data.getTag();
		while(idx <= tagArray.size()){
			tagArray.add("");
		}
		
		if(status){
			tagArray.set(index, new JsonPrimitive("Y"));
		}else{
			tagArray.set(index, new JsonPrimitive("N"));
		}
		
		boolean isSaveTag = TagConfigScript.getSaveTagDefine(tableName);
		if(isSaveTag){
			AutoScalingRowDataProcessor.save(statisticsConn, data);
		}
		
		return data;
	}

}
