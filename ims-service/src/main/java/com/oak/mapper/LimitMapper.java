package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.LimitDto;
import com.oak.entity.LimitEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface LimitMapper extends DtoAndEntityMapper<LimitDto, LimitEntity> {}
