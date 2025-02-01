package com.oak.service.impl;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oak.dto.ElementDto;
import com.oak.dto.FilterSelectionDto;
import com.oak.dto.LineChartAssetData;
import com.oak.dto.LineChartData;
import com.oak.dto.LineChartElementData;
import com.oak.dto.LineChartReading;
import com.oak.dto.LineChartSourceData;
import com.oak.dto.WidgetDto;
import com.oak.dto.WidgetElementDto;
import com.oak.entity.SnapshotDecimalDataEntity;
import com.oak.service.SnapshotDecimalDataService;
import com.oak.service.SnapshotLineChartWidgetDataService;
import com.oak.service.WidgetService;

@Service
@Transactional
public class SnapshotLineChartWidgetDataServiceImpl implements SnapshotLineChartWidgetDataService {
	private static final ZoneId TIMEZONE = ZoneId.systemDefault();
	private final Logger logger;
	private final WidgetService widgetService;
	private final SnapshotDecimalDataService decimalDataService;
	private final FilterSelectionProcessor filterSelectionProcessor;

	@Autowired
	public SnapshotLineChartWidgetDataServiceImpl(Logger logger, WidgetService widgetService,
			SnapshotDecimalDataService decimalDataService, FilterSelectionProcessor filterSelectionProcessor) {
		super();
		this.logger = logger;
		this.widgetService = widgetService;
		this.decimalDataService = decimalDataService;
		this.filterSelectionProcessor = filterSelectionProcessor;
	}

	@Override
	public List<LineChartData> read(Long widgetId, Long assetId, FilterSelectionDto filter) {
		logger.info("Get Snapshot Line Chart Widget Data [ widgetId: {}, assetId: {}, filter: {} ]", widgetId, assetId,
				filter);

		WidgetDto widget = this.widgetService.read(widgetId);
		final FilterSelectionDto updatedFilter = filterSelectionProcessor.apply(widget.getFilterCriteria(), filter,
				assetId);
		applyElements(widget, updatedFilter);
		return getWidgetData(updatedFilter);
	}

	private void applyElements(WidgetDto widget, FilterSelectionDto filter) {
		if (filter.getElementIds().isEmpty()) {
			filter.setElementIds(widget.getElements().stream().map(WidgetElementDto::getElement)
					.map(ElementDto::getId).distinct().collect(toList()));
		}
	}

	private List<LineChartData> getWidgetData(FilterSelectionDto filter) {
		List<SnapshotDecimalDataEntity> snapshotData = decimalDataService.getData(filter);
		List<LineChartData> chartData = new ArrayList<>();
		snapshotData.forEach(dd -> {
			// ensure Unit data
			LineChartData unitData = new LineChartData(dd.getUnitId());
			if (!chartData.contains(unitData)) {
				chartData.add(unitData);
			} else {
				unitData = chartData.get(chartData.lastIndexOf(unitData));
			}

			// ensure Asset data
			LineChartAssetData assetData = new LineChartAssetData(dd.getAssetId());
			if (!unitData.getAssetData().contains(assetData)) {
				unitData.getAssetData().add(assetData);
			} else {
				assetData = unitData.getAssetData().get(unitData.getAssetData().lastIndexOf(assetData));
			}

			// ensure Source data
			LineChartSourceData sourceData = new LineChartSourceData(dd.getSourceId());
			if (!assetData.getSourceData().contains(sourceData)) {
				assetData.getSourceData().add(sourceData);
			} else {
				sourceData = assetData.getSourceData().get(assetData.getSourceData().lastIndexOf(sourceData));
			}

			// ensure Element data
			LineChartElementData elementData = new LineChartElementData(dd.getElementId());
			if (!sourceData.getElementData().contains(elementData)) {
				sourceData.getElementData().add(elementData);
			} else {
				elementData = sourceData.getElementData().get(sourceData.getElementData().lastIndexOf(elementData));
			}

			// ensure Reading
			elementData.addReading(new LineChartReading(dd.getReadingDate(), dd.getReading()));
		});
		
		// aggregate data if needed
		if (isAggregationNeeded(filter)) {
			aggregate(chartData, filter);
		}

		return chartData;
	}

	private void aggregate(List<LineChartData> chartData, FilterSelectionDto filter) {
		chartData.forEach(cd -> {
			cd.getAssetData().forEach(ad -> {
				ad.getSourceData().forEach(sd -> {
					sd.getElementData().forEach(ed -> {
						ed.setReadings(aggregateReadings(ed.getReadings(), filter));
					});
				});
			});
		});
	}

	private boolean isAggregationNeeded(FilterSelectionDto filter) {
		return filter.getAggregationType() != null && filter.getAggregationInterval() != null
				&& filter.getAggregationTime() > 0;
	}

	private List<LineChartReading> aggregateReadings(List<LineChartReading> readings, FilterSelectionDto filter) {
		SortedMap<Instant, List<LineChartReading>> batch = batchReadings(readings, filter);
		return batch.entrySet().stream().map(es -> this.aggregateBatch(es.getKey(), es.getValue(), filter)).filter(Objects::nonNull).collect(toList());
	}

	private LineChartReading aggregateBatch(Instant aggregatedDate, List<LineChartReading> readings, FilterSelectionDto filter) {
		if (readings.stream().map(LineChartReading::getReadingValue).filter(Objects::nonNull).count() == 0) {
			return null;
		}
		
		BigDecimal aggregatedValue = null;
		switch (filter.getAggregationType()) {
			case TOTAL:
				aggregatedValue = readings.stream().map(LineChartReading::getReadingValue).filter(Objects::nonNull).reduce(ZERO, BigDecimal::add);
				break;
			case MAX:
				aggregatedValue = readings.stream().map(LineChartReading::getReadingValue).filter(Objects::nonNull).max(BigDecimal::compareTo).orElse(null);
				break;
			case MIN:
				aggregatedValue = readings.stream().map(LineChartReading::getReadingValue).filter(Objects::nonNull).min(BigDecimal::compareTo).orElse(null);
				break;
			case AVERAGE:
				aggregatedValue = average(readings.stream().map(LineChartReading::getReadingValue).collect(toList()));
				break;
			default:
				throw new RuntimeException("Unhandled AggregationType found!");
		}
		return aggregatedValue == null ? null : new LineChartReading(aggregatedDate, aggregatedValue);
	}
	
	private BigDecimal average(List<BigDecimal> readings) {
		// Filter the list removing null values
		List<BigDecimal> bigDecimals = readings.stream().filter(Objects::nonNull).collect(Collectors.toList());

		// Special cases
		if (bigDecimals.isEmpty()) {
			return null;
		}
		if (bigDecimals.size() == 1) {
			return bigDecimals.get(0);
		}

		// Return the average of the BigDecimals in the list
		return bigDecimals.stream().reduce(ZERO, BigDecimal::add).divide(new BigDecimal(bigDecimals.size()), HALF_UP);
	}

	private SortedMap<Instant, List<LineChartReading>> batchReadings(List<LineChartReading> readings,
			FilterSelectionDto filter) {
		SortedMap<Instant, List<LineChartReading>> batchReadings = new TreeMap<>();
		List<Instant> ticks = getDatesBetween(filter.getRangeStart(), filter.getRangeEnd(), filter);
		if (ticks.size() <= 2) {
			batchReadings.put(filter.getRangeStart(), readings);
		} else {
			for (int i = 0; i < ticks.size(); i++) {
				Instant start = ticks.get(i);
				if (i < ticks.size()-1) {
					Instant end = ticks.get(i + 1);
					List<LineChartReading> tickReadings = readings.stream()
							.filter(r -> r.getReadingDate().compareTo(start) >= 0 && r.getReadingDate().isBefore(end))
							.collect(toList());
					batchReadings.put(start, tickReadings);
				} else {
					List<LineChartReading> tickReadings = readings.stream()
							.filter(r -> r.getReadingDate().compareTo(start) >= 0)
							.collect(toList());
					batchReadings.put(start, tickReadings);
				}
			}
		}
		return batchReadings;
	}
	
	private List<Instant> getDatesBetween(Instant rangeStart, Instant rangeEnd, FilterSelectionDto filter) {
		List<Instant> dates = new ArrayList<>();
		
		ChronoUnit tickUnit = getChronoUnit(filter);
		LocalDateTime startDate = truncateToEarliestChronoUnit(LocalDateTime.ofInstant(rangeStart, TIMEZONE), tickUnit);
		LocalDateTime endDate = LocalDateTime.ofInstant(rangeEnd, TIMEZONE);
		
		// add range start
		dates.add(startDate.atZone(TIMEZONE).toInstant());
		
		// add ticks
		long totalTicks = tickUnit.between(startDate, endDate);
		if (totalTicks > 0 && filter.getAggregationTime() > 0 && totalTicks/filter.getAggregationTime() > 0) {
			totalTicks = totalTicks/filter.getAggregationTime();
			dates.addAll(LongStream.rangeClosed(1, totalTicks).mapToObj(i -> startDate.plus(i*filter.getAggregationTime(), tickUnit)).map(d -> d.atZone(TIMEZONE).toInstant()).collect(toList()));
		}
		
		// add range end if last tick is not same as the range end
		if (! rangeEnd.equals(dates.get(dates.size()-1))) {
			dates.add(rangeEnd);
		}
		
		return dates;
	}

	private ChronoUnit getChronoUnit(FilterSelectionDto filter) {
		switch (filter.getAggregationInterval()) {
			case MINUTES:
				return ChronoUnit.MINUTES;
			case HOURS:
				return ChronoUnit.HOURS;
			case DAYS:
				return ChronoUnit.DAYS;
			case MONTHS:
				return ChronoUnit.MONTHS;
			case YEARS:
				return ChronoUnit.YEARS;
			default:
				throw new RuntimeException("Unhandled AggregationInterval found!");
		}
	}

	private LocalDateTime truncateToEarliestChronoUnit(LocalDateTime dt, ChronoUnit unit) {
		switch (unit) {
			case MINUTES:
			case HOURS:
			case DAYS:
				return dt.truncatedTo(unit);
			case MONTHS:
				return dt.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);
			case YEARS:
				return dt.with(TemporalAdjusters.firstDayOfYear()).truncatedTo(ChronoUnit.DAYS);
			default:
				throw new RuntimeException("Unhandled AggregationInterval found!");
		}
	}
}
