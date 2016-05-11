package com.ai.edc.etl.transform;

import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;

import com.ai.edc.common.container.InstanceContainer;
import com.ai.edc.etl.bridge.extract2transform.ExtractData;
import com.ai.edc.etl.bridge.extract2transform.ExtractData.ColumnData;
import com.ai.edc.etl.bridge.extract2transform.ExtractData.DML_TYPE;
import com.ai.edc.etl.bridge.extract2transform.IGotoTransform;
import com.ai.edc.etl.transform.dbmodel.DBModelDefineExcetpion;
import com.ai.edc.etl.transform.dbmodel.ModelFileLoader;
import com.ai.edc.etl.transform.groovy.GroovyScriptLoader;

public class TestTableStat2TestTableStatDepartTransformMain extends TestCase {
	public void testETL() throws DBModelDefineExcetpion, IOException,
			SQLException {
		InstanceContainer.init("application-context.xml");
		ModelFileLoader.load();
		GroovyScriptLoader.load();

		IGotoTransform trans = InstanceContainer.getBean(IGotoTransform.class);
		ExtractData data = new ExtractData("TEST_TABLE_STAT", null,
				DML_TYPE.INSERT);
		data.appendColumn(new ColumnData("_ID", "1", "1"));
		data.appendColumn(new ColumnData("_DATA", "{'DEPARTID':'asdfsdf123','NUMSUM':'95'}",
				"{'DEPARTID':'asdfsdf123','NUMSUM':'100'}"));
		data.appendColumn(new ColumnData("_TAG", "[]", "[]"));

		// ExtractData data = new ExtractData("TEST_TABLE", null,
		// DML_TYPE.UPDATE);
		// data.appendColumn(new ColumnData("ROWVALUE_ID", null, "1"));
		// data.appendColumn(new ColumnData("REMARKS", null, "asdfsdf123"));
		// data.appendColumn(new ColumnData("STS", null, "1"));
		// data.appendColumn(new ColumnData("STS_TIME", null,
		// "2016-5-1 16:42:19"));
		// data.appendColumn(new ColumnData("NUM", null, "100"));
		trans.transform(data);
	}
}
