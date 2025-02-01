package com.oak.service;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.validation.annotation.Validated;

import com.oak.common.rest.service.CrudService;
import com.oak.common.rest.service.SearchService;
import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.LinkRecordDto;
import com.oak.entity.LinkRecordEntity;

@Validated
public interface LinkRecordService extends SearchService<LinkRecordDto, LinkRecordEntity, Long>,
		CrudService<LinkRecordDto, LinkRecordEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	LinkRecordDto create(@IsNotNull("LinkRecord") @Valid LinkRecordDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	LinkRecordDto update(@IsNotNull("LinkRecord") @Valid LinkRecordDto dto,
			@IsNotNull("LinkRecord Id") @EntityExists(LinkRecordEntity.class) Long id);

	LinkRecordDto read(@IsNotNull("LinkRecord Id") @EntityExists(LinkRecordEntity.class) Long id);

	void delete(@IsNotNull("LinkRecord Id") @EntityExists(LinkRecordEntity.class) Long id);
}
