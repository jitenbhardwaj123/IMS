package com.oak.dto;

import static java.util.Arrays.asList;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oak.common.validation.constraint.IsNotBlank;

public class MeasurementBooleanOptionDto {
	@IsNotBlank("Measurement True Option")
	private String trueOption;

	@IsNotBlank("Measurement False Option")
	private String falseOption;

	public String getTrueOption() {
		return trueOption;
	}

	public void setTrueOption(String trueOption) {
		this.trueOption = trueOption;
	}

	public String getFalseOption() {
		return falseOption;
	}

	public void setFalseOption(String falseOption) {
		this.falseOption = falseOption;
	}
	
	@JsonIgnore
	public List<String> getOptions() {
		return asList(trueOption, falseOption);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString())
				.append("trueOption", trueOption).append("falseOption", falseOption).toString();
	}
}