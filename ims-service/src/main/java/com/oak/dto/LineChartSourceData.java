package com.oak.dto;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LineChartSourceData {
	private Long sourceId;
	private List<LineChartElementData> elementData = new ArrayList<>();
	
	public LineChartSourceData() {
	}

	public LineChartSourceData(Long sourceId) {
		this.sourceId = sourceId;
	}

	public LineChartSourceData(Long sourceId, List<LineChartElementData> elementData) {
		this.sourceId = sourceId;
		this.elementData = elementData;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public List<LineChartElementData> getElementData() {
		return elementData;
	}

	public void setElementData(List<LineChartElementData> elementData) {
		this.elementData.clear();
		if (isNotEmpty(elementData)) {
			this.elementData = elementData;
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!getClass().equals(other.getClass())) {
			return false;
		}
		LineChartSourceData castOther = (LineChartSourceData) other;
		return new EqualsBuilder().append(sourceId, castOther.sourceId).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sourceId).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("sourceId", sourceId).append("elementData", elementData).toString();
	}
}
