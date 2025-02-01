package com.oak.dto;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LineChartElementData {
	private Long elementId;
	private List<LineChartReading> readings = new ArrayList<>();
	
	public LineChartElementData() {
	}

	public LineChartElementData(Long elementId) {
		this.elementId = elementId;
	}

	public LineChartElementData(Long elementId, List<LineChartReading> readings) {
		this.elementId = elementId;
		this.readings = readings;
	}

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public List<LineChartReading> getReadings() {
		return readings;
	}

	public void setReadings(List<LineChartReading> readings) {
		this.readings.clear();
		if (isNotEmpty(readings)) {
			this.readings = readings;
		}
	}

	public void addReading(LineChartReading reading) {
		this.readings.add(reading);
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
		LineChartElementData castOther = (LineChartElementData) other;
		return new EqualsBuilder().append(elementId, castOther.elementId).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(elementId).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("elementId", elementId).append("readings", readings).toString();
	}
}
