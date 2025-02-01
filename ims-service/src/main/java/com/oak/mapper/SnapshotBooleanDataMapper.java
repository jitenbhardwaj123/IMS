package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.SnapshotBooleanDataDto;
import com.oak.entity.SnapshotBooleanDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface SnapshotBooleanDataMapper extends DtoAndEntityMapper<SnapshotBooleanDataDto, SnapshotBooleanDataEntity> {}
