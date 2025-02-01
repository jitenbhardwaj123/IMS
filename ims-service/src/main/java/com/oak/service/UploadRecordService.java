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
import com.oak.dto.UploadRecordDto;
import com.oak.entity.UploadRecordEntity;

@Validated
public interface UploadRecordService extends SearchService<UploadRecordDto, UploadRecordEntity, Long>,
		CrudService<UploadRecordDto, UploadRecordEntity, Long> {
	
	@Validated(value = { Default.class, CreateValidationGroup.class })
	UploadRecordDto create(@IsNotNull("UploadRecord") @Valid UploadRecordDto dto);
	
	@Validated
	UploadRecordDto create(@NotEmptyAndNoNullElements("File Resources") List<MultipartFile> multipartFiles);

	@Validated(value = { Default.class, UpdateValidationGroup.class })
	UploadRecordDto update(@IsNotNull("UploadRecord") @Valid UploadRecordDto dto,
			@IsNotNull("UploadRecord Id") @EntityExists(UploadRecordEntity.class) Long id);

	UploadRecordDto read(@IsNotNull("UploadRecord Id") @EntityExists(UploadRecordEntity.class) Long id);
	
	List<FileResourceDto> getFileResources(@IsNotNull("UploadRecord Id") @EntityExists(UploadRecordEntity.class) Long id);

	void delete(@IsNotNull("UploadRecord Id") @EntityExists(UploadRecordEntity.class) Long id);
}
