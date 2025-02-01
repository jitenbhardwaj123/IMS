package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.UploadRecordDto;
import com.oak.entity.UploadRecordEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface UploadRecordMapper extends DtoAndEntityMapper<UploadRecordDto, UploadRecordEntity> {}
