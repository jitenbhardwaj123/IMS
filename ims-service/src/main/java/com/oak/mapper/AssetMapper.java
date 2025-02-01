package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.AssetDto;
import com.oak.entity.AssetEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface AssetMapper extends DtoAndEntityMapper<AssetDto, AssetEntity> {}
