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
import com.oak.dto.LimitTypeDto;
import com.oak.entity.LimitTypeEntity;

@Validated
public interface LimitTypeService
		extends SearchService<LimitTypeDto, LimitTypeEntity, Long>, CrudService<LimitTypeDto, LimitTypeEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	LimitTypeDto create(@IsNotNull("Limit Type") @Valid LimitTypeDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	LimitTypeDto update(@IsNotNull("Limit Type") @Valid LimitTypeDto dto,
			@IsNotNull("Limit Type Id") @EntityExists(LimitTypeEntity.class) Long id);

	LimitTypeDto read(@IsNotNull("Limit Type Id") @EntityExists(LimitTypeEntity.class) Long id);

	void delete(@IsNotNull("Limit Type Id") @EntityExists(LimitTypeEntity.class) Long id);
}
