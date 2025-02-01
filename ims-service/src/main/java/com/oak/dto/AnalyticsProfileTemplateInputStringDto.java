package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.validation.constraint.IsNotBlank;

public class AnalyticsProfileTemplateInputStringDto {
	@IsNotBlank("Analytics Profile Template Input String Key")
	private String key;

	private String value;

	public AnalyticsProfileTemplateInputStringDto() {}

	public AnalyticsProfileTemplateInputStringDto(
			@IsNotBlank("Analytics Profile Template Input String Key") String key) {
		super();
		this.key = key;
	}

	public AnalyticsProfileTemplateInputStringDto(@IsNotBlank("Analytics Profile Template Input String Key") String key,
			String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("key", key)
				.append("value", value).toString();
	}
}