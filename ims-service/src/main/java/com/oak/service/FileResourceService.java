package com.oak.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.oak.common.rest.service.CrudService;
import com.oak.common.rest.service.SearchService;
import com.oak.common.validation.constraint.EntityExists;
import com.oak.common.validation.constraint.IsNotNull;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;
import com.oak.common.validation.group.CreateValidationGroup;
import com.oak.common.validation.group.UpdateValidationGroup;
import com.oak.dto.FileResourceDto;
import com.oak.entity.FileResourceEntity;

@Validated
public interface FileResourceService extends SearchService<FileResourceDto, FileResourceEntity, Long>,
		CrudService<FileResourceDto, FileResourceEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	FileResourceDto create(@IsNotNull("FileResource") @Valid FileResourceDto dto);
	
	@Validated
	List<FileResourceDto> createFromFiles(@NotEmptyAndNoNullElements("File Resources") List<MultipartFile> multipartFiles);
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	List<FileResourceDto> create(@NotEmptyAndNoNullElements("File Resources") @Valid List<FileResourceDto> dtos);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	FileResourceDto update(@IsNotNull("FileResource") @Valid FileResourceDto dto,
			@IsNotNull("FileResource Id") @EntityExists(FileResourceEntity.class) Long id);

	FileResourceDto read(@IsNotNull("FileResource Id") @EntityExists(FileResourceEntity.class) Long id);
	List<FileResourceDto> read(@NotEmptyAndNoNullElements("File Resource ids") @Valid List<Long> ids);

	void delete(@IsNotNull("FileResource Id") @EntityExists(FileResourceEntity.class) Long id);
}
