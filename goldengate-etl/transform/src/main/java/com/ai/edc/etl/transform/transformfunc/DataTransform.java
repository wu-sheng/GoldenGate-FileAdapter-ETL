package com.ai.edc.etl.transform.transformfunc;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ai.edc.common.utils.StringUtil;
import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.bridge.extract2transform.ExtractData.ColumnData;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;
import com.ai.edc.etl.transform.dbmodel.ModelFileLoader;

@Component
public class DataTransform implements IDataTrans {
	@Override
	public AutoScalingRowData toTransform(DBModel model, ExtractData data)
			throws SQLException {
		AutoScalingRowData _ret;
		if (model.isOriginTable()) {
			String _id = "";
			for (String pk : model.getPk()) {
				String pkValue = data.getColumn(pk).getNewValue();
				if (StringUtil.isBlank(pkValue)) {
					pkValue = data.getColumn(pk).getOldValue();
				}
				_id += pkValue + ",";
			}
			_ret = new AutoScalingRowData(model.getTableName(), _id);

			for (ColumnData columnData : data.getColumns()) {
				_ret.setColumnValue(columnData.getName(),
						columnData.getNewValue());
			}
		} else {
			String _id = data.getColumn("_id").getNewValue();
			if (StringUtil.isBlank(_id)) {
				_id = data.getColumn("_id").getOldValue();
			}
			_ret = new AutoScalingRowData(_id, data.getColumn("_data")
					.getNewValue());
		}

		/**
		 * 运行transformfunc
		 */
		for (String columnName : model.getValueTransform().keySet()) {
			try {
				String newValue = model.getValueTransform()
						.get(columnName).transform(_ret.getColumnValue(columnName));
				_ret.setColumnValue(columnName, newValue);
			} catch (Throwable e) {
				throw new TransformFuncExcetpion("transformFunc for column ["
						+ columnName + "] execute failure", e);
			}
		}

		return _ret;
	}

}
