package com.oak.service;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.validation.annotation.Validated;

import com.oak.common.rest.service.CrudService;
import com.oak.common.rest.service.SearchService;
import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.AnalyticsEngineTemplateDto;
import com.oak.entity.AnalyticsEngineTemplateEntity;
import com.oak.validator.AnalyticsEngineTemplateParamTypesSize;

@Validated
public interface AnalyticsEngineTemplateService
		extends SearchService<AnalyticsEngineTemplateDto, AnalyticsEngineTemplateEntity, Long>, CrudService<AnalyticsEngineTemplateDto, AnalyticsEngineTemplateEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	AnalyticsEngineTemplateDto create(@IsNotNull("Analytics Engine Template") @AnalyticsEngineTemplateParamTypesSize @Valid AnalyticsEngineTemplateDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	AnalyticsEngineTemplateDto update(@IsNotNull("Analytics Engine Template") @AnalyticsEngineTemplateParamTypesSize @Valid AnalyticsEngineTemplateDto dto,
			@IsNotNull("Analytics Engine Template Id") @EntityExists(AnalyticsEngineTemplateEntity.class) Long id);

	AnalyticsEngineTemplateDto read(@IsNotNull("Analytics Engine Template Id") @EntityExists(AnalyticsEngineTemplateEntity.class) Long id);

	void delete(@IsNotNull("Analytics Engine Template Id") @EntityExists(AnalyticsEngineTemplateEntity.class) Long id);
}
