package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.LimitTypeDto;
import com.oak.entity.LimitTypeEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface LimitTypeMapper extends DtoAndEntityMapper<LimitTypeDto, LimitTypeEntity> {}
