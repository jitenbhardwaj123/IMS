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
import com.oak.dto.AssetTypeDto;
import com.oak.entity.AssetTypeEntity;

@Validated
public interface AssetTypeService
		extends SearchService<AssetTypeDto, AssetTypeEntity, Long>, CrudService<AssetTypeDto, AssetTypeEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	AssetTypeDto create(@IsNotNull("Asset Type") @Valid AssetTypeDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	AssetTypeDto update(@IsNotNull("Asset Type") @Valid AssetTypeDto dto,
			@IsNotNull("Asset Type Id") @EntityExists(AssetTypeEntity.class) Long id);

	AssetTypeDto read(@IsNotNull("Asset Type Id") @EntityExists(AssetTypeEntity.class) Long id);

	void delete(@IsNotNull("Asset Type Id") @EntityExists(AssetTypeEntity.class) Long id);
}
