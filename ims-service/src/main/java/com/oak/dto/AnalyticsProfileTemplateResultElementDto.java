package com.oak.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.enums.DataCategory;

public class AnalyticsProfileTemplateResultElementDto {
	@IsNotNull("Analytics Profile Template Result Element")
	private ElementDto element;
	
	@IsNotNull("Analytics Profile Template Result Element Data Category")
	private DataCategory dataCategory;
	
	@IsNotBlank("Analytics Profile Template Result Element Key")
	private String key;
	
	public AnalyticsProfileTemplateResultElementDto() {}
	
	public AnalyticsProfileTemplateResultElementDto(
			@IsNotNull("Analytics Profile Template Result Element") ElementDto element,
			@IsNotNull("Analytics Profile Template Result Element Data Category") DataCategory dataCategory,
			@IsNotBlank("Analytics Profile Template Result Element Key") String key) {
		super();
		this.element = element;
		this.dataCategory = dataCategory;
		this.key = key;
	}

	public ElementDto getElement() {
		return element;
	}

	public void setElement(ElementDto element) {
		this.element = element;
	}

	public DataCategory getDataCategory() {
		return dataCategory;
	}

	public void setDataCategory(DataCategory dataCategory) {
		this.dataCategory = dataCategory;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("element", element).append("dataCategory", dataCategory).append("key", key).toString();
	}
}