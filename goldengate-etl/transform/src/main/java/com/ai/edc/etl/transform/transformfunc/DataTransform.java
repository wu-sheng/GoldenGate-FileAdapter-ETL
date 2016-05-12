package com.ai.edc.etl.transform.transformfunc;

import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.ai.edc.common.utils.StringUtil;
import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.bridge.extract2transform.ExtractData.ColumnData;
import com.ai.edc.etl.bridge.extract2transform.ExtractData.DML_TYPE;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;

@Component
public class DataTransform implements IDataTrans {
	@Override
	public AutoScalingRowData toTransform(DBModel model, ExtractData data)
			throws SQLException {
		AutoScalingRowData _ret;
		if (model.isOriginTable()) {
			String _id = "";
			boolean first = true;
			for (String pk : model.getPk()) {
				String pkValue = data.getColumn(pk).getNewValue();
				if (StringUtil.isBlank(pkValue)) {
					pkValue = data.getColumn(pk).getOldValue();
				}
				if (first) {
					first = false;
				} else {
					_id += ",";
				}
				_id += pkValue;
			}
			_ret = new AutoScalingRowData(model.getTableName(), _id, false);

			for (String pk : model.getPk()) {
				String pkValue = data.getColumn(pk).getNewValue();
				if (StringUtil.isBlank(pkValue)) {
					pkValue = data.getColumn(pk).getOldValue();
				}
				_ret.setColumnValue(pk, pkValue);
			}

			if (DML_TYPE.DELETE.equals(data.getOpType())) {
				for (String columnName : model.getColumns()) {
					if (!_ret.hasColumnValue(columnName)) {
						_ret.setColumnValue(columnName, null);
					}
				}
			} else {
				for (ColumnData columnData : data.getColumns()) {
					if (!_ret.hasColumnValue(columnData.getName())) {
						_ret.setColumnValue(columnData.getName(),
								columnData.getNewValue());
					}
				}
			}
		} else {
			String _id = data.getColumn("_ID").getNewValue();
			if (StringUtil.isBlank(_id)) {
				_id = data.getColumn("_ID").getOldValue();
			}
			String _data = data.getColumn("_DATA") == null ? "{}" : data
					.getColumn("_DATA").getNewValue();
			String _tag = data.getColumn("_TAG") == null ? "[]" : data
					.getColumn("_TAG").getNewValue();
			_ret = new AutoScalingRowData(model.getTableName(), _id, _data,
					_tag);
			_ret.setColumnValue("_ID", _id);
		}

		/**
		 * 运行transformfunc
		 */
		for (String columnName : model.getValueTransform().keySet()) {
			try {
				if (!_ret.hasColumnValue(columnName)) {
					continue;
				}
				String columnValue = _ret.getColumnValue(columnName);
				if(columnValue == null){
					continue;
				}
				String newValue = model.getValueTransform().get(columnName)
						.transform(columnValue);
				_ret.setColumnValue(columnName, newValue);
			} catch (Throwable e) {
				throw new TransformFuncExcetpion("transformFunc for column ["
						+ columnName + "] execute failure", e);
			}
		}

		return _ret;
	}

}
