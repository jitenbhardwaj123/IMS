package com.oak.service;

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
import com.oak.dto.PrivilegeDto;
import com.oak.entity.PrivilegeEntity;

@Validated
public interface PrivilegeService extends SearchService<PrivilegeDto, PrivilegeEntity, Long>, CrudService<PrivilegeDto, PrivilegeEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	PrivilegeDto create(@IsNotNull("Privilege") @Valid PrivilegeDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	PrivilegeDto update(@IsNotNull("Privilege") @Valid PrivilegeDto dto,
			@IsNotNull("Privilege Id") @EntityExists(PrivilegeEntity.class) Long id);

	PrivilegeDto read(@IsNotNull("Privilege Id") @EntityExists(PrivilegeEntity.class) Long id);
	
	PrivilegeDto readByName(@IsNotBlank("Privilege Name") String privilegeName);
	
	void delete(@IsNotNull("Privilege Id") @EntityExists(PrivilegeEntity.class) Long id);
}
