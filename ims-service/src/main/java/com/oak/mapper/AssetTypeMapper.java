package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.AssetTypeDto;
import com.oak.entity.AssetTypeEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface AssetTypeMapper extends DtoAndEntityMapper<AssetTypeDto, AssetTypeEntity> {}
