package com.oak.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.IsValidClassName;
import com.oak.common.validation.constraint.NoNullElements;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.validator.AnalyticsEngineTemplateReturnType;

public class AnalyticsEngineTemplateDto extends AuditableDto implements Identifiable {
	@IsNull(groups = CreateValidationGroup.class, value = "Analytics Engine Template Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Analytics Engine Template Id")
	private Long id;

	@IsNotBlank("Template Name")
	private String name;

	@IsNotBlank("Template Description")
	private String description;

	@IsNotBlank("Template")
	private String template;

	@IsNotBlank("Template Return Type")
	@IsValidClassName("Analytics Engine Template return type")
	@AnalyticsEngineTemplateReturnType
	private String returnType;

	@NoNullElements("Analytics Engine Template Imports")
	private List<String> imports = new ArrayList<>();

	@NoNullElements("Analytics Engine Template Exceptions")
	private List<@IsValidClassName("Analytics Engine Template Exception") String> exceptions = new ArrayList<>();

	@NotEmptyAndNoNullElements("Analytics Engine Template Params")
	private List<String> params = new ArrayList<>();

	@NotEmptyAndNoNullElements("Analytics Engine Template Param Types")
	private List<@IsValidClassName("Analytics Engine Template Param Type") String> paramTypes = new ArrayList<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports = imports;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public List<String> getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(List<String> paramTypes) {
		this.paramTypes = paramTypes;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).append("template", template)
				.append("returnType", returnType).append("imports", imports).append("exceptions", exceptions)
				.append("params", params).append("paramTypes", paramTypes).toString();
	}
}
