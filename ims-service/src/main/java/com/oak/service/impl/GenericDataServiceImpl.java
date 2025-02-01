package com.oak.service.impl;

import static com.oak.enums.DataCategory.RANGE;
import static com.oak.enums.DataCategory.SNAPSHOT;
import static java.lang.String.format;
import static java.time.Instant.ofEpochMilli;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.dto.ElementDto;
import com.oak.dto.GenericDataDto;
import com.oak.dto.WidgetDto;
import com.oak.dto.WidgetElementDto;
import com.oak.enums.MeasurementType;
import com.oak.mapper.GenericDataMapper;
import com.oak.service.GenericDataService;
import com.oak.service.RangeBooleanDataService;
import com.oak.service.RangeDecimalDataService;
import com.oak.service.RangeLinkDataService;
import com.oak.service.RangeTextDataService;
import com.oak.service.RangeUploadDataService;
import com.oak.service.SnapshotBooleanDataService;
import com.oak.service.SnapshotDecimalDataService;
import com.oak.service.SnapshotLinkDataService;
import com.oak.service.SnapshotTextDataService;
import com.oak.service.SnapshotUploadDataService;
import com.oak.service.WidgetService;

@Service
@Transactional
public class GenericDataServiceImpl implements GenericDataService {
	@Autowired
	private Logger logger;
	
	@Autowired
	private RangeDecimalDataService rangeDecimalDataService;
	
	@Autowired
	private RangeTextDataService rangeTextDataService;
	
	@Autowired
	private RangeBooleanDataService rangeBooleanDataService;
	
	@Autowired
	private RangeUploadDataService rangeUploadDataService;
	
	@Autowired
	private RangeLinkDataService rangeLinkDataService;
	
	@Autowired
	private SnapshotDecimalDataService snapshotDecimalDataService;
	
	@Autowired
	private SnapshotTextDataService snapshotTextDataService;
	
	@Autowired
	private SnapshotBooleanDataService snapshotBooleanDataService;
	
	@Autowired
	private SnapshotUploadDataService snapshotUploadDataService;
	
	@Autowired
	private SnapshotLinkDataService snapshotLinkDataService;
	
	@Autowired
	private WidgetService widgetService;
	
	@Autowired
	private GenericDataMapper mapper;

	@Override
	public List<GenericDataDto> readWidgetDataRow(long widgetId, long assetId, long sourceId,
			long readingDate, Long endDate) {
		WidgetDto widget = this.widgetService.read(widgetId);
		List<ElementDto> elements = widget.getElements().stream().map(WidgetElementDto::getElement).collect(toList());
		if (RANGE == widget.getWidgetType().getCategory()) {
			return stream(MeasurementType.values())
					.map(mt -> this.getRangeData(elements, mt, widgetId, assetId, sourceId, readingDate, endDate))
					.flatMap(List::stream).collect(toList());
		} else {
			return stream(MeasurementType.values())
					.map(mt -> this.getSnapshotData(elements, mt, widgetId, assetId, sourceId, readingDate))
					.flatMap(List::stream).collect(toList());
		}
	}

	@Override
	public void create(List<GenericDataDto> data) {
		logger.info("Creating data: {}", data);
		
		data.stream().forEach(this::createData);
	}

	@Override
	public void update(List<GenericDataDto> data) {
		logger.info("Updating data: {}", data);
		
		data.stream().forEach(this::updateData);
	}

	@Override
	public void save(List<GenericDataDto> data) {
		logger.info("Saving data: {}", data);
		
		List<GenericDataDto> newData = data.stream().filter(d -> d.getId() == null).collect(toList());
		List<GenericDataDto> existingData = data.stream().filter(d -> d.getId() != null).collect(toList());
		this.update(existingData);
		if (! newData.isEmpty()) {
			this.create(newData);
		}
	}

	@Override
	public void delete(List<GenericDataDto> data) {
		logger.info("Deleting data [ id: {} ]", data);
		
		data.stream().forEach(this::deleteData);
	}
	
	private void createData(GenericDataDto data) {
		if (RANGE == data.getDataCategory()) {
			this.createRangeData(data);
		} else if (SNAPSHOT == data.getDataCategory()) {
			this.createSnapshotData(data);
		} else {
			throw new RuntimeException(format("Un-supported Data Category [ %s ] found.", data.getDataCategory()));
		}
	}
	
	private void createRangeData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
			case DECIMAL:
				rangeDecimalDataService.create(mapper.toRangeDecimalDto(data));
				break;
			case TEXT:
			case TEXT_OPTIONS:
				rangeTextDataService.create(mapper.toRangeTextDto(data));
				break;
			case BOOLEAN_OPTIONS:
				rangeBooleanDataService.create(mapper.toRangeBooleanDto(data));
				break;
			case LINK_GROUP:
				rangeLinkDataService.create(mapper.toRangeLinkDto(data));
				break;
			case FILE_GROUP:
				rangeUploadDataService.create(mapper.toRangeUploadDto(data));
				break;
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", data.getMeasurementType()));
		}
	}
	
	private void createSnapshotData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
			case DECIMAL:
				snapshotDecimalDataService.create(mapper.toSnapshotDecimalDto(data));
				break;
			case TEXT:
			case TEXT_OPTIONS:
				snapshotTextDataService.create(mapper.toSnapshotTextDto(data));
				break;
			case BOOLEAN_OPTIONS:
				snapshotBooleanDataService.create(mapper.toSnapshotBooleanDto(data));
				break;
			case LINK_GROUP:
				snapshotLinkDataService.create(mapper.toSnapshotLinkDto(data));
				break;
			case FILE_GROUP:
				snapshotUploadDataService.create(mapper.toSnapshotUploadDto(data));
				break;
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", data.getMeasurementType()));
		}
	}
	
	private List<GenericDataDto> getSnapshotData(List<ElementDto> elements, MeasurementType measurementType, long widgetId, long assetId, long sourceId,
			long readingDate) {
		List<Long> measurementElementIds = elements.stream()
				.filter(e -> measurementType == e.getMeasurementType())
				.map(ElementDto::getId)
				.collect(toList());
		if (measurementElementIds.isEmpty()) {
			return emptyList();
		}
		switch (measurementType) {
			case DECIMAL:
				return snapshotDecimalDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate))
						.stream().map(mapper::fromSnapshotDecimalEntity)
						.collect(toList());
			case TEXT:
			case TEXT_OPTIONS:
				return snapshotTextDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate))
						.stream().map(mapper::fromSnapshotTextEntity)
						.collect(toList());
			case BOOLEAN_OPTIONS:
				return snapshotBooleanDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate))
						.stream().map(mapper::fromSnapshotBooleanEntity)
						.collect(toList());
			case LINK_GROUP:
				return snapshotLinkDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate))
						.stream().map(mapper::fromSnapshotLinkEntity)
						.collect(toList());
			case FILE_GROUP:
				return snapshotUploadDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate))
						.stream().map(mapper::fromSnapshotUploadEntity)
						.collect(toList());
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", measurementType));
		}
	}
	
	private List<GenericDataDto> getRangeData(List<ElementDto> elements, MeasurementType measurementType, long widgetId, long assetId, long sourceId,
			long readingDate, Long endDate) {
		List<Long> measurementElementIds = elements.stream()
				.filter(e -> measurementType == e.getMeasurementType())
				.map(ElementDto::getId)
				.collect(toList());
		if (measurementElementIds.isEmpty()) {
			return emptyList();
		}
		switch (measurementType) {
			case DECIMAL:
				return rangeDecimalDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate), endDate != null ? ofEpochMilli(endDate) : null)
						.stream().map(mapper::fromRangeDecimalEntity)
						.collect(toList());
			case TEXT:
			case TEXT_OPTIONS:
				return rangeTextDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate), endDate != null ? ofEpochMilli(endDate) : null)
						.stream().map(mapper::fromRangeTextEntity)
						.collect(toList());
			case BOOLEAN_OPTIONS:
				return rangeBooleanDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate), endDate != null ? ofEpochMilli(endDate) : null)
						.stream().map(mapper::fromRangeBooleanEntity)
						.collect(toList());
			case LINK_GROUP:
				return rangeLinkDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate), endDate != null ? ofEpochMilli(endDate) : null)
						.stream().map(mapper::fromRangeLinkEntity)
						.collect(toList());
			case FILE_GROUP:
				return rangeUploadDataService
						.getData(measurementElementIds, assetId, sourceId, ofEpochMilli(readingDate), endDate != null ? ofEpochMilli(endDate) : null)
						.stream().map(mapper::fromRangeUploadEntity)
						.collect(toList());
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", measurementType));
		}
	}
	
	private void updateData(GenericDataDto data) {
		if (RANGE == data.getDataCategory()) {
			this.updateRangeData(data);
		} else if (SNAPSHOT == data.getDataCategory()) {
			this.updateSnapshotData(data);
		} else {
			throw new RuntimeException(format("Un-supported Data Category [ %s ] found.", data.getDataCategory()));
		}
	}
	
	private void updateRangeData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
			case DECIMAL:
				rangeDecimalDataService.update(mapper.toRangeDecimalDto(data), data.getId());
				break;
			case TEXT:
			case TEXT_OPTIONS:
				rangeTextDataService.update(mapper.toRangeTextDto(data), data.getId());
				break;
			case BOOLEAN_OPTIONS:
				rangeBooleanDataService.update(mapper.toRangeBooleanDto(data), data.getId());
				break;
			case LINK_GROUP:
				rangeLinkDataService.update(mapper.toRangeLinkDto(data), data.getId());
				break;
			case FILE_GROUP:
				rangeUploadDataService.update(mapper.toRangeUploadDto(data), data.getId());
				break;
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", data.getMeasurementType()));
		}
	}
	
	private void updateSnapshotData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
			case DECIMAL:
				snapshotDecimalDataService.update(mapper.toSnapshotDecimalDto(data), data.getId());
				break;
			case TEXT:
			case TEXT_OPTIONS:
				snapshotTextDataService.update(mapper.toSnapshotTextDto(data), data.getId());
				break;
			case BOOLEAN_OPTIONS:
				snapshotBooleanDataService.update(mapper.toSnapshotBooleanDto(data), data.getId());
				break;
			case LINK_GROUP:
				snapshotLinkDataService.update(mapper.toSnapshotLinkDto(data), data.getId());
				break;
			case FILE_GROUP:
				snapshotUploadDataService.update(mapper.toSnapshotUploadDto(data), data.getId());
				break;
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", data.getMeasurementType()));
		}
	}
	
	private void deleteData(GenericDataDto data) {
		if (RANGE == data.getDataCategory()) {
			this.deleteRangeData(data);
		} else if (SNAPSHOT == data.getDataCategory()) {
			this.deleteSnapshotData(data);
		} else {
			throw new RuntimeException(format("Un-supported Data Category [ %s ] found.", data.getDataCategory()));
		}
	}
	
	private void deleteRangeData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
			case DECIMAL:
				rangeDecimalDataService.delete(data.getId());
				break;
			case TEXT:
			case TEXT_OPTIONS:
				rangeTextDataService.delete(data.getId());
				break;
			case BOOLEAN_OPTIONS:
				rangeBooleanDataService.delete(data.getId());
				break;
			case LINK_GROUP:
				rangeLinkDataService.delete(data.getId());
				break;
			case FILE_GROUP:
				rangeUploadDataService.delete(data.getId());
				break;
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", data.getMeasurementType()));
		}
	}
	
	private void deleteSnapshotData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
			case DECIMAL:
				snapshotDecimalDataService.delete(data.getId());
				break;
			case TEXT:
			case TEXT_OPTIONS:
				snapshotTextDataService.delete(data.getId());
				break;
			case BOOLEAN_OPTIONS:
				snapshotBooleanDataService.delete(data.getId());
				break;
			case LINK_GROUP:
				snapshotLinkDataService.delete(data.getId());
				break;
			case FILE_GROUP:
				snapshotUploadDataService.delete(data.getId());
				break;
			default:
				throw new RuntimeException(format("Un-supported Measurement Type [ %s ] found.", data.getMeasurementType()));
		}
	}
}
