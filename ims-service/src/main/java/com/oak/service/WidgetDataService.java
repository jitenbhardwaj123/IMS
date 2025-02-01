package com.oak.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.WidgetDataDto;
import com.oak.entity.AssetEntity;
import com.oak.entity.WidgetEntity;

@Validated
public interface WidgetDataService {
	List<WidgetDataDto> read(
			@IsNotNull("Widget Id") 
			@EntityExists(WidgetEntity.class) 
			Long widgetId,

			@IsNotNull("Asset Id") 
			@EntityExists(AssetEntity.class) 
			Long assetId,
			
			FilterSelectionDto filter);
}
