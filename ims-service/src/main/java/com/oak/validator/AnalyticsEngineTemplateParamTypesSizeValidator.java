package com.oak.validator;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import javax.validation.ConstraintValidatorContext;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.AnalyticsEngineTemplateDto;

public class AnalyticsEngineTemplateParamTypesSizeValidator extends BaseValidator<AnalyticsEngineTemplateParamTypesSize, AnalyticsEngineTemplateDto> {

	@Override
	public boolean isValid(AnalyticsEngineTemplateDto template, ConstraintValidatorContext context) {
		boolean valid = true;
		if (template != null && isNotEmpty(template.getParams()) && isNotEmpty(template.getParamTypes())) {
			if (template.getParams().size() != template.getParamTypes().size()) {
				valid = false;
			}
		}
		return valid;
	}
}
