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
import com.ai.edc.etl.transform.join.TableJoinExcetpion;
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
		String[] tagResults = TagConfigScript.evalTagFunc(tableName, columnValues);
		
		for(String tagResult : tagResults){
			String[] tagAndResult = tagResult.split("=");
			if(tagAndResult.length != 2){
				throw new TableJoinExcetpion("table " + model.getTableName() + " businessCheck() return tagResult:" + tagResult + " is illegal.");
			}
			String tagName = tagAndResult[0].toUpperCase();
			int tagIdx = model.findTagIndex(tagName);
			JsonArray tagArray = data.getTag();
			while(tagIdx >= tagArray.size()){
				tagArray.add("");
			}
			String tagValue = tagAndResult[1];
			boolean status = Boolean.parseBoolean(tagValue);
			
			if(status){
				tagArray.set(tagIdx, new JsonPrimitive("Y"));
			}else{
				tagArray.set(tagIdx, new JsonPrimitive("N"));
			}
			logger.debug("table " + model.getTableName() + " set tag." + tagIdx + " = " + tagArray.get(tagIdx).getAsString());
			
		}
		
		boolean isSaveTag = TagConfigScript.getSaveTagDefine(tableName);
		if(isSaveTag){
			AutoScalingRowDataProcessor.save(statisticsConn, data);
		}
		
		return data;
	}

}
