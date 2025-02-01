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
import com.oak.dto.SnapshotBooleanDataDto;
import com.oak.entity.SnapshotBooleanDataEntity;
import com.oak.validator.DuplicateSnapshotBooleanData;

@Validated
public interface SnapshotBooleanDataService
		extends SearchService<SnapshotBooleanDataDto, SnapshotBooleanDataEntity, Long>,
		CrudService<SnapshotBooleanDataDto, SnapshotBooleanDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	SnapshotBooleanDataDto create(@IsNotNull("SnapshotBooleanData") @Valid @DuplicateSnapshotBooleanData SnapshotBooleanDataDto dto);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	void create(@NotEmptyAndNoNullElements("Data") @Valid List<SnapshotBooleanDataDto> dtos);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void update(@NotEmptyAndNoNullElements("Data") @Valid List<SnapshotBooleanDataDto> dtos);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	SnapshotBooleanDataDto update(@IsNotNull("SnapshotBooleanData") @Valid @DuplicateSnapshotBooleanData SnapshotBooleanDataDto dto,
			@IsNotNull("SnapshotBooleanData Id") @EntityExists(SnapshotBooleanDataEntity.class) Long id);

	SnapshotBooleanDataDto read(
			@IsNotNull("SnapshotBooleanData Id") @EntityExists(SnapshotBooleanDataEntity.class) Long id);

	void delete(@IsNotNull("SnapshotBooleanData Id") @EntityExists(SnapshotBooleanDataEntity.class) Long id);

	List<SnapshotBooleanDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Reading Date") Instant readingDate);

	List<SnapshotBooleanDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<SnapshotBooleanDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
	
	List<SnapshotBooleanDataEntity> getData(long assetId, long sourceId, long elementId, @NotEmptyAndNoNullElements("Reading Dates") List<Instant> readingDates);

	void deleteByElementIdIn(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds);

	void deleteByElementIdInAndAssetIdAndReadingDate(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, @IsNotNull("Reading Date") Instant readingDate);
	
	void deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, @IsNotNull("Range Start") Instant rangeStart, Instant rangeEnd);
	
	List<SnapshotBooleanDataDto> findClosestReading(long assetId, long elementId, @IsNotNull("Reading date") Instant readingDate);
}
