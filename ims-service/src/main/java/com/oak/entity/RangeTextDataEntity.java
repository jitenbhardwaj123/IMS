package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotNull;

@Entity
@Table(name = "range_text_data")
public class RangeTextDataEntity extends Auditable implements Mergeable<RangeTextDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "range_text_data_seq", sequenceName = "range_text_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "range_text_data_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "asset_id")
	@IsNotNull("Asset Id")
	private Long assetId;

	@Column(name = "element_id")
	@IsNotNull("Element Id")
	private Long elementId;

	@Column(name = "source_id")
	@IsNotNull("Source Id")
	private Long sourceId;

	@Column(name = "start_date")
	@IsNotNull("Start Date")
	private Instant startDate;

	@Column(name = "end_date")
	private Instant endDate;

	@Column(name = "reading")
	private String reading;

	private RangeTextDataEntity(RangeTextDataEntityBuilder rangeTextDataEntityBuilder) {
		this.id = rangeTextDataEntityBuilder.id;
		this.assetId = rangeTextDataEntityBuilder.assetId;
		this.elementId = rangeTextDataEntityBuilder.elementId;
		this.sourceId = rangeTextDataEntityBuilder.sourceId;
		this.startDate = rangeTextDataEntityBuilder.startDate;
		this.endDate = rangeTextDataEntityBuilder.endDate;
		this.reading = rangeTextDataEntityBuilder.reading;
	}

	public RangeTextDataEntity() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}
	
	@Override
	public RangeTextDataEntity merge(RangeTextDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.startDate = other.startDate;
			this.endDate = other.endDate;
			this.reading = other.reading;
		}
		return this;
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
		RangeTextDataEntity castOther = (RangeTextDataEntity) other;
		return new EqualsBuilder().append(assetId, castOther.assetId).append(elementId, castOther.elementId)
				.append(sourceId, castOther.sourceId).append(startDate, castOther.startDate).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(assetId).append(elementId).append(sourceId).append(startDate).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("assetId", assetId).append("elementId", elementId).append("sourceId", sourceId)
				.append("startDate", startDate).append("endDate", endDate).append("reading", reading).toString();
	}

	public static RangeTextDataEntityBuilder builder() {
		return new RangeTextDataEntityBuilder();
	}

	public static RangeTextDataEntityBuilder builderFrom(RangeTextDataEntity rangeTextDataEntity) {
		return new RangeTextDataEntityBuilder(rangeTextDataEntity);
	}

	public static final class RangeTextDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant startDate;
		private Instant endDate;
		private String reading;

		private RangeTextDataEntityBuilder() {
		}

		private RangeTextDataEntityBuilder(RangeTextDataEntity rangeTextDataEntity) {
			this.id = rangeTextDataEntity.id;
			this.assetId = rangeTextDataEntity.assetId;
			this.elementId = rangeTextDataEntity.elementId;
			this.sourceId = rangeTextDataEntity.sourceId;
			this.startDate = rangeTextDataEntity.startDate;
			this.endDate = rangeTextDataEntity.endDate;
			this.reading = rangeTextDataEntity.reading;
		}

		public RangeTextDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public RangeTextDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public RangeTextDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public RangeTextDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public RangeTextDataEntityBuilder startDate(Instant startDate) {
			this.startDate = startDate;
			return this;
		}

		public RangeTextDataEntityBuilder endDate(Instant endDate) {
			this.endDate = endDate;
			return this;
		}

		public RangeTextDataEntityBuilder reading(String reading) {
			this.reading = reading;
			return this;
		}

		public RangeTextDataEntity build() {
			return new RangeTextDataEntity(this);
		}
	}
}