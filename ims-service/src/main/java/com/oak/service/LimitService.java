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
import com.oak.dto.LimitDto;
import com.oak.entity.LimitEntity;

@Validated
public interface LimitService
		extends SearchService<LimitDto, LimitEntity, Long>, CrudService<LimitDto, LimitEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	LimitDto create(@IsNotNull("Limit") @Valid LimitDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	LimitDto update(@IsNotNull("Limit") @Valid LimitDto dto,
			@IsNotNull("Limit Id") @EntityExists(LimitEntity.class) Long id);

	LimitDto read(@IsNotNull("Limit Id") @EntityExists(LimitEntity.class) Long id);

	void delete(@IsNotNull("Limit Id") @EntityExists(LimitEntity.class) Long id);
}
