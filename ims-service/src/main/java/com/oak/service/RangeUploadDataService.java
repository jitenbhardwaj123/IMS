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
import com.oak.dto.RangeUploadDataDto;
import com.oak.entity.RangeUploadDataEntity;
import com.oak.validator.DuplicateRangeUploadData;

@Validated
public interface RangeUploadDataService extends SearchService<RangeUploadDataDto, RangeUploadDataEntity, Long>,
		CrudService<RangeUploadDataDto, RangeUploadDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	RangeUploadDataDto create(@IsNotNull("RangeUploadData") @Valid @DuplicateRangeUploadData RangeUploadDataDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	RangeUploadDataDto update(@IsNotNull("RangeUploadData") @Valid @DuplicateRangeUploadData RangeUploadDataDto dto,
			@IsNotNull("RangeUploadData Id") @EntityExists(RangeUploadDataEntity.class) Long id);

	RangeUploadDataDto read(@IsNotNull("RangeUploadData Id") @EntityExists(RangeUploadDataEntity.class) Long id);

	void delete(@IsNotNull("RangeUploadData Id") @EntityExists(RangeUploadDataEntity.class) Long id);

	List<RangeUploadDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate);

	List<RangeUploadDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<RangeUploadDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
}
