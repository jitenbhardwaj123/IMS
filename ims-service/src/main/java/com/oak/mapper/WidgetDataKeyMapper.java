package com.oak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.oak.common.mappers.mapstruct.MapStructConfigMapper;
import com.oak.dto.WidgetDataDto;
import com.oak.dto.WidgetDataKey;
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
public interface WidgetDataKeyMapper {
	@Mapping(source = "startDate", target = "readingDate")
	WidgetDataKey toKey(RangeDecimalDataEntity entity);

	@Mapping(source = "startDate", target = "readingDate")
	WidgetDataKey toKey(RangeTextDataEntity entity);

	@Mapping(source = "startDate", target = "readingDate")
	WidgetDataKey toKey(RangeBooleanDataEntity entity);

	@Mapping(source = "startDate", target = "readingDate")
	WidgetDataKey toKey(RangeLinkDataEntity entity);

	@Mapping(source = "startDate", target = "readingDate")
	WidgetDataKey toKey(RangeUploadDataEntity entity);
	
	WidgetDataKey toKey(SnapshotDecimalDataEntity entity);

	WidgetDataKey toKey(SnapshotTextDataEntity entity);

	WidgetDataKey toKey(SnapshotBooleanDataEntity entity);

	WidgetDataKey toKey(SnapshotLinkDataEntity entity);

	WidgetDataKey toKey(SnapshotUploadDataEntity entity);

	WidgetDataDto fromKey(WidgetDataKey key);
}
