package com.ai.edc.etl.transform.dbmodel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ModelFileLoader {
	private static Logger logger = LogManager.getLogger(ModelFileLoader.class);

	private static String DMD_FILE_PATTERN = "classpath*:/db_model_define/*/*.dmd";

	private static Models MODOLS = new Models();

	public static void load() throws IOException, DBModelDefineExcetpion {
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
		Resource[] resources = (Resource[]) resolver
				.getResources(DMD_FILE_PATTERN);

		if (resources.length == 0) {
			logger.warn("no dmd file define in {}", DMD_FILE_PATTERN);
		}

		for (Resource resource : resources) {
			logger.debug("find dmd file: {}", resource.getDescription());
			try (InputStream is = resource.getInputStream()) {
				MODOLS.addModel(resource.getFilename(), is);
			}
		}
	}
	
	public static DBModel findModel(String tableName) throws DBModelDefineExcetpion {
		return MODOLS.findModel(tableName);
	}
}
