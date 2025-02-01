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
import com.oak.dto.RangeLinkDataDto;
import com.oak.entity.RangeLinkDataEntity;
import com.oak.validator.DuplicateRangeLinkData;

@Validated
public interface RangeLinkDataService extends SearchService<RangeLinkDataDto, RangeLinkDataEntity, Long>,
		CrudService<RangeLinkDataDto, RangeLinkDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	RangeLinkDataDto create(@IsNotNull("RangeLinkData") @Valid @DuplicateRangeLinkData RangeLinkDataDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	RangeLinkDataDto update(@IsNotNull("RangeLinkData") @Valid @DuplicateRangeLinkData RangeLinkDataDto dto,
			@IsNotNull("RangeLinkData Id") @EntityExists(RangeLinkDataEntity.class) Long id);

	RangeLinkDataDto read(@IsNotNull("RangeLinkData Id") @EntityExists(RangeLinkDataEntity.class) Long id);

	void delete(@IsNotNull("RangeLinkData Id") @EntityExists(RangeLinkDataEntity.class) Long id);

	List<RangeLinkDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate);

	List<RangeLinkDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<RangeLinkDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
}
