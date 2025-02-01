package com.oak.dto;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WidgetDataDto {
	private Long assetId;
	private Long sourceId;
	private Instant readingDate;
	private Instant endDate;
	private List<ElementDataDto> elementData = new ArrayList<>();
	
	public Long getAssetId() {
		return assetId;
	}
	
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	
	public Long getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Instant getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Instant readingDate) {
		this.readingDate = readingDate;
	}
	
	public Instant getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}
	
	public List<ElementDataDto> getElementData() {
		return elementData;
	}
	
	public void setElementData(List<ElementDataDto> elementData) {
		this.elementData.clear();
		if (isNotEmpty(elementData)) {
			this.elementData.addAll(elementData);
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("assetId", assetId).append("sourceId", sourceId).append("readingDate", readingDate)
				.append("endDate", endDate).append("elementData", elementData).toString();
	}
}