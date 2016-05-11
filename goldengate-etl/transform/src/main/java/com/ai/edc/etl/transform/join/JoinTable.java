package com.ai.edc.etl.transform.join;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ai.edc.common.utils.CollectionUtil;
import com.ai.edc.etl.transform.dbassist.AutoScalingRowDataProcessor;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;

@Component
public class JoinTable implements IJoin {
	private static Logger logger = LogManager.getLogger(JoinTable.class);

	public AutoScalingRowData join(Connection statisticsConn, DBModel model,
			AutoScalingRowData data) throws SQLException {
		if (!JoinConfigScript.hasScript(model.getTableName())) {
			return data;
		}
		logger.debug("table " + model.getTableName() + " begin to join");

		String pkColumnName = JoinConfigScript.getColumn4FindTargetDefine(model
				.getTableName());
		String finalTargetTableName = null;
		String[] pkTargets = pkColumnName.split("\\.");
		if (pkTargets.length == 2) {
			finalTargetTableName = pkTargets[0];
			pkColumnName = pkTargets[1];
		}
		String pk = data.getColumnValue(pkColumnName);

		ArrayList<String> transformRules = JoinConfigScript
				.getTransformDefine(model.getTableName());
		if (CollectionUtil.isEmpty(transformRules)) {
			logger.debug("table " + model.getTableName()
					+ " has no transform rule");
		}

		Map<String, String> columnNameMapping = new HashMap<String, String>();
		for (String transformRule : transformRules) {
			String[] rules = transformRule.split("->");
			if (rules.length != 2) {
				throw new TableJoinExcetpion(
						"table["
								+ model.getTableName()
								+ "] transform rule["
								+ transformRule
								+ "] is illegal. usage: column->targetTable.targetColumn");
			}
			String[] targets = rules[1].split("\\.");
			String targetTableName = targets[0];
			String targetColumnName = targets[1];
			if (targets.length == 1) {
				if (finalTargetTableName == null) {
					throw new TableJoinExcetpion(
							"table["
									+ model.getTableName()
									+ "] transform rule["
									+ transformRule
									+ "] is illegal. target only use targetColumn, only if column4FindTarget contains targetTable");
				}else{
					targetTableName = finalTargetTableName;
					targetColumnName = targets[0];
				}
			} else if (targets.length != 2) {
				throw new TableJoinExcetpion(
						"table["
								+ model.getTableName()
								+ "] transform rule["
								+ transformRule
								+ "] is illegal. target usage: targetTable.targetColumn");
			} else {
				targetTableName = targets[0];
				targetColumnName = targets[1];
			}
			String sourceColumnName = rules[0];

			if (finalTargetTableName == null) {
				finalTargetTableName = targetTableName;
			} else if (!finalTargetTableName.equals(targetTableName)) {
				throw new TableJoinExcetpion(
						"table["
								+ model.getTableName()
								+ "] transform rule["
								+ transformRule
								+ "] is illegal. targetTable is multi-declear. prevTargetTable or tablename of column4FindTarget is"
								+ finalTargetTableName);
			}

			columnNameMapping.put(sourceColumnName, targetColumnName);
		}

		AutoScalingRowData targetData = AutoScalingRowDataProcessor.load(
				statisticsConn, finalTargetTableName, pk);

		/**
		 * join columnValue to target
		 */
		for (String sourceColumnName : columnNameMapping.keySet()) {
			String sourceColumnNameUpper = sourceColumnName.toUpperCase();
			String targetColumnName = columnNameMapping.get(sourceColumnName)
					.toUpperCase();
			targetData.setColumnValue(targetColumnName,
					data.getColumnValue(sourceColumnNameUpper));
		}

		if (JoinConfigScript.getSaveJoinDefine(model.getTableName())) {
			AutoScalingRowDataProcessor.save(statisticsConn, targetData);
		}

		return targetData;
	}
}
