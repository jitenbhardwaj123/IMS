package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.RangeDecimalDataDto;
import com.oak.entity.RangeDecimalDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface RangeDecimalDataMapper extends DtoAndEntityMapper<RangeDecimalDataDto, RangeDecimalDataEntity> {}
