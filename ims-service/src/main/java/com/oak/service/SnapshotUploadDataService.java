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
import com.oak.dto.SnapshotUploadDataDto;
import com.oak.entity.SnapshotUploadDataEntity;
import com.oak.validator.DuplicateSnapshotUploadData;

@Validated
public interface SnapshotUploadDataService extends SearchService<SnapshotUploadDataDto, SnapshotUploadDataEntity, Long>,
		CrudService<SnapshotUploadDataDto, SnapshotUploadDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	SnapshotUploadDataDto create(@IsNotNull("SnapshotUploadData") @Valid @DuplicateSnapshotUploadData SnapshotUploadDataDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	SnapshotUploadDataDto update(@IsNotNull("SnapshotUploadData") @Valid @DuplicateSnapshotUploadData SnapshotUploadDataDto dto,
			@IsNotNull("SnapshotUploadData Id") @EntityExists(SnapshotUploadDataEntity.class) Long id);

	SnapshotUploadDataDto read(
			@IsNotNull("SnapshotUploadData Id") @EntityExists(SnapshotUploadDataEntity.class) Long id);

	void delete(@IsNotNull("SnapshotUploadData Id") @EntityExists(SnapshotUploadDataEntity.class) Long id);

	List<SnapshotUploadDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Reading Date") Instant readingDate);

	List<SnapshotUploadDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<SnapshotUploadDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
}
