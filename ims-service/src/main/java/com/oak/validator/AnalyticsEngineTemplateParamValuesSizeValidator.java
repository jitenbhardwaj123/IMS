package com.oak.validator;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.AnalyticsEngineTemplateDto;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class AnalyticsEngineTemplateParamValuesSizeValidator extends BaseValidator<AnalyticsEngineTemplateParamValuesSize, Object[]> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(Object[] values, ConstraintValidatorContext context) {
		boolean valid = true;
		if (values != null && values.length == 2 && isNotEmpty(((AnalyticsEngineTemplateDto)values[0]).getParams()) && isNotEmpty((List<Object>)values[1])) {
			if (((AnalyticsEngineTemplateDto)values[0]).getParams().size() != ((List<Object>)values[1]).size()) {
				valid = false;
			}
		}
		return valid;
	}
}
