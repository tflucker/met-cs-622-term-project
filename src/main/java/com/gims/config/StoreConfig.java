package com.gims.config;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.gims.constants.ConfigType;

@Entity
@DiscriminatorValue("STORE")
public class StoreConfig extends Config {

	@Column(name="STORE_ID")
	public long storeId;

	@Override
	public String getType() {
		return ConfigType.STORE.name();
	}

	public StoreConfig(long id, String userId, String name, String value, String defaultValue, String type, long storeId) {
		super(id, userId, name, value, defaultValue, type);
		this.storeId = storeId;
	}
	
	public StoreConfig(String userId, String name, String value, String defaultValue, String valueType, long storeId) {
		super(userId, name, value, defaultValue, valueType);
		this.storeId = storeId;
	}
	
	public StoreConfig(String userId, String name, String value, String defaultValue, String valueType) {
		super(userId, name, value, defaultValue, valueType);
	}
	
	public StoreConfig() {
		
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

}
