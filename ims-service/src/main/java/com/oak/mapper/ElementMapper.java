package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.ElementDto;
import com.oak.entity.ElementEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface ElementMapper extends DtoAndEntityMapper<ElementDto, ElementEntity> {}
