package com.oak.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.validation.annotation.Validated;

import com.oak.common.rest.service.CrudService;
import com.oak.common.rest.service.SearchService;
import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.UnitDto;
import com.oak.entity.UnitEntity;
import com.oak.entity.UnitTypeEntity;

@Validated
public interface UnitService extends SearchService<UnitDto, UnitEntity, Long>, CrudService<UnitDto, UnitEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	UnitDto create(@IsNotNull("Unit") @Valid UnitDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	UnitDto update(@IsNotNull("Unit") @Valid UnitDto dto,
			@IsNotNull("Unit Id") @EntityExists(UnitEntity.class) Long id);

	UnitDto read(@IsNotNull("Unit Id") @EntityExists(UnitEntity.class) Long id);
	
	UnitDto readByName(@IsNotBlank("Unit Name") String unitName);
	
	List<UnitDto> readByUnitType(@EntityExists(UnitTypeEntity.class) long id);

	void delete(@IsNotNull("Unit Id") @EntityExists(UnitEntity.class) Long id);
}
