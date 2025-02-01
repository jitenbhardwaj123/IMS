package com.oak.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.LineChartData;
import com.oak.entity.AssetEntity;
import com.oak.entity.WidgetEntity;

@Validated
public interface SnapshotLineChartWidgetDataService {
	List<LineChartData> read(
			@IsNotNull("Widget Id") 
			@EntityExists(WidgetEntity.class) 
			Long widgetId,

			@IsNotNull("Asset Id") 
			@EntityExists(AssetEntity.class) 
			Long assetId,
			
			FilterSelectionDto filter);
}
