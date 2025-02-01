package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.AnalyticsEngineTemplateDto;
import com.oak.entity.AnalyticsEngineTemplateEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface AnalyticsEngineTemplateMapper extends DtoAndEntityMapper<AnalyticsEngineTemplateDto, AnalyticsEngineTemplateEntity> {}
