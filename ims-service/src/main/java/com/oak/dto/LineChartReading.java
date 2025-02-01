package com.oak.dto;

import java.math.BigDecimal;
import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LineChartReading {
	private Instant readingDate;
	private BigDecimal readingValue;

	public LineChartReading() {
	}

	public LineChartReading(Instant readingDate, BigDecimal readingValue) {
		this.readingDate = readingDate;
		this.readingValue = readingValue;
	}

	public Instant getReadingDate() {
		return readingDate;
	}
	
	public void setReadingDate(Instant readingDate) {
		this.readingDate = readingDate;
	}
	
	public BigDecimal getReadingValue() {
		return readingValue;
	}
	
	public void setReadingValue(BigDecimal readingValue) {
		this.readingValue = readingValue;
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
		LineChartReading castOther = (LineChartReading) other;
		return new EqualsBuilder().append(readingDate, castOther.readingDate).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(readingDate).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("readingDate", readingDate).append("readingValue", readingValue).toString();
	}
}
