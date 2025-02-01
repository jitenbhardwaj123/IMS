package com.oak.service.impl;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.dto.ElementDataDto;
import com.oak.dto.ElementDto;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.WidgetDataDto;
import com.oak.dto.WidgetDataKey;
import com.oak.dto.WidgetDto;
import com.oak.dto.WidgetElementDto;
import com.oak.entity.RangeBooleanDataEntity;
import com.oak.entity.RangeDecimalDataEntity;
import com.oak.entity.RangeLinkDataEntity;
import com.oak.entity.RangeTextDataEntity;
import com.oak.entity.RangeUploadDataEntity;
import com.oak.entity.SnapshotBooleanDataEntity;
import com.oak.entity.SnapshotDecimalDataEntity;
import com.oak.entity.SnapshotLinkDataEntity;
import com.oak.entity.SnapshotTextDataEntity;
import com.oak.entity.SnapshotUploadDataEntity;
import com.oak.enums.MeasurementType;
import com.oak.enums.WidgetType;
import com.oak.mapper.ElementDataMapper;
import com.oak.mapper.FilterSelectionMapper;
import com.oak.mapper.WidgetDataKeyMapper;
import com.oak.service.ElementService;
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
import com.oak.service.WidgetDataService;
import com.oak.service.WidgetService;

@Service
@Transactional
public class WidgetDataServiceImpl implements WidgetDataService {
	@Autowired
	private Logger logger;
	
	@Autowired
	private WidgetService widgetService;
	
	@Autowired
	private ElementService elementService;
	
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
	private WidgetDataKeyMapper keyMapper;
	
	@Autowired
	private ElementDataMapper elementDataMapper;
	
	@Autowired
	private FilterSelectionMapper filterSelectionMapper;
	
	@Autowired
	private FilterSelectionProcessor filterSelectionProcessor;

	@Override
	public List<WidgetDataDto> read(Long widgetId, Long assetId, FilterSelectionDto filter) {
		logger.info("Get Widget Data [ widgetId: {}, assetId: {}, filter: {} ]", widgetId, assetId, filter);

		WidgetDto widget = this.widgetService.read(widgetId);
		final FilterSelectionDto updatedFilter = filterSelectionProcessor.apply(widget.getFilterCriteria(), filter, assetId);
		List<ElementDto> applicableElements = getApplicableElements(widget, updatedFilter);
		
		List<WidgetDataDto> widgetData = new ArrayList<>();
		TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetDataMap = new TreeMap<>();
		Arrays.stream(MeasurementType.values()).forEach(mType -> populateWidgetData(widget.getWidgetType(), applicableElements, widgetDataMap, mType, updatedFilter));
		widgetDataMap.forEach((key, elementData) -> widgetData.add(this.getDataItem(key, elementData, updatedFilter.getElementIds())));
		
		return widgetData ;
	}
	
	private List<ElementDto> getApplicableElements(WidgetDto widget, FilterSelectionDto filter) {
		if (filter.getElementIds().isEmpty()) {
			List<ElementDto> elements = widget.getElements().stream()
					.map(WidgetElementDto::getElement)
					.distinct()
					.collect(toList());
			filter.setElementIds(elements.stream().map(ElementDto::getId).collect(toList()));
			return elements;
		} else {
			return elementService.getElementsByIds(filter.getElementIds());
		}
	}
	
	private WidgetDataDto getDataItem(WidgetDataKey key, Map<Long, ElementDataDto> elementData,
			List<Long> applicableElements) {
		WidgetDataDto data = keyMapper.fromKey(key);
		data.setElementData(applicableElements.stream().map(elementData::get).collect(toList()));
		return data;
	}

	private void populateWidgetData(WidgetType widgetType, List<ElementDto> elements, TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData,
			MeasurementType measurementType, FilterSelectionDto filter) {
		List<Long> applicableElements = getElements(measurementType, elements);
		if (isNotEmpty(applicableElements)) {
			FilterSelectionDto applicableFilter = filterSelectionMapper.copy(filter);
			applicableFilter.setElementIds(applicableElements);
			switch (widgetType.getCategory()) {
				case RANGE:
					populateRangeWidgetData(widgetData, measurementType, applicableFilter);
					break;
				case SNAPSHOT:
					populateSnapshotWidgetData(widgetData, measurementType, applicableFilter);
					break;
				default:
					throw new RuntimeException("Found a new widget type in populateWidgetData : " + measurementType);
			}
		}
	}

	private void populateRangeWidgetData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData,
			MeasurementType measurementType, FilterSelectionDto filter) {
		switch (measurementType) {
			case DECIMAL:
				populateRangeDecimalData(widgetData, filter);
				break;
			case TEXT:
			case TEXT_OPTIONS:
				populateRangeTextData(widgetData, filter);
				break;
			case BOOLEAN_OPTIONS:
				populateRangeBooleanData(widgetData, filter);
				break;
			case LINK_GROUP:
				populateRangeLinkData(widgetData, filter);
				break;
			case FILE_GROUP:
				populateRangeUploadData(widgetData, filter);
				break;
			default:
				throw new RuntimeException("Found a new measurement type in populateRangeWidgetData : " + measurementType);
		}
	}

	private void populateSnapshotWidgetData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData,
			MeasurementType measurementType, FilterSelectionDto filter) {
		switch (measurementType) {
			case DECIMAL:
				populateSnapshotDecimalData(widgetData, filter);
				break;
			case TEXT:
			case TEXT_OPTIONS:
				populateSnapshotTextData(widgetData, filter);
				break;
			case BOOLEAN_OPTIONS:
				populateSnapshotBooleanData(widgetData, filter);
				break;
			case LINK_GROUP:
				populateSnapshotLinkData(widgetData, filter);
				break;
			case FILE_GROUP:
				populateSnapshotUploadData(widgetData, filter);
				break;
			default:
				throw new RuntimeException("Found a new measurement type in populateSnapshotWidgetData : " + measurementType);
		}
	}
	
	private void populateRangeDecimalData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<RangeDecimalDataEntity> elementData = rangeDecimalDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateRangeTextData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<RangeTextDataEntity> elementData = rangeTextDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateRangeBooleanData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<RangeBooleanDataEntity> elementData = rangeBooleanDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateRangeLinkData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<RangeLinkDataEntity> elementData = rangeLinkDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateRangeUploadData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<RangeUploadDataEntity> elementData = rangeUploadDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateSnapshotDecimalData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<SnapshotDecimalDataEntity> elementData = snapshotDecimalDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateSnapshotTextData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<SnapshotTextDataEntity> elementData = snapshotTextDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateSnapshotBooleanData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<SnapshotBooleanDataEntity> elementData = snapshotBooleanDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateSnapshotLinkData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<SnapshotLinkDataEntity> elementData = snapshotLinkDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}
	
	private void populateSnapshotUploadData(TreeMap<WidgetDataKey, Map<Long, ElementDataDto>> widgetData, FilterSelectionDto filter) {
		List<SnapshotUploadDataEntity> elementData = snapshotUploadDataService.getData(filter);
		elementData.forEach(e -> {
			WidgetDataKey key = keyMapper.toKey(e);
			ElementDataDto data = elementDataMapper.toElementData(e);
			Map<Long, ElementDataDto> elements = widgetData.getOrDefault(key, new HashMap<>());
			elements.put(data.getElementId(), data);
			widgetData.put(key, elements);
		});
	}

	private List<Long> getElements(MeasurementType measurementType, List<ElementDto> applicableElements) {
		return applicableElements.stream()
				.filter(e -> measurementType == e.getMeasurementType())
				.map(ElementDto::getId)
				.distinct()
				.collect(toList());
	}
}