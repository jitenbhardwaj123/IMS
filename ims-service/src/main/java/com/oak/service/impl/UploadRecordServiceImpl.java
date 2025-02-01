package com.oak.service.impl;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.FileResourceDto;
import com.oak.dto.UploadRecordDto;
import com.oak.entity.UploadRecordEntity;
import com.oak.mapper.UploadRecordMapper;
import com.oak.repository.UploadRecordRepository;
import com.oak.service.FileResourceService;
import com.oak.service.UploadRecordService;

@Service
@Transactional
public class UploadRecordServiceImpl implements UploadRecordService {
	private final Logger logger;
	private final UploadRecordRepository repository;
	private final FileResourceService fileResourceService;
	private final UploadRecordMapper mapper;

	@Autowired
	public UploadRecordServiceImpl(Logger logger, UploadRecordRepository repository, FileResourceService fileResourceService, UploadRecordMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.fileResourceService = fileResourceService;
		this.mapper = mapper;
	}

	@Override
	public UploadRecordDto create(UploadRecordDto dto) {
		return commonCreate(dto);
	}
	
	@Override
	public UploadRecordDto create(List<MultipartFile> multipartFiles) {
		List<FileResourceDto> fileResources = fileResourceService.create(multipartFiles.stream().map(FileResourceUtils::fromMultiPartFile).collect(toList()));
		UploadRecordDto dto = new UploadRecordDto();
		dto.setFileResourceIds(fileResources.stream().map(FileResourceDto::getId).collect(toSet()));
		return commonCreate(dto);
	}

	@Override
	public UploadRecordDto update(UploadRecordDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public UploadRecordDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public List<FileResourceDto> getFileResources(Long id) {
		List<FileResourceDto> fileResources = null;
		Optional<UploadRecordEntity> uploadRecord = repository.findById(id);
		if (uploadRecord.isPresent()) {
			fileResources = fileResourceService.read(new ArrayList<Long>(uploadRecord.get().getFileResourceIds()));
		} else {
			fileResources = emptyList();
		}
		
		return fileResources;
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<UploadRecordDto, UploadRecordEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<UploadRecordEntity, Long> getRepository() {
		return repository;
	}
}
