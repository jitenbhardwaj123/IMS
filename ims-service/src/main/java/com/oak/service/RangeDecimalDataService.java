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
import com.oak.dto.RangeDecimalDataDto;
import com.oak.entity.RangeDecimalDataEntity;
import com.oak.validator.DuplicateRangeDecimalData;

@Validated
public interface RangeDecimalDataService extends SearchService<RangeDecimalDataDto, RangeDecimalDataEntity, Long>,
		CrudService<RangeDecimalDataDto, RangeDecimalDataEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	RangeDecimalDataDto create(@IsNotNull("RangeDecimalData") @Valid @DuplicateRangeDecimalData RangeDecimalDataDto dto);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	void create(@NotEmptyAndNoNullElements("Data") @Valid List<RangeDecimalDataDto> dtos);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void update(@NotEmptyAndNoNullElements("Data") @Valid List<RangeDecimalDataDto> dtos);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	RangeDecimalDataDto update(@IsNotNull("RangeDecimalData") @Valid @DuplicateRangeDecimalData RangeDecimalDataDto dto,
			@IsNotNull("RangeDecimalData Id") @EntityExists(RangeDecimalDataEntity.class) Long id);

	RangeDecimalDataDto read(@IsNotNull("RangeDecimalData Id") @EntityExists(RangeDecimalDataEntity.class) Long id);

	void delete(@IsNotNull("RangeDecimalData Id") @EntityExists(RangeDecimalDataEntity.class) Long id);

	List<RangeDecimalDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, long assetId, long sourceId, @IsNotNull("Start Date") Instant startDate, Instant endDate);

	List<RangeDecimalDataEntity> getData(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds, @NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);

	List<RangeDecimalDataEntity> getData(@IsNotNull("Filter") FilterSelectionDto filter);
	
	List<RangeDecimalDataEntity> getData(long assetId, long sourceId, long elementId, @NotEmptyAndNoNullElements("Start Dates") List<Instant> startDates);
	
	List<RangeDecimalDataDto> findApplicableForDate(long elementId, long assetId, Instant checkDate);
}
