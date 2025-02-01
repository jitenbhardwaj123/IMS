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
import com.oak.dto.RangeBooleanDataDto;
import com.oak.entity.RangeBooleanDataEntity;
import com.oak.validator.DuplicateRangeBooleanData;

@Validated
public interface RangeBooleanDataService extends SearchService<RangeBooleanDataDto, RangeBooleanDataEntity, Long>,
		CrudService<RangeBooleanDataDto, RangeBooleanDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	RangeBooleanDataDto create(@IsNotNull("RangeBooleanData") @Valid @DuplicateRangeBooleanData RangeBooleanDataDto dto);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	void create(@NotEmptyAndNoNullElements("Data") @Valid List<RangeBooleanDataDto> dtos);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void update(@NotEmptyAndNoNullElements("Data") @Valid List<RangeBooleanDataDto> dtos);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	RangeBooleanDataDto update(@IsNotNull("RangeBooleanData") @Valid @DuplicateRangeBooleanData RangeBooleanDataDto dto,
			@IsNotNull("RangeBooleanData Id") @EntityExists(RangeBooleanDataEntity.class) Long id);

	RangeBooleanDataDto read(@IsNotNull("RangeBooleanData Id") @EntityExists(RangeBooleanDataEntity.class) Long id);

	void delete(@IsNotNull("RangeBooleanData Id") @EntityExists(RangeBooleanDataEntity.class) Long id);

	List<RangeBooleanDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate);

	List<RangeBooleanDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<RangeBooleanDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
	
	List<RangeBooleanDataEntity> getData(long assetId, long sourceId, long elementId, @NotEmptyAndNoNullElements("Start Dates") List<Instant> startDates);
	
	List<RangeBooleanDataDto> findApplicableForDate(long elementId, long assetId, Instant checkDate);
}
