package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.SnapshotLinkDataDto;
import com.oak.entity.SnapshotLinkDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface SnapshotLinkDataMapper extends DtoAndEntityMapper<SnapshotLinkDataDto, SnapshotLinkDataEntity> {}
