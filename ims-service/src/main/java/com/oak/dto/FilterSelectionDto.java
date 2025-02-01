package com.oak.dto;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.enums.AggregationInterval;
import com.oak.enums.AggregationType;

public class FilterSelectionDto {
	private Instant rangeStart;
	private Instant rangeEnd;
	private List<Long> assetIds = new ArrayList<>();
	private List<Long> elementIds = new ArrayList<>();
	private AggregationType aggregationType;
	private AggregationInterval aggregationInterval;
	private int aggregationTime;
	
	public Instant getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(Instant rangeStart) {
		this.rangeStart = rangeStart;
	}

	public Instant getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(Instant rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	public List<Long> getAssetIds() {
		return assetIds;
	}

	public void setAssetIds(List<Long> assetIds) {
		this.assetIds.clear();
		if (isNotEmpty(assetIds)) {
			this.assetIds.addAll(assetIds);
		}
	}

	public List<Long> getElementIds() {
		return elementIds;
	}

	public void setElementIds(List<Long> elementIds) {
		this.elementIds.clear();
		if (isNotEmpty(elementIds)) {
			this.elementIds.addAll(elementIds);
		}
	}

	public AggregationType getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(AggregationType aggregationType) {
		this.aggregationType = aggregationType;
	}

	public AggregationInterval getAggregationInterval() {
		return aggregationInterval;
	}

	public void setAggregationInterval(AggregationInterval aggregationInterval) {
		this.aggregationInterval = aggregationInterval;
	}

	public int getAggregationTime() {
		return aggregationTime;
	}

	public void setAggregationTime(int aggregationTime) {
		this.aggregationTime = aggregationTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("rangeStart", rangeStart).append("rangeEnd", rangeEnd).append("assetIds", assetIds)
				.append("elementIds", elementIds).append("aggregationType", aggregationType)
				.append("aggregationInterval", aggregationInterval).append("aggregationTime", aggregationTime)
				.toString();
	}
}
