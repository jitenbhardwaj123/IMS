package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.RangeTextDataDto;
import com.oak.entity.RangeTextDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface RangeTextDataMapper extends DtoAndEntityMapper<RangeTextDataDto, RangeTextDataEntity> {}
