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
import com.oak.dto.SourceDto;
import com.oak.entity.SourceEntity;

@Validated
public interface SourceService
		extends SearchService<SourceDto, SourceEntity, Long>, CrudService<SourceDto, SourceEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	SourceDto create(@IsNotNull("Source") @Valid SourceDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	SourceDto update(@IsNotNull("Source") @Valid SourceDto dto,
			@IsNotNull("Source Id") @EntityExists(SourceEntity.class) Long id);

	SourceDto read(@IsNotNull("Source Id") @EntityExists(SourceEntity.class) Long id);

	void delete(@IsNotNull("Source Id") @EntityExists(SourceEntity.class) Long id);
	
	SourceDto getAnalyticsSource();
}
