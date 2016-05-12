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

public class TestTableJoinTransformMain extends TestCase {
	public void testETL() throws DBModelDefineExcetpion, IOException,
			SQLException {
		InstanceContainer.init("application-context.xml");
		ModelFileLoader.load();
		GroovyScriptLoader.load();

		IGotoTransform trans = InstanceContainer.getBean(IGotoTransform.class);
//		ExtractData data = new ExtractData("TEST_TABLE_JOIN", null,
//				DML_TYPE.INSERT);
//		data.appendColumn(new ColumnData("_ID", "1", "1"));
//		data.appendColumn(new ColumnData("_DATA", null,
//				"{'STS_TIME':'2016-5-1 16:42:19','STS':'Y','NUM':'100','REMARKS':'asdfsdf123'}"));
//		data.appendColumn(new ColumnData("_TAG", "[\"N\"]", "[\"Y\"]"));

		// ExtractData data = new ExtractData("TEST_TABLE", null,
		// DML_TYPE.UPDATE);
		// data.appendColumn(new ColumnData("ROWVALUE_ID", null, "1"));
		// data.appendColumn(new ColumnData("REMARKS", null, "asdfsdf123"));
		// data.appendColumn(new ColumnData("STS", null, "1"));
		// data.appendColumn(new ColumnData("STS_TIME", null,
		// "2016-5-1 16:42:19"));
		// data.appendColumn(new ColumnData("NUM", null, "100"));
		
		ExtractData data = new ExtractData("TEST_TABLE_JOIN", null,
				DML_TYPE.DELETE);
		data.appendColumn(new ColumnData("_ID", "1", null));
		data.appendColumn(new ColumnData("_DATA", "{'STS_TIME':'2016-5-1 16:42:19','STS':'Y','NUM':'100','REMARKS':'asdfsdf123'}",
				"{'STS_TIME':null,'STS':null,'NUM':null,'REMARKS':null}"));
		data.appendColumn(new ColumnData("_TAG", "[\"Y\"]", "[\"N\"]"));
		
		trans.transform(data);
	}
}
