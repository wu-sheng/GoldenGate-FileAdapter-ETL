package com.ai.edc.etl.transform;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.edc.common.datasource.DBConnector;
import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.bridge.extract2transform.IGotoTransform;
import com.ai.edc.etl.transform.dbmodel.AutoScalingRowData;
import com.ai.edc.etl.transform.dbmodel.DBModel;
import com.ai.edc.etl.transform.dbmodel.ModelFileLoader;
import com.ai.edc.etl.transform.join.IJoin;
import com.ai.edc.etl.transform.sync2mirror.ISync;
import com.ai.edc.etl.transform.transformfunc.IDataTrans;

@Component
public class Transform implements IGotoTransform {
	@Autowired
	private ISync sync;

	@Autowired
	private IDataTrans trans;

	@Autowired
	private IJoin join;

	@Override
	public void transform(ExtractData data) throws SQLException {
		DataSource ds = DBConnector.getDataSource("mirror");
		try (Connection mirrorConn = ds.getConnection()) {
			String tableName = data.getTableName();// 根据表名称，定义不同的订阅模式
			DBModel model = ModelFileLoader.findModel(tableName);

			if (model.isMirror()) {
				/**
				 * 1.同步到镜像库
				 */
				sync.toDB(mirrorConn, model, data);
			}

			/**
			 * 2.数据的编码转换
			 */
			AutoScalingRowData autoScalingRowData = trans.toTransform(model,
					data);

			DataSource ds2 = DBConnector.getDataSource("statistics");
			try (Connection statisticsConn = ds2.getConnection()) {
				/**
				 * 3.join groovy
				 */
				AutoScalingRowData afterJoinAutoScalingRowData = join.join(
						statisticsConn, model, autoScalingRowData);

				/**
				 * 4.tag groovy
				 */

				/**
				 * 5.group groovy
				 */

				statisticsConn.commit();
			}

			mirrorConn.commit();
		}
	}

}
