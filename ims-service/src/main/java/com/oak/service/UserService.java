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
import com.oak.dto.UserDto;
import com.oak.entity.UserEntity;

@Validated
public interface UserService extends SearchService<UserDto, UserEntity, Long>, CrudService<UserDto, UserEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	UserDto create(@IsNotNull("User") @Valid UserDto dto);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	UserDto register(@IsNotNull("User") @Valid UserDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	UserDto update(@IsNotNull("User") @Valid UserDto dto,
			@IsNotNull("User Id") @EntityExists(UserEntity.class) Long id);

	UserDto read(@IsNotNull("User Id") @EntityExists(UserEntity.class) Long id);
	
	UserDto readByUsername(@IsNotBlank("Username") String userName);
	
	void delete(@IsNotNull("User Id") @EntityExists(UserEntity.class) Long id);
}
