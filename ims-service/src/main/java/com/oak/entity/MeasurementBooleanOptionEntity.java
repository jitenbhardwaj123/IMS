package com.oak.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.oak.common.validation.constraint.IsNotBlank;

@Embeddable
public class MeasurementBooleanOptionEntity {
	@Column(name = "true_option")
	@IsNotBlank("Measurement True Option")
	private String trueOption;

	@Column(name = "false_option")
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
}
