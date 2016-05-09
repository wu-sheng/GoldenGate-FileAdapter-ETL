package com.ai.edc.etl.transform.groovy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class GroovyScriptLoader {
	private static Logger logger = LogManager
			.getLogger(GroovyScriptLoader.class);

	private static String JOIN_FILE_PATTERN = "classpath*:/event_subscription/*/*.join.groovy";

	private static String TAG_FILE_PATTERN = "classpath*:/event_subscription/*/*.tag.groovy";

	private static String GROUP_FILE_PATTERN = "classpath*:/event_subscription/*/*.group.groovy";
	
	private static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");  

	private static Map<String, String> SCRIPTS = new HashMap<String, String>();

	public static void load() throws IOException {
		loadGroovy(JOIN_FILE_PATTERN);
		loadGroovy(TAG_FILE_PATTERN);
		loadGroovy(GROUP_FILE_PATTERN);
	}
	
	public static boolean hasScript(String scriptkey){
		return SCRIPTS.containsKey(scriptkey);
	}

	public static String getScript(String scriptkey, String... scriptLines)
			throws GroovyScriptNotFoundExcetpion {
		if (SCRIPTS.containsKey(scriptkey)){
			StringBuilder script = new StringBuilder(SCRIPTS.get(scriptkey));
			for(String scriptLine : scriptLines){
				script.append(scriptLine).append(LINE_SEPARATOR);
			}
			return script.toString();
		}else{
			throw new GroovyScriptNotFoundExcetpion("script [" + scriptkey
					+ "] not found.");
		}
	}

	private static void loadGroovy(String pattern) throws IOException {
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
		Resource[] resources = (Resource[]) resolver.getResources(pattern);
		if (resources.length == 0) {
			logger.warn("no groovy file define in {}", JOIN_FILE_PATTERN);
		}

		for (Resource resource : resources) {
			String filename = resource.getFilename();
			String scriptkey = filename.toUpperCase().substring(0,
					filename.length() - ".groovy".length());

			logger.debug("find file: {}", resource.getDescription());
			StringBuilder script = new StringBuilder();
			try (InputStream is = resource.getInputStream()) {
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(is, "UTF-8"))) {
					String line;
					while ((line = br.readLine()) != null) {
						script.append(line).append(LINE_SEPARATOR);
					}
				}
			}
			SCRIPTS.put(scriptkey, script.toString());
		}
	}

}
