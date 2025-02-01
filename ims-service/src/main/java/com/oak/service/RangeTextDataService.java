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
import com.oak.dto.RangeTextDataDto;
import com.oak.entity.RangeTextDataEntity;
import com.oak.validator.DuplicateRangeTextData;

@Validated
public interface RangeTextDataService extends SearchService<RangeTextDataDto, RangeTextDataEntity, Long>,
		CrudService<RangeTextDataDto, RangeTextDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	RangeTextDataDto create(@IsNotNull("RangeTextData") @Valid @DuplicateRangeTextData RangeTextDataDto dto);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	void create(@NotEmptyAndNoNullElements("Data") @Valid List<RangeTextDataDto> dtos);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void update(@NotEmptyAndNoNullElements("Data") @Valid List<RangeTextDataDto> dtos);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	RangeTextDataDto update(@IsNotNull("RangeTextData") @Valid @DuplicateRangeTextData RangeTextDataDto dto,
			@IsNotNull("RangeTextData Id") @EntityExists(RangeTextDataEntity.class) Long id);

	RangeTextDataDto read(@IsNotNull("RangeTextData Id") @EntityExists(RangeTextDataEntity.class) Long id);

	void delete(@IsNotNull("RangeTextData Id") @EntityExists(RangeTextDataEntity.class) Long id);

	List<RangeTextDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate);

	List<RangeTextDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<RangeTextDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
	
	List<RangeTextDataEntity> getData(long assetId, long sourceId, long elementId, @NotEmptyAndNoNullElements("Start Dates") List<Instant> startDates);
	
	List<RangeTextDataDto> findApplicableForDate(long elementId, long assetId, Instant checkDate);
}
