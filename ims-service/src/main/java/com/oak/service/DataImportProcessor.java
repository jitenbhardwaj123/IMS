package com.oak.service;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.validation.annotation.Validated;

import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.dto.FileResourceDto;
import com.oak.entity.WidgetEntity;

@Validated
public interface DataImportProcessor {
	
	Pair<String, byte[]> getTemplate(@EntityExists(WidgetEntity.class) long widgetId);

	@Validated(value = { Default.class, CreateValidationGroup.class })
	void importWidgetData(
			@EntityExists(WidgetEntity.class) 
			long widgetId, 
			
			@IsNotBlank("Client Timezone") 
			String timezone,
			
			@IsNotNull("FileResource") 
			@Valid 
			FileResourceDto widgetData);
}
