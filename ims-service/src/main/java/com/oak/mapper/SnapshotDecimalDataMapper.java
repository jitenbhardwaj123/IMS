package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.SnapshotDecimalDataDto;
import com.oak.entity.SnapshotDecimalDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface SnapshotDecimalDataMapper extends DtoAndEntityMapper<SnapshotDecimalDataDto, SnapshotDecimalDataEntity> {}
