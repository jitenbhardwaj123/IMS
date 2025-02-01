package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.SnapshotUploadDataDto;
import com.oak.entity.SnapshotUploadDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface SnapshotUploadDataMapper extends DtoAndEntityMapper<SnapshotUploadDataDto, SnapshotUploadDataEntity> {}
