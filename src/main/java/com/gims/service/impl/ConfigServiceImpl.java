package com.gims.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gims.config.AppConfig;
import com.gims.config.Config;
import com.gims.config.DefaultConfig;
import com.gims.config.StoreConfig;
import com.gims.constants.CommonConstants;
import com.gims.dao.ConfigRepository;
import com.gims.exception.InvalidConfigValueException;
import com.gims.exception.NoConfigsFoundException;
import com.gims.service.ConfigService;
import com.gims.service.StoreService;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private StoreService storeService;

	@Override
	public List<Config> retrieveAppConfigs(String userId) {
		return configRepository.getAppConfigs(userId);
	}

	@Override
	public List<Config> retrieveStoreConfig(String userId, long storeId) {
		return configRepository.getStoreConfigs(userId, storeId);
	}

	@Override
	public void saveConfig(Config c) {
		configRepository.save(c);
	}

	@Override
	public Config findConfigByNameAndUserId(String name, String userId, String commandType) {
		Config c = configRepository.findConfigByNameAndUserId(name, userId);
		if (c == null) {
			c = findDefaultValues(name, userId, commandType);
			c = configRepository.save(c);
		}
		return c;
	}

	@Override
	public Config findStoreConfig(String name, String userId, long storeId) {
		Config c = configRepository.findStoreConfig(name, userId, storeId);
		if (c == null) {
			c = findDefaultValues(name, userId, storeId);
			c = configRepository.save(c);
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getConfigValue(Config c) {
		T value = null;
		try {
			// attempt to cast the value of the Config 'value' field to the specified object
			// type
			try {
				if (c.getValueType().equals("Integer")) {
					value = (T) Integer.valueOf(c.getValue());
				} else if (c.getValueType().equals("Boolean")) {
					value = (T) Boolean.valueOf(c.getValue());
				} else {
					value = (T) c.getValue();
				}
			} catch (NumberFormatException nfe) {
				throw new InvalidConfigValueException(CommonConstants.CONFIG_VALUE_ERROR);
			}

		} catch (InvalidConfigValueException e) {
			// if value cannot be cast, then attempt to repeat the process with the
			// 'defaultValue', which cannot be modified
			value = (T) Integer.valueOf(c.getDefaultValue());
		}
		return value;
	}

	@Override
	public boolean checkForConfigWithUserId(String userId) {
		// get number of records that have the specified userId value
		long recordCount = configRepository.findByUserId(userId);
		// return true if count greater than 0, else false
		if (recordCount == 0) {
			throw new NoConfigsFoundException(CommonConstants.NO_CONFIGS_FOUND);
		} else {
			return true;
		}
	}

	@Override
	public void generateDefaultConfigs(String userId) {

		List<Config> defaultConfigs = new ArrayList<>();

		// add store configs, for each id
		storeService.retrieveIds().stream().forEach(storeId -> {

			defaultConfigs.add(new StoreConfig(userId, "shopping.threads", "5", "5", "Integer", storeId));

		});

		// add app configs
		defaultConfigs.add(new AppConfig(userId, "delete.confirm", "true", "true", "Boolean", "DELETE"));

		configRepository.saveAll(defaultConfigs);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> Config findDefaultValues(String name, String userId, T otherInformation) {

		Map<String, DefaultConfig<T>> defaultConfigMap = new HashMap<String, DefaultConfig<T>>();

		defaultConfigMap.put("delete.confirm",
				new DefaultConfig(new AppConfig(userId, name,"true", "true", "Boolean"), otherInformation));
		defaultConfigMap.put("shopping.threads",
				new DefaultConfig(new StoreConfig(userId, name,"5", "5", "Integer"), otherInformation));

		return (Config) defaultConfigMap.get(name).getConfig();

	}

}
