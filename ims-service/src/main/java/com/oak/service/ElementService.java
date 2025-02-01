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
import com.oak.dto.ElementDto;
import com.oak.entity.ElementEntity;

@Validated
public interface ElementService
		extends SearchService<ElementDto, ElementEntity, Long>, CrudService<ElementDto, ElementEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	ElementDto create(@IsNotNull("Element") @Valid ElementDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	ElementDto update(@IsNotNull("Element") @Valid ElementDto dto,
			@IsNotNull("Element Id") @EntityExists(ElementEntity.class) Long id);

	ElementDto read(@IsNotNull("Element Id") @EntityExists(ElementEntity.class) Long id);
	List<ElementDto> getElementsByIds(@NotEmptyAndNoNullElements("Element Ids") List<Long> elementIds);

	void delete(@IsNotNull("Element Id") @EntityExists(ElementEntity.class) Long id);
}
