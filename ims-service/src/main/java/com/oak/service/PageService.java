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
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.PageDto;
import com.oak.entity.AssetTypeEntity;
import com.oak.entity.PageEntity;

@Validated
public interface PageService
		extends SearchService<PageDto, PageEntity, Long>, CrudService<PageDto, PageEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	PageDto create(@IsNotNull("Page") @Valid PageDto dto);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	PageDto update(@IsNotNull("Page") @Valid PageDto dto,
			@IsNotNull("Page Id") @EntityExists(PageEntity.class) Long id);

	PageDto read(@IsNotNull("Page Id") @EntityExists(PageEntity.class) Long id);

	List<PageDto> readRootPages();
	
	List<PageDto> readRootPages(@EntityExists(AssetTypeEntity.class) long assetTypeId);
	
	PageDto readDashboardPage(@EntityExists(AssetTypeEntity.class) long assetTypeId);
	
	List<PageDto> readRootDashboardPages();

	void delete(@IsNotNull("Page Id") @EntityExists(PageEntity.class) Long id);
	
	List<PageDto> getPagesByName(@IsNotBlank("Page Name") String name);
}
