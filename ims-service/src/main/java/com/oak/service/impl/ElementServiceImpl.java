package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.ElementDto;
import com.oak.entity.ElementEntity;
import com.oak.mapper.ElementMapper;
import com.oak.repository.ElementRepository;
import com.oak.service.ElementService;

@Service
@Transactional
public class ElementServiceImpl implements ElementService {
	private final Logger logger;
	private final ElementRepository repository;
	private final ElementMapper mapper;

	@Autowired
	public ElementServiceImpl(Logger logger, ElementRepository repository, ElementMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public ElementDto create(ElementDto dto) {
		return commonCreate(dto);
	}

	@Override
	public ElementDto update(ElementDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public ElementDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public List<ElementDto> getElementsByIds(List<Long> elementIds) {
		return repository.findAllById(elementIds).stream().map(mapper::toDto).collect(toList());
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<ElementDto, ElementEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<ElementEntity, Long> getRepository() {
		return repository;
	}
}
