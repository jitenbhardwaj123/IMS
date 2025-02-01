package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.MeasurementLocationDto;
import com.oak.entity.MeasurementLocationEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface MeasurementLocationMapper extends DtoAndEntityMapper<MeasurementLocationDto, MeasurementLocationEntity> {}
