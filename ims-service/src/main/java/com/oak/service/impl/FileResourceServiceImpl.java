package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.FileResourceDto;
import com.oak.entity.FileResourceEntity;
import com.oak.mapper.FileResourceMapper;
import com.oak.repository.FileResourceRepository;
import com.oak.service.FileResourceService;

// TODO: Whitelist accepted file types
@Service
@Transactional
public class FileResourceServiceImpl implements FileResourceService {
	private final Logger logger;
	private final FileResourceRepository repository;
	private final FileResourceMapper mapper;

	@Autowired
	public FileResourceServiceImpl(Logger logger, FileResourceRepository repository, FileResourceMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public FileResourceDto create(FileResourceDto dto) {
		return commonCreate(dto);
	}

	@Override
	public List<FileResourceDto> createFromFiles(List<MultipartFile> multipartFiles) {
		return this.create(multipartFiles.stream().map(FileResourceUtils::fromMultiPartFile).collect(toList()));
	}

	@Override
	public List<FileResourceDto> create(List<FileResourceDto> dtos) {
		return dtos.stream().map(this::create).collect(toList());
	}

	@Override
	public FileResourceDto update(FileResourceDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public FileResourceDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public List<FileResourceDto> read(List<Long> ids) {
		return repository.findAllById(ids).stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<FileResourceDto, FileResourceEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<FileResourceEntity, Long> getRepository() {
		return repository;
	}
}
