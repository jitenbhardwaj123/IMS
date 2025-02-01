package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.RoleDto;
import com.oak.entity.RoleEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface RoleMapper extends DtoAndEntityMapper<RoleDto, RoleEntity> {}
