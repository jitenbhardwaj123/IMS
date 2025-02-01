package com.oak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.dto.GenericDataDto;
import com.oak.dto.RangeBooleanDataDto;
import com.oak.dto.RangeDecimalDataDto;
import com.oak.dto.RangeLinkDataDto;
import com.oak.dto.RangeTextDataDto;
import com.oak.dto.RangeUploadDataDto;
import com.oak.dto.SnapshotBooleanDataDto;
import com.oak.dto.SnapshotDecimalDataDto;
import com.oak.dto.SnapshotLinkDataDto;
import com.oak.dto.SnapshotTextDataDto;
import com.oak.dto.SnapshotUploadDataDto;
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
public interface GenericDataMapper {
	
	@Mapping(source = "readingDate", target = "startDate")
	@Mapping(source = "decimalReading", target = "reading")
	RangeDecimalDataDto toRangeDecimalDto(GenericDataDto data);
	
	@Mapping(source = "readingDate", target = "startDate")
	@Mapping(source = "textReading", target = "reading")
	RangeTextDataDto toRangeTextDto(GenericDataDto data);
	
	@Mapping(source = "readingDate", target = "startDate")
	@Mapping(source = "booleanReading", target = "reading")
	RangeBooleanDataDto toRangeBooleanDto(GenericDataDto data);
	
	@Mapping(source = "readingDate", target = "startDate")
	RangeLinkDataDto toRangeLinkDto(GenericDataDto data);
	
	@Mapping(source = "readingDate", target = "startDate")
	RangeUploadDataDto toRangeUploadDto(GenericDataDto data);
	
	@Mapping(source = "decimalReading", target = "reading")
	SnapshotDecimalDataDto toSnapshotDecimalDto(GenericDataDto data);
	
	@Mapping(source = "textReading", target = "reading")
	SnapshotTextDataDto toSnapshotTextDto(GenericDataDto data);
	
	@Mapping(source = "booleanReading", target = "reading")
	SnapshotBooleanDataDto toSnapshotBooleanDto(GenericDataDto data);
	
	SnapshotLinkDataDto toSnapshotLinkDto(GenericDataDto data);
	
	SnapshotUploadDataDto toSnapshotUploadDto(GenericDataDto data);
	
	@Mapping(source = "startDate", target = "readingDate")
	@Mapping(source = "reading", target = "decimalReading")
	GenericDataDto fromRangeDecimalEntity(RangeDecimalDataEntity data);
	
	@Mapping(source = "startDate", target = "readingDate")
	@Mapping(source = "reading", target = "textReading")
	GenericDataDto fromRangeTextEntity(RangeTextDataEntity data);
	
	@Mapping(source = "startDate", target = "readingDate")
	@Mapping(source = "reading", target = "booleanReading")
	GenericDataDto fromRangeBooleanEntity(RangeBooleanDataEntity data);
	
	@Mapping(source = "startDate", target = "readingDate")
	GenericDataDto fromRangeLinkEntity(RangeLinkDataEntity data);
	
	@Mapping(source = "startDate", target = "readingDate")
	GenericDataDto fromRangeUploadEntity(RangeUploadDataEntity data);
	
	@Mapping(source = "reading", target = "decimalReading")
	GenericDataDto fromSnapshotDecimalEntity(SnapshotDecimalDataEntity data);
	
	@Mapping(source = "reading", target = "textReading")
	GenericDataDto fromSnapshotTextEntity(SnapshotTextDataEntity data);
	
	@Mapping(source = "reading", target = "booleanReading")
	GenericDataDto fromSnapshotBooleanEntity(SnapshotBooleanDataEntity data);
	
	GenericDataDto fromSnapshotLinkEntity(SnapshotLinkDataEntity data);
	
	GenericDataDto fromSnapshotUploadEntity(SnapshotUploadDataEntity data);
}
