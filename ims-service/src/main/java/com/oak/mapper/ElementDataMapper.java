package com.oak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.dto.ElementDataDto;
import com.oak.entity.RangeBooleanDataEntity;
import com.oak.entity.RangeDecimalDataEntity;
import com.oak.entity.RangeLinkDataEntity;
import com.oak.entity.RangeTextDataEntity;
import com.oak.entity.RangeUploadDataEntity;
import com.oak.entity.SnapshotBooleanDataEntity;
import com.oak.entity.SnapshotDecimalDataEntity;
import com.oak.entity.SnapshotLinkDataEntity;
import com.oak.entity.SnapshotTextDataEntity;
import com.oak.entity.SnapshotUploadDataEntity;

@Mapper(config = MapStructConfigMapper.class)
public interface ElementDataMapper {
	@Mapping(source = "reading", target = "decimalReading")
	ElementDataDto toElementData(RangeDecimalDataEntity entity);
	
	@Mapping(source = "reading", target = "textReading")
	ElementDataDto toElementData(RangeTextDataEntity entity);
	
	@Mapping(source = "reading", target = "booleanReading")
	ElementDataDto toElementData(RangeBooleanDataEntity entity);
	
	ElementDataDto toElementData(RangeLinkDataEntity entity);

	ElementDataDto toElementData(RangeUploadDataEntity entity);
	
	@Mapping(source = "reading", target = "decimalReading")
	ElementDataDto toElementData(SnapshotDecimalDataEntity entity);
	
	@Mapping(source = "reading", target = "textReading")
	ElementDataDto toElementData(SnapshotTextDataEntity entity);
	
	@Mapping(source = "reading", target = "booleanReading")
	ElementDataDto toElementData(SnapshotBooleanDataEntity entity);
	
	ElementDataDto toElementData(SnapshotLinkDataEntity entity);

	ElementDataDto toElementData(SnapshotUploadDataEntity entity);
}
