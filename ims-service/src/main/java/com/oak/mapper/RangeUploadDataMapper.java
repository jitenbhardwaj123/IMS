package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.RangeUploadDataDto;
import com.oak.entity.RangeUploadDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface RangeUploadDataMapper extends DtoAndEntityMapper<RangeUploadDataDto, RangeUploadDataEntity> {}
