package com.ai.edc.etl.transform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.bridge.extract2transform.IGotoTransform;
import com.ai.edc.etl.transform.sync2mirror.ISync;

@Component
public class Transform implements IGotoTransform {
	@Autowired
	private ISync sync;

	@Override
	public void transform(ExtractData data) {
		data.getTableName();//根据表名称，定义不同的订阅模式
		
		/**
		 * 1.同步到镜像库
		 */
		sync.toDB(data);

		/**
		 * 2.数据的编码转换
		 */
		
		/**
		 * 3.join groovy
		 */
		
		/**
		 * 4.tag groovy
		 */
		
		/**
		 * 5.group groovy
		 */
	}

}
