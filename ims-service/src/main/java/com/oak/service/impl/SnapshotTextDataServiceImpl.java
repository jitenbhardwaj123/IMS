package com.oak.service.impl;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.common.rest.mapping.DtoAndEntityMapper;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.SnapshotTextDataDto;
import com.oak.entity.SnapshotTextDataEntity;
import com.oak.mapper.SnapshotTextDataMapper;
import com.oak.repository.SnapshotTextDataRepository;
import com.oak.service.SnapshotTextDataService;

@Service
@Transactional
public class SnapshotTextDataServiceImpl implements SnapshotTextDataService {
	private final Logger logger;
	private final JdbcTemplate jdbcTemplate;
	private final SnapshotTextDataRepository repository;
	private final SnapshotTextDataMapper mapper;
	
	@Value("${sql.in-clause.maxsize}")
	private int partitionSizeForInClause;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int jdbcBatchSize;

	@Autowired
	public SnapshotTextDataServiceImpl(Logger logger, JdbcTemplate jdbcTemplate, SnapshotTextDataRepository repository, SnapshotTextDataMapper mapper) {
		this.logger = logger;
		this.jdbcTemplate = jdbcTemplate;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public SnapshotTextDataDto create(SnapshotTextDataDto dto) {
		return commonCreate(dto);
	}

	@Override
	public void create(List<SnapshotTextDataDto> dtos) {
		repository.batchCreate(jdbcTemplate, dtos, jdbcBatchSize);
	}
	
	@Override
	public void update(List<SnapshotTextDataDto> dtos) {
		repository.batchUpdate(jdbcTemplate, dtos, jdbcBatchSize);
	}

	@Override
	public SnapshotTextDataDto update(SnapshotTextDataDto dto, Long id) {
		return commonUpdate(dto, id);
	}

	@Override
	public SnapshotTextDataDto read(Long id) {
		return commonRead(id);
	}

	@Override
	public void delete(Long id) {
		commonDelete(id);
	}

	@Override
	public DtoAndEntityMapper<SnapshotTextDataDto, SnapshotTextDataEntity> getMapper() {
		return mapper;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public CommonCustomJpaRepository<SnapshotTextDataEntity, Long> getRepository() {
		return repository;
	}

	@Override
	public List<SnapshotTextDataEntity> getData(List<Long> elementIds, List<Long> assetIds) {
		return repository.findByElementIdInAndAssetIdIn(elementIds, assetIds);
	}

	@Override
	public List<SnapshotTextDataEntity> getData(FilterSelectionDto filter) {
		return repository.findByElementIdInAndAssetIdInAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(filter.getElementIds(),
				filter.getAssetIds(), filter.getRangeStart(), filter.getRangeEnd());
	}

	@Override
	public List<SnapshotTextDataEntity> getData(List<Long> elementIds, long assetId, long sourceId,
			Instant readingDate) {
		return repository.findByElementIdInAndAssetIdAndSourceIdAndReadingDate(elementIds, assetId, sourceId, readingDate);
	}

	@Override
	public List<SnapshotTextDataEntity> getData(long assetId, long sourceId, long elementId, List<Instant> readingDates) {
		return ListUtils.partition(readingDates, partitionSizeForInClause)
		 .stream()
		 .map(batchedDates -> repository.findByAssetIdAndSourceIdAndElementIdAndReadingDateIn(assetId, sourceId, elementId, batchedDates))
		 .flatMap(Collection::stream)
		 .collect(toList());
	}

	@Override
	public void deleteByElementIdIn(List<Long> elementIds) {
		repository.deleteByElementIdIn(elementIds);
	}

	@Override
	public void deleteByElementIdInAndAssetIdAndReadingDate(List<Long> elementIds, long assetId, Instant readingDate) {
		repository.deleteByElementIdInAndAssetIdAndReadingDate(elementIds, assetId, readingDate);
	}

	@Override
	public void deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			List<Long> elementIds, long assetId, Instant rangeStart, Instant rangeEnd) {
		if (rangeEnd == null) {
			repository.deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqual(elementIds, assetId, rangeStart);
		} else {
			repository.deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(elementIds, assetId, rangeStart, rangeEnd);
		}
	}

	@Override
	public List<SnapshotTextDataDto> findClosestReading(long assetId, long elementId, Instant readingDate) {
		return repository.findClosestReading(assetId, elementId, readingDate).stream().map(this::map).collect(toList());
	}
}
