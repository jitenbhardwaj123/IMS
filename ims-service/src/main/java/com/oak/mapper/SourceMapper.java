package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.SourceDto;
import com.oak.entity.SourceEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface SourceMapper extends DtoAndEntityMapper<SourceDto, SourceEntity> {}
