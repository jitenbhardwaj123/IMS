package com.oak.validator;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import javax.validation.ConstraintValidatorContext;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.ExecutionResult;

public class AnalyticsEngineTemplateReturnTypeValidator extends BaseValidator<AnalyticsEngineTemplateReturnType, String> {

	@Override
	public boolean isValid(String rType, ConstraintValidatorContext context) {
		boolean valid = true;
		if (isNotBlank(rType) && ! rType.equals(ExecutionResult.class.getCanonicalName())) {
			addCustomViolation(context, "analytics-engine-template.return-type.not-allowed", "ExecutionResult.class.getCanonicalName()");
			return false;
		}
		return valid;
	}
}
