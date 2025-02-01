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
import com.oak.dto.RoleDto;
import com.oak.entity.RoleEntity;

@Validated
public interface RoleService extends SearchService<RoleDto, RoleEntity, Long>, CrudService<RoleDto, RoleEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	RoleDto create(@IsNotNull("Role") @Valid RoleDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	RoleDto update(@IsNotNull("Role") @Valid RoleDto dto,
			@IsNotNull("Role Id") @EntityExists(RoleEntity.class) Long id);

	RoleDto read(@IsNotNull("Role Id") @EntityExists(RoleEntity.class) Long id);
	
	RoleDto readByName(@IsNotBlank("Role Name") String roleName);
	
	void delete(@IsNotNull("Role Id") @EntityExists(RoleEntity.class) Long id);
}
