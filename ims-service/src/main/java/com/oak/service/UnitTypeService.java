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
import com.oak.dto.UnitTypeDto;
import com.oak.entity.UnitTypeEntity;

@Validated
public interface UnitTypeService
		extends SearchService<UnitTypeDto, UnitTypeEntity, Long>, CrudService<UnitTypeDto, UnitTypeEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	UnitTypeDto create(@IsNotNull("Unit Type") @Valid UnitTypeDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	UnitTypeDto update(@IsNotNull("Unit Type") @Valid UnitTypeDto dto,
			@IsNotNull("Unit Type Id") @EntityExists(UnitTypeEntity.class) Long id);

	UnitTypeDto read(@IsNotNull("Unit Type Id") @EntityExists(UnitTypeEntity.class) Long id);

	void delete(@IsNotNull("Unit Type Id") @EntityExists(UnitTypeEntity.class) Long id);
}
