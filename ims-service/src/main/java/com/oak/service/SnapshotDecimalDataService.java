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
import com.oak.dto.SnapshotDecimalDataDto;
import com.oak.entity.SnapshotDecimalDataEntity;
import com.oak.validator.DuplicateSnapshotDecimalData;

@Validated
public interface SnapshotDecimalDataService
		extends SearchService<SnapshotDecimalDataDto, SnapshotDecimalDataEntity, Long>,
		CrudService<SnapshotDecimalDataDto, SnapshotDecimalDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	SnapshotDecimalDataDto create(@IsNotNull("SnapshotDecimalData") @Valid @DuplicateSnapshotDecimalData SnapshotDecimalDataDto dto);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	void create(@NotEmptyAndNoNullElements("Data") @Valid List<SnapshotDecimalDataDto> dtos);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void update(@NotEmptyAndNoNullElements("Data") @Valid List<SnapshotDecimalDataDto> dtos);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	SnapshotDecimalDataDto update(@IsNotNull("SnapshotDecimalData") @Valid @DuplicateSnapshotDecimalData SnapshotDecimalDataDto dto,
			@IsNotNull("SnapshotDecimalData Id") @EntityExists(SnapshotDecimalDataEntity.class) Long id);

	SnapshotDecimalDataDto read(
			@IsNotNull("SnapshotDecimalData Id") @EntityExists(SnapshotDecimalDataEntity.class) Long id);

	void delete(@IsNotNull("SnapshotDecimalData Id") @EntityExists(SnapshotDecimalDataEntity.class) Long id);

	List<SnapshotDecimalDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Reading Date") Instant readingDate);

	List<SnapshotDecimalDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<SnapshotDecimalDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
	
	List<SnapshotDecimalDataEntity> getData(long assetId, long sourceId, long elementId, @NotEmptyAndNoNullElements("Reading Dates") List<Instant> readingDates);

	void deleteByElementIdIn(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds);

	void deleteByElementIdInAndAssetIdAndReadingDate(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, @IsNotNull("Reading Date") Instant readingDate);
	
	void deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, @IsNotNull("Range Start") Instant rangeStart, Instant rangeEnd);
	
	List<SnapshotDecimalDataDto> findClosestReading(long assetId, long elementId, @IsNotNull("Reading date") Instant readingDate);
}
