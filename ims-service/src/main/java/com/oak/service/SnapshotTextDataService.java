package com.oak.service;

import java.time.Instant;
import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.validation.annotation.Validated;

import com.oak.common.rest.service.CrudService;
import com.oak.common.rest.service.SearchService;
import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.SnapshotTextDataDto;
import com.oak.entity.SnapshotTextDataEntity;
import com.oak.validator.DuplicateSnapshotTextData;

@Validated
public interface SnapshotTextDataService extends SearchService<SnapshotTextDataDto, SnapshotTextDataEntity, Long>,
		CrudService<SnapshotTextDataDto, SnapshotTextDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	SnapshotTextDataDto create(@IsNotNull("SnapshotTextData") @Valid @DuplicateSnapshotTextData SnapshotTextDataDto dto);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	void create(@NotEmptyAndNoNullElements("Data") @Valid List<SnapshotTextDataDto> dtos);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void update(@NotEmptyAndNoNullElements("Data") @Valid List<SnapshotTextDataDto> dtos);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	SnapshotTextDataDto update(@IsNotNull("SnapshotTextData") @Valid @DuplicateSnapshotTextData SnapshotTextDataDto dto,
			@IsNotNull("SnapshotTextData Id") @EntityExists(SnapshotTextDataEntity.class) Long id);

	SnapshotTextDataDto read(@IsNotNull("SnapshotTextData Id") @EntityExists(SnapshotTextDataEntity.class) Long id);

	void delete(@IsNotNull("SnapshotTextData Id") @EntityExists(SnapshotTextDataEntity.class) Long id);

	List<SnapshotTextDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Reading Date") Instant readingDate);

	List<SnapshotTextDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<SnapshotTextDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
	
	List<SnapshotTextDataEntity> getData(long assetId, long sourceId, long elementId, @NotEmptyAndNoNullElements("Reading Dates") List<Instant> readingDates);

	void deleteByElementIdIn(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds);

	void deleteByElementIdInAndAssetIdAndReadingDate(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, @IsNotNull("Reading Date") Instant readingDate);
	
	void deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, @IsNotNull("Range Start") Instant rangeStart, Instant rangeEnd);
	
	List<SnapshotTextDataDto> findClosestReading(long assetId, long elementId, @IsNotNull("Reading date") Instant readingDate);
}
