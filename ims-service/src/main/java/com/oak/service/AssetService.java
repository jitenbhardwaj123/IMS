package com.oak.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.validation.annotation.Validated;

import com.oak.common.rest.service.CrudService;
import com.oak.common.rest.service.SearchService;
import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.AssetDto;
import com.oak.entity.AssetEntity;

@Validated
public interface AssetService
		extends SearchService<AssetDto, AssetEntity, Long>, CrudService<AssetDto, AssetEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	AssetDto create(@IsNotNull("Asset") @Valid AssetDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	AssetDto update(@IsNotNull("Asset") @Valid AssetDto dto,
			@IsNotNull("Asset Id") @EntityExists(AssetEntity.class) Long id);

	AssetDto read(@IsNotNull("Asset Id") @EntityExists(AssetEntity.class) Long id);

	List<AssetDto> readRootAssets();

	void delete(@IsNotNull("Asset Id") @EntityExists(AssetEntity.class) Long id);
	
	List<AssetDto> getAssetsByName(@IsNotBlank("Asset Name") String name);
	List<AssetDto> getAssetsByIds(@NotEmptyAndNoNullElements("Asset Ids") List<Long> assetIds);
}
