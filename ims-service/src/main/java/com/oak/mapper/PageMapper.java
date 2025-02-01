package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.PageDto;
import com.oak.entity.PageEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface PageMapper extends DtoAndEntityMapper<PageDto, PageEntity> {}
