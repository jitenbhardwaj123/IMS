package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.LinkRecordDto;
import com.oak.entity.LinkRecordEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface LinkRecordMapper extends DtoAndEntityMapper<LinkRecordDto, LinkRecordEntity> {}
