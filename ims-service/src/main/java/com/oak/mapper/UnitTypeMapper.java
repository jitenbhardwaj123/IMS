package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.UnitTypeDto;
import com.oak.entity.UnitTypeEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface UnitTypeMapper extends DtoAndEntityMapper<UnitTypeDto, UnitTypeEntity> {}
