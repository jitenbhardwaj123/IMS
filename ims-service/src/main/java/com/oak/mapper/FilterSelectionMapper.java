package com.oak.mapper;

import org.mapstruct.Mapper;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.dto.FilterSelectionDto;

@Mapper(config = MapStructConfigMapper.class)
public interface FilterSelectionMapper {
    FilterSelectionDto copy(FilterSelectionDto original);
}
