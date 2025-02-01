package com.oak.dto;

import java.time.Instant;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class WidgetDataKey implements Comparable<WidgetDataKey> {
	private Long assetId;
	private Long sourceId;
	private Instant readingDate;
	private Instant endDate;

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
		WidgetDataKey castOther = (WidgetDataKey) other;
		return new EqualsBuilder().append(assetId, castOther.assetId).append(sourceId, castOther.sourceId)
				.append(readingDate, castOther.readingDate).append(endDate, castOther.endDate).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(assetId).append(sourceId).append(readingDate).append(endDate).toHashCode();
	}

	@Override
	public int compareTo(final WidgetDataKey other) {
		return new CompareToBuilder().append(assetId, other.assetId).append(sourceId, other.sourceId)
				.append(other.readingDate, readingDate).append(other.endDate, endDate).toComparison();
	}
}