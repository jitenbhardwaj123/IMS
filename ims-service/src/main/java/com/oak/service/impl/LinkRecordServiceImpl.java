package com.oak.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.LinkRecordDto;
import com.oak.entity.LinkRecordEntity;
import com.oak.mapper.LinkRecordMapper;
import com.oak.repository.LinkRecordRepository;
import com.oak.service.LinkRecordService;

@Service
@Transactional
public class LinkRecordServiceImpl implements LinkRecordService {
	private final Logger logger;
	private final LinkRecordRepository repository;
	private final LinkRecordMapper mapper;

	@Autowired
	public LinkRecordServiceImpl(Logger logger, LinkRecordRepository repository, LinkRecordMapper mapper) {
		this.logger = logger;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public LinkRecordDto create(LinkRecordDto dto) {
		return commonCreate(dto);
	}

	@Override
	public LinkRecordDto update(LinkRecordDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public LinkRecordDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<LinkRecordDto, LinkRecordEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<LinkRecordEntity, Long> getRepository() {
		return repository;
	}
}
