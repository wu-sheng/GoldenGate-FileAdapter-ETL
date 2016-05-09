package com.ai.edc.common.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnector {
	private static Map<String, HikariDataSource> DS_POOL = new ConcurrentHashMap<String, HikariDataSource>();

	public static void init(String name, String configPath) {
		HikariConfig config = new HikariConfig(configPath);
		config.setAutoCommit(false);
		HikariDataSource ds = new HikariDataSource(config);
		DS_POOL.put(name, ds);
	}

	public static DataSource getDataSource(String name) {
		if (!DS_POOL.containsKey(name)) {
			init(name, "/" + name + ".hikari.properties");
		}
		return DS_POOL.get(name);
	}
}
