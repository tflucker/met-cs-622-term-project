package com.gims.service;

import java.util.List;

import com.gims.config.Config;

public abstract interface ConfigService {

	public boolean checkForConfigWithUserId(String userId);
	
	public List<Config> retrieveAppConfigs(String userId);

	public List<Config> retrieveStoreConfig(String userId, long storeId);

	public Config findConfigByNameAndUserId(String name, String userId, String commandType);

	public Config findStoreConfig(String name, String userId, long storeId);

	public <T> T getConfigValue(Config c);
	
	public void saveConfig(Config c);

	public void generateDefaultConfigs(String userId);
}
