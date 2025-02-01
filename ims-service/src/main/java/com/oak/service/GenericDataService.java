package com.oak.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.validation.annotation.Validated;

import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotEmpty;
import com.oak.common.validation.constraint.NotNullAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.GenericDataDto;
import com.oak.entity.AssetEntity;
import com.oak.entity.SourceEntity;
import com.oak.entity.WidgetEntity;
import com.oak.validator.DuplicateGenericData;

@Validated
public interface GenericDataService {
	List<GenericDataDto> readWidgetDataRow(
			@EntityExists(WidgetEntity.class) long widgetId,
			@EntityExists(AssetEntity.class) long assetId, 
			@EntityExists(SourceEntity.class) long sourceId, 
			long readingDate, 
			Long endDate);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void create(
			@NotNullAndNoNullElements("Data") 
			@IsNotEmpty("Data")
			@DuplicateGenericData
			List<@Valid GenericDataDto> data);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	void update(
			@NotNullAndNoNullElements("Data") 
			@IsNotEmpty("Data")
			@DuplicateGenericData
			List<@Valid GenericDataDto> data);
	
	@Validated
	void save(
			@NotNullAndNoNullElements("Data") 
			@IsNotEmpty("Data")
			@DuplicateGenericData
			List<@Valid GenericDataDto> data);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	void delete(
			@NotNullAndNoNullElements("Data") 
			@IsNotEmpty("Data")
			List<@Valid GenericDataDto> data);
}
