package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.RangeLinkDataDto;
import com.oak.entity.RangeLinkDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface RangeLinkDataMapper extends DtoAndEntityMapper<RangeLinkDataDto, RangeLinkDataEntity> {}
