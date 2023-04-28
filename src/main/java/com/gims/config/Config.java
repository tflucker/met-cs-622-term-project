package com.gims.config;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "CONFIG")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CONFIG_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Config {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Column(name = "USER_ID")
	public String userId;

	@Column(name = "NAME")
	public String name;

	@Column(name = "VALUE")
	public String value;

	@Column(name = "DEFAULT_VALUE")
	public String defaultValue;

	@Column(name = "VALUE_TYPE")
	public String valueType;

	public abstract String getType();

	public Config(long id, String userId, String name, String value, String defaultValue, String valueType) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.value = value;
		this.defaultValue = defaultValue;
		this.valueType = valueType;
	}

	public Config(String userId, String name, String value, String defaultValue, String valueType) {
		super();
		this.userId = userId;
		this.name = name;
		this.value = value;
		this.defaultValue = defaultValue;
		this.valueType = valueType;
	}

	public Config(String value, String defaultValue, String valueType) {
		this.value = value;
		this.defaultValue = defaultValue;
		this.valueType = valueType;
	}
	
	public Config() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}
