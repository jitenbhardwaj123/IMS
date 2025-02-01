package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.PrivilegeDto;
import com.oak.entity.PrivilegeEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface PrivilegeMapper extends DtoAndEntityMapper<PrivilegeDto, PrivilegeEntity> {}
