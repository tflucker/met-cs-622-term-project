package com.gims.config;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.gims.constants.ConfigType;

@Entity
@DiscriminatorValue("APP")
public class AppConfig extends Config {

	@Column(name = "COMMAND_TYPE")
	public String commandType;

	@Override
	public String getType() {
		return ConfigType.APP.name();
	}

	public AppConfig(long id, String userId, String name, String value, String defaultValue, String type,
			String commandType) {
		super(id, userId, name, value, defaultValue, type);
		this.commandType = commandType;
	}

	public AppConfig(String userId, String name, String value, String defaultValue, String type, String commandType) {
		super(userId, name, value, defaultValue, type);
		this.commandType = commandType;
	}
	
	public AppConfig(String userId, String name, String value, String defaultValue, String valueType) {
		super(userId, name, value, defaultValue, valueType);
	}

	public AppConfig() {

	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

}
