package com.oak.service;

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
import com.oak.dto.WidgetDto;
import com.oak.entity.WidgetEntity;
import com.oak.validator.WidgetUsed;

@Validated
public interface WidgetService
		extends SearchService<WidgetDto, WidgetEntity, Long>, CrudService<WidgetDto, WidgetEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	WidgetDto create(@IsNotNull("Widget") @Valid WidgetDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	WidgetDto update(@IsNotNull("Widget") @Valid WidgetDto dto,
			@IsNotNull("Widget Id") @EntityExists(WidgetEntity.class) Long id);

	WidgetDto read(@IsNotNull("Widget Id") @EntityExists(WidgetEntity.class) Long id);
	
	List<WidgetDto> getWidgetsForAssetTypes(
			@NotEmptyAndNoNullElements("Widget Asset Types") List<Long> assetTypeIds);

	void delete(
			@IsNotNull("Widget Id") 
			@EntityExists(WidgetEntity.class) 
			@WidgetUsed
			Long id);
}
