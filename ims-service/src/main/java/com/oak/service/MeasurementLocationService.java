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
import com.oak.dto.MeasurementLocationDto;
import com.oak.entity.MeasurementLocationEntity;

@Validated
public interface MeasurementLocationService
		extends SearchService<MeasurementLocationDto, MeasurementLocationEntity, Long>,
		CrudService<MeasurementLocationDto, MeasurementLocationEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	MeasurementLocationDto create(@IsNotNull("MeasurementLocation") @Valid MeasurementLocationDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	MeasurementLocationDto update(@IsNotNull("MeasurementLocation") @Valid MeasurementLocationDto dto,
			@IsNotNull("MeasurementLocation Id") @EntityExists(MeasurementLocationEntity.class) Long id);

	MeasurementLocationDto read(
			@IsNotNull("MeasurementLocation Id") @EntityExists(MeasurementLocationEntity.class) Long id);

	void delete(@IsNotNull("MeasurementLocation Id") @EntityExists(MeasurementLocationEntity.class) Long id);
}
