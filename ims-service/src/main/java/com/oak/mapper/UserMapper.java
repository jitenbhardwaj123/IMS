package com.oak.mapper;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.UserDto;
import com.oak.entity.UserEntity;

@Mapper(config = MapStructConfigMapper.class)
public abstract class UserMapper implements DtoAndEntityMapper<UserDto, UserEntity> {
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@Override
	@Mapping(target = "password", ignore = true)
	public abstract UserDto toDto(UserEntity entity);

	@AfterMapping
	public void encodePassword(UserDto dto, @MappingTarget UserEntity entity) {
		if (isNotBlank(entity.getPassword())) {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		}
	}
}
