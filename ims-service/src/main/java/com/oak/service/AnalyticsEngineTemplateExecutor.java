package com.oak.service;

import java.util.List;

import javax.validation.Valid;

import org.codehaus.commons.compiler.IScriptEvaluator;
import org.springframework.validation.annotation.Validated;

import com.oak.common.validation.constraint.IsNotEmpty;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.dto.AnalyticsEngineTemplateDto;
import com.oak.dto.ExecutionResult;
import com.oak.validator.AnalyticsEngineTemplateParamTypesSize;
import com.oak.validator.AnalyticsEngineTemplateParamValuesSize;

@Validated
public interface AnalyticsEngineTemplateExecutor {

	IScriptEvaluator validateTemplate(
			@IsNotNull("Analytics Engine Template")
			@AnalyticsEngineTemplateParamTypesSize 
			@Valid 
			AnalyticsEngineTemplateDto template);

	@AnalyticsEngineTemplateParamValuesSize
	ExecutionResult executeTemplate(
			@IsNotNull("Analytics Engine Template") 
			@AnalyticsEngineTemplateParamTypesSize 
			@Valid 
			AnalyticsEngineTemplateDto template, 
			@IsNotEmpty("Analytics Engine Template Param Values") 
			List<Object> paramValues);
}
