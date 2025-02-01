package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.WidgetDto;
import com.oak.entity.WidgetEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface WidgetMapper extends DtoAndEntityMapper<WidgetDto, WidgetEntity> {}
