package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.FileResourceDto;
import com.oak.entity.FileResourceEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface FileResourceMapper extends DtoAndEntityMapper<FileResourceDto, FileResourceEntity> {}
