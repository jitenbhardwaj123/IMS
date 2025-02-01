package com.oak.service.impl;

import static com.oak.enums.DataCategory.RANGE;
import static com.oak.enums.MeasurementType.BOOLEAN_OPTIONS;
import static com.oak.enums.MeasurementType.DECIMAL;
import static com.oak.enums.MeasurementType.TEXT;
import static com.oak.enums.MeasurementType.TEXT_OPTIONS;
import static com.oak.utils.DateFormatUtils.getJavaFormatFromMomentJsFormat;
import static com.oak.utils.DateFormatUtils.hasTimeComponent;
import static java.lang.String.format;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.common.exception.model.UploadDatasheetException;
import com.oak.dto.AssetDto;
import com.oak.dto.ElementDto;
import com.oak.dto.FileResourceDto;
import com.oak.dto.MeasurementBooleanOptionDto;
import com.oak.dto.RangeBooleanDataDto;
import com.oak.dto.RangeDecimalDataDto;
import com.oak.dto.RangeTextDataDto;
import com.oak.dto.SnapshotBooleanDataDto;
import com.oak.dto.SnapshotDecimalDataDto;
import com.oak.dto.SnapshotTextDataDto;
import com.oak.dto.SourceDto;
import com.oak.dto.UnitDto;
import com.oak.dto.WidgetDto;
import com.oak.dto.WidgetElementDto;
import com.oak.entity.RangeBooleanDataEntity;
import com.oak.entity.RangeDecimalDataEntity;
import com.oak.entity.RangeTextDataEntity;
import com.oak.entity.SnapshotBooleanDataEntity;
import com.oak.entity.SnapshotDecimalDataEntity;
import com.oak.entity.SnapshotTextDataEntity;
import com.oak.enums.MeasurementType;
import com.oak.enums.WidgetType;
import com.oak.excel.ExcelCell;
import com.oak.excel.ExcelColumn;
import com.oak.excel.ExcelProcessor;
import com.oak.excel.ExcelRow;
import com.oak.excel.ExcelSheetData;
import com.oak.service.AssetService;
import com.oak.service.DataImportProcessor;
import com.oak.service.RangeBooleanDataService;
import com.oak.service.RangeDecimalDataService;
import com.oak.service.RangeTextDataService;
import com.oak.service.SnapshotBooleanDataService;
import com.oak.service.SnapshotDecimalDataService;
import com.oak.service.SnapshotTextDataService;
import com.oak.service.SourceService;
import com.oak.service.UnitService;
import com.oak.service.WidgetService;

@Service
@Transactional
public class DataImportProcessorImpl implements DataImportProcessor {
	private static final String MANDATORY_MESSAGE = "%s is mandatory";
	private static final String DUPLICATE_MESSAGE = "Multiple rows [ Rows: %s ] found for the Asset [ %s ] & %s [ %s ]";
	private static final String INVALID_OPTION_MESSAGE = "[ %s ] is not a valid option for %s";
	private static final String ASSET_NAME_HEADER = "Asset";
	private static final String UNIT_HEADER = "Unit";
	private static final List<MeasurementType> ALLOWED_TYPES = asList(DECIMAL, TEXT, TEXT_OPTIONS, BOOLEAN_OPTIONS);
	
	@Autowired
	private Logger logger;
	
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private SourceService sourceService;
	
	@Autowired
	private RangeDecimalDataService rangeDecimalDataService;
	
	@Autowired
	private RangeTextDataService rangeTextDataService;
	
	@Autowired
	private RangeBooleanDataService rangeBooleanDataService;
	
	@Autowired
	private SnapshotDecimalDataService snapshotDecimalDataService;
	
	@Autowired
	private SnapshotTextDataService snapshotTextDataService;
	
	@Autowired
	private SnapshotBooleanDataService snapshotBooleanDataService;
	
	@Autowired
	private WidgetService widgetService;
	
	@Autowired
	private UnitService unitService;
	
	@Override
	public Pair<String, byte[]> getTemplate(long widgetId) {
		logger.info("Generating template for [ widget id: {} ]", widgetId);
		
		WidgetDto widget = widgetService.read(widgetId);
		List<ExcelColumn> headers = getHeaders(widget);
		String widgetName = widget.getName();

		return Pair.of(widgetName, new ExcelProcessor().generateWorkbook(widgetName, headers));
	}
	
	private List<ExcelColumn> getHeaders(WidgetDto widget) {
		// check if there are any elements available to import
		List<WidgetElementDto> elements = getAllowedImportableWidgetElements(widget);
		if (elements.isEmpty()) {
			throw new RuntimeException("Error generating template: No eligible elements found!");
		}
		
		// check if there is one manual source
		long sourceCount = sourceService.search(null).stream().filter(SourceDto::isManualSource).count();
		if (sourceCount != 1) {
			throw new RuntimeException("Error generating template: No/Multiple Manual Sources found!");
		}
		
		List<ExcelColumn> headers = new ArrayList<>();
		headers.add(new ExcelColumn(ASSET_NAME_HEADER, true));
		headers.add(new ExcelColumn(widget.getReadingDateLabel() + " \n" + widget.getDateFormat(), true));
		if (RANGE == widget.getWidgetType().getCategory()) {
			headers.add(new ExcelColumn(widget.getEndDateLabel() + " \n" + widget.getDateFormat()));
		}
		elements.forEach(we -> {
				List<String> options = null;
				if (TEXT_OPTIONS == we.getElement().getMeasurementType()) {
					options = we.getElement().getTextOptions();
				} else if (BOOLEAN_OPTIONS == we.getElement().getMeasurementType()) {
					options = we.getElement().getBooleanOption().getOptions();
				}
				headers.add(new ExcelColumn(we.getName(), we.isMandatory(), options));
				if (we.getElement().getUnitType() != null) {
					headers.add(new ExcelColumn(UNIT_HEADER, we.isMandatory(), unitService.readByUnitType(we.getElement().getUnitType().getId())
							.stream().map(UnitDto::getName).collect(toList())));
				}
			});
		return headers;
	}

	private SourceDto getSource(ExcelSheetData widgetData) {
		SourceDto source = null;
		List<SourceDto> manualSources = sourceService.search(null).stream().filter(SourceDto::isManualSource).collect(toList());
		if (manualSources.isEmpty()) {
			widgetData.addError("Manual Source is not configured in the system. Please contact your System Administrator");
		} else if (manualSources.size() > 1) {
			widgetData.addError(format("Multiple Manual Sources found [ %s ]. Please contact your System Administrator", 
					manualSources.stream().map(SourceDto::getName).collect(joining(","))));
		} else {
			source = manualSources.get(0);
		}
		return source;
	}
	
	private AssetDto parseAsset(ExcelCell cell, Map<String, List<AssetDto>> cachedNames) {
		String assetName = cell.getRawValue();
		
		if (isBlank(assetName)) {
			cell.addError(format(MANDATORY_MESSAGE, ASSET_NAME_HEADER));						
		} else {
			List<AssetDto> matchingAssets = cachedNames.containsKey(assetName) ?
					cachedNames.get(assetName) : assetService.getAssetsByName(assetName);
			cachedNames.put(assetName, matchingAssets);
			if (isEmpty(matchingAssets)) {
				cell.addError(format("No %s found by name [ %s ]", ASSET_NAME_HEADER, assetName));
			} else if (matchingAssets.size() > 1) {
				cell.addError(format("Multiple Assets found by name [ %s ]", assetName));
			} else {
				cell.setParsedValue(matchingAssets.get(0));
				return matchingAssets.get(0);
			}
		}
		return null;
	}

	private Instant parseInstant(boolean mandatory, ExcelCell cell, String label, String dateFormat, String timezone) {
		String rawValue = cell.getRawValue();

		if (isNotBlank(rawValue)) {
			try {

				String javaFormatFromMomentJsFormat = getJavaFormatFromMomentJsFormat(dateFormat);
				DateTimeFormatter dateTimeFormatter = ofPattern(javaFormatFromMomentJsFormat);
				Instant instant = null;
				if (hasTimeComponent(javaFormatFromMomentJsFormat)) {
					LocalDateTime parse = parse(rawValue.replaceAll(" +", " "), dateTimeFormatter);
					instant = parse.atZone(TimeZone.getTimeZone(timezone).toZoneId()).toInstant();
				} else {
					LocalDate parse = LocalDate.parse(rawValue.replaceAll(" +", " "), dateTimeFormatter);
					instant = parse.atStartOfDay(TimeZone.getTimeZone(timezone).toZoneId()).toInstant();
				}
				cell.setParsedValue(instant);
				return instant;
			} catch (Exception e) {
				cell.addError(format("%s [ %s ] is invalid. Date should be of format [ %s ]", label, rawValue, dateFormat));
			}
		} else if (mandatory) {
			cell.addError(format(MANDATORY_MESSAGE, label));						
		}
		return null;
	}

	private BigDecimal parseBigDecimal(ExcelCell cell, boolean mandatory, String label) {
		if (isBlank(cell.getRawValue())) {
			if (mandatory) {
				cell.addError(format(MANDATORY_MESSAGE, label));
			}
		} else {
			try {
				BigDecimal decimalValue = getLocaleSpecificNumberFromString(cell.getRawValue());
				cell.setParsedValue(decimalValue);
				return decimalValue;
			} catch (Exception de) {
				cell.addError(format("%s reading [ %s ] is not a valid decimal reading", label, cell.getRawValue()));
			}
		}
		return null;
	}
	
	private BigDecimal getLocaleSpecificNumberFromString(String strValue) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		ParsePosition pos = new ParsePosition(0);
		Number parsed = formatter.parse(strValue, pos);
		if (pos.getIndex() != strValue.length() || pos.getErrorIndex() != -1) {
			throw new NumberFormatException(format("Error parsing %s to decimal", strValue));
		} else {
			return new BigDecimal(parsed.toString());
		}
	}

	private UnitDto parseUnit(boolean readingMandatory, Map<String, UnitDto> cachedNames, ExcelCell unitCell, ExcelCell readingCell, 
			String readingLabel) {
		if (isBlank(unitCell.getRawValue())) {
			if (readingMandatory) {
				unitCell.addError(format("%s %s is mandatory", readingLabel, UNIT_HEADER));
			} else if (isNotBlank(readingCell.getRawValue())) {
				unitCell.addError(format("%s %s must be provided along with %s reading", readingLabel, UNIT_HEADER, readingLabel));
			}
		} else {
			if (isBlank(readingCell.getRawValue()) && !readingMandatory) {
				unitCell.addError(format("%s %s [ %s ] should only be provided along with %s reading", readingLabel, UNIT_HEADER, unitCell.getRawValue(), readingLabel));
			}
			UnitDto unit = cachedNames.containsKey(unitCell.getRawValue()) ?
					cachedNames.get(unitCell.getRawValue()) : unitService.readByName(unitCell.getRawValue());
			cachedNames.put(unitCell.getRawValue(), unit);
			if (unit == null) {
				unitCell.addError(format("No %s %s found by name [ %s ]", readingLabel, UNIT_HEADER, unitCell.getRawValue()));
			} else {
				unitCell.setParsedValue(unit);
				return unit;
			}
		}
		return null;
	}
	
	private void parseString(ExcelCell cell, boolean mandatory, String label, List<String> validValues) {
		if (isBlank(cell.getRawValue())) {
			if (mandatory) {
				cell.addError(format(MANDATORY_MESSAGE, label));
			}
		} else {
			if (isNotEmpty(validValues) && !validValues.contains(cell.getRawValue())) {
				cell.addError(format(INVALID_OPTION_MESSAGE, cell.getRawValue(), label));
			} else {
				cell.setParsedValue(cell.getRawValue());
			}
		}
	}
	
	private void parseBoolean(ExcelCell cell, boolean mandatory, String label, MeasurementBooleanOptionDto booleanOption) {
		if (isBlank(cell.getRawValue())) {
			if (mandatory) {
				cell.addError(format(MANDATORY_MESSAGE, label));
			}
		} else {
			if (isNotEmpty(booleanOption.getOptions()) && !booleanOption.getOptions().contains(cell.getRawValue())) {
				cell.addError(format(INVALID_OPTION_MESSAGE, cell.getRawValue(), label));
			} else {
				cell.setParsedValue(booleanOption.getTrueOption().equals(cell.getRawValue()));
			}
		}
	}
	
	@Override
	public void importWidgetData(long widgetId, String timezone, FileResourceDto widgetFileResource) {
		try {
			logger.info("Import file [ name: {} ] for Widget [ id: {} ] ", widgetFileResource.getFileNameWithExtension(), widgetId);

			WidgetDto widget = widgetService.read(widgetId);
			WidgetType widgetType = widget.getWidgetType();

			List<Class<?>> cellTypes = getCellTypes(widget);
			List<WidgetElementDto> re = getAllowedImportableWidgetElements(widget);
			List<ElementDto> mandatoryElements = getAllowedImportableMandatoryElements(widget);
			
			ExcelSheetData widgetData = new ExcelProcessor().importReportData(widget.getName(), cellTypes, widgetFileResource.getRawData());
			
			SourceDto source = getSource(widgetData);
			Long sourceId = source != null ? source.getId() : null;
			
			Map<String, List<AssetDto>> assetNames = new HashMap<>();
			Map<String, UnitDto> unitNames = new HashMap<>();
			Map<Pair<String, String>, List<ExcelRow>> duplicates = new HashMap<>();
			
			widgetData.getRows().forEach(row -> {
				int i = 0;
				
				List<ExcelCell> rowData = row.getCells();
				parseAsset(rowData.get(i++), assetNames);
				parseInstant(true, rowData.get(i++), widget.getReadingDateLabel(), widget.getDateFormat(), timezone);
				if (RANGE == widgetType.getCategory()) {
					parseInstant(false, rowData.get(i++), widget.getEndDateLabel(), widget.getDateFormat(), timezone);
					validateDateRange(row, widget.getReadingDateLabel(), widget.getEndDateLabel());
				}

				// find duplicate rows
				if (isNoneBlank(rowData.get(0).getRawValue(), rowData.get(1).getRawValue())) {
					Pair<String, String> key = Pair.of(rowData.get(0).getRawValue(), rowData.get(1).getRawValue());
					duplicates.putIfAbsent(key, new ArrayList<>());
					duplicates.get(key).add(row);
				}
				
				for (int e = 0; e < re.size(); e++) {
					WidgetElementDto reportElement = re.get(e);
					ElementDto element = reportElement.getElement();
					ExcelCell readingCell = rowData.get(i + e);
					
					switch (element.getMeasurementType()) {
						case DECIMAL:
							parseBigDecimal(readingCell, mandatoryElements.contains(element), reportElement.getName());
							break;
						case TEXT:
							parseString(readingCell, mandatoryElements.contains(element), reportElement.getName(), null);
							break;
						case TEXT_OPTIONS:
							parseString(readingCell, mandatoryElements.contains(element), reportElement.getName(), element.getTextOptions());
							break;
						case BOOLEAN_OPTIONS:
							parseBoolean(readingCell, mandatoryElements.contains(element), reportElement.getName(), element.getBooleanOption());
							break;
						default:
							readingCell.addError(format("Import of %s data is not supported yet", element.getMeasurementType()));
							break;
					}
					
					if (element.getUnitType() != null) {
						i++;
						ExcelCell unitCell = rowData.get(i + e);
						parseUnit(mandatoryElements.contains(element), unitNames, unitCell, readingCell, reportElement.getName());
					}
				}
			});
			
			// report duplicates
			for (Entry<Pair<String, String>, List<ExcelRow>> entry: duplicates.entrySet()) {
				if (entry.getValue().size() > 1) {
					String duplicateRows = entry.getValue().stream().map(ExcelRow::getReference).collect(joining(", "));
					widgetData.addError(format(DUPLICATE_MESSAGE, duplicateRows, entry.getKey().getKey(), widget.getReadingDateLabel(), entry.getKey().getValue()));
				}
			}
			
			// report all errors
			if (widgetData.hasErrors()) {
				throw new UploadDatasheetException("Validation errors found while importing data!", widgetData.getCumulativeErrors());
			}
			
			// persist data if no errors
			if (RANGE == widgetType.getCategory()) {
				Map<Triple<Long, Long, Long>, List<RangeDecimalDataDto>> decimalData = new HashMap<>();
				Map<Triple<Long, Long, Long>, List<RangeTextDataDto>> textData = new HashMap<>();
				Map<Triple<Long, Long, Long>, List<RangeBooleanDataDto>> booleanData = new HashMap<>();
				widgetData.getRows().forEach(rowData -> {
					int i = 0;
					AssetDto asset = (AssetDto) rowData.getCells().get(i++).getParsedValue();
					Instant startDate = (Instant) rowData.getCells().get(i++).getParsedValue();
					Instant endDate = (Instant) rowData.getCells().get(i++).getParsedValue();
					for (int e = 0; e < re.size(); e++) {
						WidgetElementDto reportElement = re.get(e);
						ElementDto element = reportElement.getElement();
						Triple<Long, Long, Long> key = Triple.of(asset.getId(), sourceId, element.getId());
						Object reading = rowData.getCells().get(i + e).getParsedValue();
						UnitDto unit = null;
						if (element.getUnitType() != null) {
							i++;
							unit = (UnitDto) rowData.getCells().get(i + e).getParsedValue();
						}
						if (reading != null) {
							if (MeasurementType.DECIMAL == element.getMeasurementType()) {
								RangeDecimalDataDto dd = new RangeDecimalDataDto();
								dd.setAssetId(asset.getId());
								dd.setStartDate(startDate);
								dd.setEndDate(endDate);
								dd.setElementId(element.getId());
								dd.setSourceId(sourceId);
								dd.setReading((BigDecimal) reading);
								dd.setUnitId(unit != null ? unit.getId() : null);
								decimalData.putIfAbsent(key, new ArrayList<>());
								decimalData.get(key).add(dd);
							} else if (MeasurementType.TEXT == element.getMeasurementType() 
									|| MeasurementType.TEXT_OPTIONS == element.getMeasurementType()) {
								RangeTextDataDto dd = new RangeTextDataDto();
								dd.setAssetId(asset.getId());
								dd.setStartDate(startDate);
								dd.setEndDate(endDate);
								dd.setElementId(element.getId());
								dd.setSourceId(sourceId);
								dd.setReading((String) reading);
								textData.putIfAbsent(key, new ArrayList<>());
								textData.get(key).add(dd);
							} else if (MeasurementType.BOOLEAN_OPTIONS == element.getMeasurementType()) {
								RangeBooleanDataDto dd = new RangeBooleanDataDto();
								dd.setAssetId(asset.getId());
								dd.setStartDate(startDate);
								dd.setEndDate(endDate);
								dd.setElementId(element.getId());
								dd.setSourceId(sourceId);
								dd.setReading((Boolean) reading);
								booleanData.putIfAbsent(key, new ArrayList<>());
								booleanData.get(key).add(dd);
							}
						}
					}
				});
				
				List<RangeDecimalDataDto> decimalDataToCreate = new ArrayList<>();
				List<RangeDecimalDataDto> decimalDataToUpdate = new ArrayList<>();
				decimalData.entrySet().forEach(entry -> {
					Triple<Long, Long, Long> key = entry.getKey();
					List<Instant> existingDates = rangeDecimalDataService
							.getData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue().stream().map(RangeDecimalDataDto::getStartDate).collect(toList()))
							.stream().map(RangeDecimalDataEntity::getStartDate).collect(toList());
					entry.getValue().forEach(dto -> {
						if (existingDates.contains(dto.getStartDate())) {
							decimalDataToUpdate.add(dto);
						} else {
							decimalDataToCreate.add(dto);
						}
					});
				});
				
				List<RangeTextDataDto> textDataToCreate = new ArrayList<>();
				List<RangeTextDataDto> textDataToUpdate = new ArrayList<>();
				textData.entrySet().forEach(entry -> {
					Triple<Long, Long, Long> key = entry.getKey();
					List<Instant> existingDates = rangeTextDataService
							.getData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue().stream().map(RangeTextDataDto::getStartDate).collect(toList()))
							.stream().map(RangeTextDataEntity::getStartDate).collect(toList());
					entry.getValue().forEach(dto -> {
						if (existingDates.contains(dto.getStartDate())) {
							textDataToUpdate.add(dto);
						} else {
							textDataToCreate.add(dto);
						}
					});
				});
				
				List<RangeBooleanDataDto> booleanDataToCreate = new ArrayList<>();
				List<RangeBooleanDataDto> booleanDataToUpdate = new ArrayList<>();
				booleanData.entrySet().forEach(entry -> {
					Triple<Long, Long, Long> key = entry.getKey();
					List<Instant> existingDates = rangeBooleanDataService
							.getData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue().stream().map(RangeBooleanDataDto::getStartDate).collect(toList()))
							.stream().map(RangeBooleanDataEntity::getStartDate).collect(toList());
					entry.getValue().forEach(dto -> {
						if (existingDates.contains(dto.getStartDate())) {
							booleanDataToUpdate.add(dto);
						} else {
							booleanDataToCreate.add(dto);
						}
					});
				});
				
				if (! decimalDataToCreate.isEmpty()) {
					rangeDecimalDataService.create(decimalDataToCreate);
				}
				
				if (! decimalDataToUpdate.isEmpty()) {
					rangeDecimalDataService.update(decimalDataToUpdate);
				}
				
				if (! textDataToCreate.isEmpty()) {
					rangeTextDataService.create(textDataToCreate);
				}
				
				if (! textDataToUpdate.isEmpty()) {
					rangeTextDataService.update(textDataToUpdate);
				}
				
				if (! booleanDataToCreate.isEmpty()) {
					rangeBooleanDataService.create(booleanDataToCreate);
				}
				
				if (! booleanDataToUpdate.isEmpty()) {
					rangeBooleanDataService.update(booleanDataToUpdate);
				}
			} else {
				Map<Triple<Long, Long, Long>, List<SnapshotDecimalDataDto>> decimalData = new HashMap<>();
				Map<Triple<Long, Long, Long>, List<SnapshotTextDataDto>> textData = new HashMap<>();
				Map<Triple<Long, Long, Long>, List<SnapshotBooleanDataDto>> booleanData = new HashMap<>();
				widgetData.getRows().forEach(rowData -> {
					int i = 0;
					AssetDto asset = (AssetDto) rowData.getCells().get(i++).getParsedValue();
					Instant readingDate = (Instant) rowData.getCells().get(i++).getParsedValue();
					for (int e = 0; e < re.size(); e++) {
						WidgetElementDto reportElement = re.get(e);
						ElementDto element = reportElement.getElement();
						Triple<Long, Long, Long> key = Triple.of(asset.getId(), sourceId, element.getId());
						Object reading = rowData.getCells().get(i + e).getParsedValue();
						UnitDto unit = null;
						if (element.getUnitType() != null) {
							i++;
							unit = (UnitDto) rowData.getCells().get(i + e).getParsedValue();
						}
						if (reading != null) {
							if (MeasurementType.DECIMAL == element.getMeasurementType()) {
								SnapshotDecimalDataDto dd = new SnapshotDecimalDataDto();
								dd.setAssetId(asset.getId());
								dd.setReadingDate(readingDate);
								dd.setElementId(element.getId());
								dd.setSourceId(sourceId);
								dd.setReading((BigDecimal) reading);
								dd.setUnitId(unit != null ? unit.getId() : null);
								decimalData.putIfAbsent(key, new ArrayList<>());
								decimalData.get(key).add(dd);
							} else if (MeasurementType.TEXT == element.getMeasurementType() 
									|| MeasurementType.TEXT_OPTIONS == element.getMeasurementType()) {
								SnapshotTextDataDto dd = new SnapshotTextDataDto();
								dd.setAssetId(asset.getId());
								dd.setReadingDate(readingDate);
								dd.setElementId(element.getId());
								dd.setSourceId(sourceId);
								dd.setReading((String) reading);
								textData.putIfAbsent(key, new ArrayList<>());
								textData.get(key).add(dd);
							} else if (MeasurementType.BOOLEAN_OPTIONS == element.getMeasurementType()) {
								SnapshotBooleanDataDto dd = new SnapshotBooleanDataDto();
								dd.setAssetId(asset.getId());
								dd.setReadingDate(readingDate);
								dd.setElementId(element.getId());
								dd.setSourceId(sourceId);
								dd.setReading((Boolean) reading);
								booleanData.putIfAbsent(key, new ArrayList<>());
								booleanData.get(key).add(dd);
							}
						}
					}
				});
				
				List<SnapshotDecimalDataDto> decimalDataToCreate = new ArrayList<>();
				List<SnapshotDecimalDataDto> decimalDataToUpdate = new ArrayList<>();
				decimalData.entrySet().forEach(entry -> {
					Triple<Long, Long, Long> key = entry.getKey();
					List<Instant> existingDates = snapshotDecimalDataService
							.getData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue().stream().map(SnapshotDecimalDataDto::getReadingDate).collect(toList()))
							.stream().map(SnapshotDecimalDataEntity::getReadingDate).collect(toList());
					entry.getValue().forEach(dto -> {
						if (existingDates.contains(dto.getReadingDate())) {
							decimalDataToUpdate.add(dto);
						} else {
							decimalDataToCreate.add(dto);
						}
					});
				});
				
				List<SnapshotTextDataDto> textDataToCreate = new ArrayList<>();
				List<SnapshotTextDataDto> textDataToUpdate = new ArrayList<>();
				textData.entrySet().forEach(entry -> {
					Triple<Long, Long, Long> key = entry.getKey();
					List<Instant> existingDates = snapshotTextDataService
							.getData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue().stream().map(SnapshotTextDataDto::getReadingDate).collect(toList()))
							.stream().map(SnapshotTextDataEntity::getReadingDate).collect(toList());
					entry.getValue().forEach(dto -> {
						if (existingDates.contains(dto.getReadingDate())) {
							textDataToUpdate.add(dto);
						} else {
							textDataToCreate.add(dto);
						}
					});
				});
				
				List<SnapshotBooleanDataDto> booleanDataToCreate = new ArrayList<>();
				List<SnapshotBooleanDataDto> booleanDataToUpdate = new ArrayList<>();
				booleanData.entrySet().forEach(entry -> {
					Triple<Long, Long, Long> key = entry.getKey();
					List<Instant> existingDates = snapshotBooleanDataService
							.getData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue().stream().map(SnapshotBooleanDataDto::getReadingDate).collect(toList()))
							.stream().map(SnapshotBooleanDataEntity::getReadingDate).collect(toList());
					entry.getValue().forEach(dto -> {
						if (existingDates.contains(dto.getReadingDate())) {
							booleanDataToUpdate.add(dto);
						} else {
							booleanDataToCreate.add(dto);
						}
					});
				});
				
				if (! decimalDataToCreate.isEmpty()) {
					snapshotDecimalDataService.create(decimalDataToCreate);
				}
				
				if (! decimalDataToUpdate.isEmpty()) {
					snapshotDecimalDataService.update(decimalDataToUpdate);
				}
				
				if (! textDataToCreate.isEmpty()) {
					snapshotTextDataService.create(textDataToCreate);
				}
				
				if (! textDataToUpdate.isEmpty()) {
					snapshotTextDataService.update(textDataToUpdate);
				}
				
				if (! booleanDataToCreate.isEmpty()) {
					snapshotBooleanDataService.create(booleanDataToCreate);
				}
				
				if (! booleanDataToUpdate.isEmpty()) {
					snapshotBooleanDataService.update(booleanDataToUpdate);
				}
			}
		} catch (UploadDatasheetException e) {
			throw e;
		} catch (Exception e) {
			throw new UploadDatasheetException("Error importing data!", e.getMessage());
		}
	}
	
	private void validateDateRange(ExcelRow row, String startDateLabel, String endDateLabel) {
		Instant startDate = (Instant) row.getCells().get(1).getParsedValue();
		Instant endDate = (Instant) row.getCells().get(2).getParsedValue();
		if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
			row.addError(format("%s [ %s ] can not be before the %s [ %s ]", endDateLabel, row.getCells().get(2).getRawValue(), startDateLabel, row.getCells().get(1).getRawValue()));
		}
	}

	private List<Class<?>> getCellTypes(WidgetDto widget) {
		List<Class<?>> cellTypes = new ArrayList<>();
		// Asset Name
		cellTypes.add(String.class);
		
		// Start/Reading Date
		cellTypes.add(Instant.class);
		
		if (RANGE == widget.getWidgetType().getCategory()) {
			// End Date
			cellTypes.add(Instant.class);
		}
		
		// Widget Element Types
		getAllowedImportableElements(widget).forEach(element -> {
			switch (element.getMeasurementType()) {
				case DECIMAL:
					cellTypes.add(BigDecimal.class);
					break;
				case TEXT:
				case TEXT_OPTIONS:
				case BOOLEAN_OPTIONS:
					cellTypes.add(String.class);
					break;
				default:
					break;
			}
			if (element.getUnitType() != null) {
				cellTypes.add(String.class);
			}
		});
		return cellTypes;
	}

	private List<WidgetElementDto> getAllowedImportableWidgetElements(WidgetDto widget) {
		return widget.getElements().stream()
				.filter(WidgetElementDto::isImportable)
				.filter(e -> ALLOWED_TYPES.contains(e.getElement().getMeasurementType()))
				.collect(toList());
	}

	private List<ElementDto> getAllowedImportableElements(WidgetDto widget) {
		return getAllowedImportableWidgetElements(widget).stream()
				.map(WidgetElementDto::getElement)
				.collect(toList());
	}

	private List<ElementDto> getAllowedImportableMandatoryElements(WidgetDto widget) {
		return getAllowedImportableWidgetElements(widget).stream()
				.filter(WidgetElementDto::isMandatory)
				.map(WidgetElementDto::getElement)
				.collect(toList());
	}
}