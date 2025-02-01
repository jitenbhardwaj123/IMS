package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.RangeBooleanDataDto;
import com.oak.entity.RangeBooleanDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface RangeBooleanDataMapper extends DtoAndEntityMapper<RangeBooleanDataDto, RangeBooleanDataEntity> {}
