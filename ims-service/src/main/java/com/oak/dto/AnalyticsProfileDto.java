package com.oak.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.IsNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.constraint.NotNullAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.enums.AnalyticsEngineType;

// TODO: validate to make sure the template params are either defined as elements or strings in profile
// TODO: validate to make sure we donot have same keys within/across elements and strings
public class AnalyticsProfileDto {
	@IsNull(groups = CreateValidationGroup.class, value = "Analytics Profile Id")
    @IsNotNull(groups = UpdateValidationGroup.class, value = "Analytics Profile Id")
	private Long id;

	@IsNotBlank("Analytics Profile Name")
	private String name;

	@IsNotBlank("Analytics Profile Description")
	private String description;

	@IsNotNull("Analytics Engine Type")
	private AnalyticsEngineType engineType;

	@IsNotNull("Analytics Engine Template")
	private AnalyticsEngineTemplateDto template;

	@NotEmptyAndNoNullElements("Analytics Profile Input Elements")
	private List<AnalyticsProfileTemplateInputElementDto> inputElements = new ArrayList<>();
	
	@NotNullAndNoNullElements("Analytics Profile Input Strings")
	private List<AnalyticsProfileTemplateInputStringDto> inputStrings = new ArrayList<>();
	
	@NotEmptyAndNoNullElements("Analytics Profile Result Elements")
	private List<AnalyticsProfileTemplateResultElementDto> resultElements = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

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

	public AnalyticsEngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(AnalyticsEngineType engineType) {
		this.engineType = engineType;
	}

	public AnalyticsEngineTemplateDto getTemplate() {
		return template;
	}

	public void setTemplate(AnalyticsEngineTemplateDto template) {
		this.template = template;
	}

	public List<AnalyticsProfileTemplateInputElementDto> getInputElements() {
		return inputElements;
	}

	public void setInputElements(List<AnalyticsProfileTemplateInputElementDto> inputElements) {
		this.inputElements = inputElements;
	}

	public List<AnalyticsProfileTemplateInputStringDto> getInputStrings() {
		return inputStrings;
	}

	public void setInputStrings(List<AnalyticsProfileTemplateInputStringDto> inputStrings) {
		this.inputStrings = inputStrings;
	}

	public List<AnalyticsProfileTemplateResultElementDto> getResultElements() {
		return resultElements;
	}

	public void setResultElements(List<AnalyticsProfileTemplateResultElementDto> resultElements) {
		this.resultElements = resultElements;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).append("engineType", engineType)
				.append("template", template).append("inputElements", inputElements)
				.append("inputStrings", inputStrings).append("resultElements", resultElements).toString();
	}
}