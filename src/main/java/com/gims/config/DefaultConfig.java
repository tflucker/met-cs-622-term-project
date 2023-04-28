package com.gims.config;

public class DefaultConfig<T> {

	T config;

	public DefaultConfig(T config, T otherInfo) {
		if (((Config) config).getType().equals("APP")) {
			// if configType is APP, then set otherInfo as a string for the 'commandType'
			// property
			((AppConfig) config).setCommandType((String) otherInfo);

		} else if (((Config) config).getType().equals("STORE")) {
			// if configType is STORE, then set other info as a long for the 'storeId'
			// property
			((StoreConfig) config).setStoreId((Long) otherInfo);
		}
		this.config = config;
	}

	public T getConfig() {
		return this.config;
	}

}
