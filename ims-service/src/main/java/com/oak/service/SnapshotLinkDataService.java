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
import com.oak.dto.SnapshotLinkDataDto;
import com.oak.entity.SnapshotLinkDataEntity;
import com.oak.validator.DuplicateSnapshotLinkData;

@Validated
public interface SnapshotLinkDataService extends SearchService<SnapshotLinkDataDto, SnapshotLinkDataEntity, Long>,
		CrudService<SnapshotLinkDataDto, SnapshotLinkDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	SnapshotLinkDataDto create(@IsNotNull("SnapshotLinkData") @Valid @DuplicateSnapshotLinkData SnapshotLinkDataDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	SnapshotLinkDataDto update(@IsNotNull("SnapshotLinkData") @Valid @DuplicateSnapshotLinkData SnapshotLinkDataDto dto,
			@IsNotNull("SnapshotLinkData Id") @EntityExists(SnapshotLinkDataEntity.class) Long id);

	SnapshotLinkDataDto read(@IsNotNull("SnapshotLinkData Id") @EntityExists(SnapshotLinkDataEntity.class) Long id);

	void delete(@IsNotNull("SnapshotLinkData Id") @EntityExists(SnapshotLinkDataEntity.class) Long id);

	List<SnapshotLinkDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Reading Date") Instant readingDate);

	List<SnapshotLinkDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<SnapshotLinkDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
}
