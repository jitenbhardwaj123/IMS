package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.SnapshotTextDataDto;
import com.oak.entity.SnapshotTextDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface SnapshotTextDataMapper extends DtoAndEntityMapper<SnapshotTextDataDto, SnapshotTextDataEntity> {}
