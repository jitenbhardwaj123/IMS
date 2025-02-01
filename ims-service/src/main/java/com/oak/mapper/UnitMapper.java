package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.UnitDto;
import com.oak.entity.UnitEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface UnitMapper extends DtoAndEntityMapper<UnitDto, UnitEntity> {}
