package com.ai.edc.etl.transform.group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ai.edc.common.utils.StringUtil;
import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.transform.dbassist.AutoScalingRowDataProcessor;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;
import com.ai.edc.etl.transform.dbmodel.ModelFileLoader;
import com.ai.edc.etl.transform.join.TableJoinExcetpion;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@Component
public class GroupTable implements IGroup {
	private static Logger logger = LogManager.getLogger(GroupTable.class);

	public AutoScalingRowData group(Connection statisticsConn, DBModel model,
			ExtractData data, AutoScalingRowData autoScalingRowData,
			AutoScalingRowData afterTagAutoScalingRowData) throws SQLException {
		if (!GroupConfigScript.hasScript(model.getTableName())) {
			return afterTagAutoScalingRowData;
		}

		logger.debug("table " + model.getTableName() + " begin to group");

		DBModel targetModel = ModelFileLoader
				.findModel(afterTagAutoScalingRowData.getTableName());
		if (targetModel.isOriginTable()) {
			throw new TableGroupExcetpion("table[" + targetModel.getTableName()
					+ "] is origin table. can't be group.");
		}

		boolean isTagChanged = data.hasColumn("_TAG");
		if (!isTagChanged) {
			logger.debug("table " + model.getTableName()
					+ " skip group step. Because _TAG is not changed.");
			return afterTagAutoScalingRowData;
		}

		String tableName = model.getTableName();
		ArrayList<String> tagNames = GroupConfigScript
				.getSubscribeTag(tableName);
		ArrayList<String> dataColumnNames = GroupConfigScript
				.getSubscribeData(tableName);

		String[] paramValues = new String[tagNames.size()
				+ dataColumnNames.size()];
		int index = 0;
		Gson gson = new Gson();
		JsonArray oldTagValues = gson.fromJson(data.getColumn("_TAG")
				.getOldValue(), JsonArray.class);

		boolean isSubscribeTagValueChanged = false;
		for (String tagName : tagNames) {
			int tagIdx = model.findTagIndex(tagName.toUpperCase());
			String newTagValue = autoScalingRowData.getTag().get(tagIdx)
					.getAsString();
			String tagValue = newTagValue;
			if (oldTagValues.size() >= tagIdx) {
				String oldTagValue = oldTagValues.get(tagIdx).getAsString();
				if (!StringUtil.compareString(oldTagValue, newTagValue)) {
					isSubscribeTagValueChanged = true;
				}
				tagValue = oldTagValue + "->" + newTagValue;
			}
			paramValues[index++] = tagValue;
		}
		if (!isSubscribeTagValueChanged) {
			logger.debug("table "
					+ model.getTableName()
					+ " skip group step. Because subscribeTagValue of _TAG is not changed.");
			return afterTagAutoScalingRowData;
		}

		for (String dataColumnName : dataColumnNames) {
			String value = afterTagAutoScalingRowData
					.try2GetColumnValue(dataColumnName);
			if (value == null) {
				afterTagAutoScalingRowData.setColumnValue(dataColumnName, "0");
			}
			paramValues[index++] = afterTagAutoScalingRowData
					.getColumnValue(dataColumnName);
		}

		ArrayList<String> groupResults = GroupConfigScript.evalGroupFunc(
				tableName, paramValues);
		for (String groupResult : groupResults) {
			String[] groupAndResult = groupResult.split("=");
			if (groupAndResult.length == 2) {
				throw new TableJoinExcetpion("table " + model.getTableName()
						+ " group() return groupResult=[" + groupResult
						+ "] is illegal.");
			}
			String targetColumnName = groupAndResult[0];
			Long newNum = null;
			try {
				newNum = Long.parseLong(groupAndResult[1]);
			} catch (NumberFormatException e) {
				throw new TableGroupExcetpion("table[" + tableName
						+ "] group result=[" + groupResult + "] is not long");
			}
			String value = afterTagAutoScalingRowData
					.try2GetColumnValue(targetColumnName);
			if (value != null) {
				try {
					newNum += Long.parseLong(value);
				} catch (NumberFormatException e) {
					throw new TableGroupExcetpion("table[" + afterTagAutoScalingRowData.getTableName()
							+ "] column[" + targetColumnName + "] is not long. value=" + value);
				}
			}
			afterTagAutoScalingRowData
					.setColumnValue(targetColumnName, "" + newNum);

		}

		/**
		 * group data save automatic.
		 */
		AutoScalingRowDataProcessor.save(statisticsConn,
				afterTagAutoScalingRowData);

		return afterTagAutoScalingRowData;
	}
}
