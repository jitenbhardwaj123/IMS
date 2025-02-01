package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
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
@Table(name = "range_decimal_data")
public class RangeDecimalDataEntity extends Auditable implements Mergeable<RangeDecimalDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "range_decimal_data_seq", sequenceName = "range_decimal_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "range_decimal_data_seq", strategy = SEQUENCE)
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
	private BigDecimal reading;

	@Column(name = "unit_id")
	private Long unitId;

	private RangeDecimalDataEntity(RangeDecimalDataEntityBuilder rangeDecimalDataEntityBuilder) {
		this.id = rangeDecimalDataEntityBuilder.id;
		this.assetId = rangeDecimalDataEntityBuilder.assetId;
		this.elementId = rangeDecimalDataEntityBuilder.elementId;
		this.sourceId = rangeDecimalDataEntityBuilder.sourceId;
		this.startDate = rangeDecimalDataEntityBuilder.startDate;
		this.endDate = rangeDecimalDataEntityBuilder.endDate;
		this.reading = rangeDecimalDataEntityBuilder.reading;
		this.unitId = rangeDecimalDataEntityBuilder.unitId;
	}

	public RangeDecimalDataEntity() {
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

	public BigDecimal getReading() {
		return reading;
	}

	public void setReading(BigDecimal reading) {
		this.reading = reading;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	
	@Override
	public RangeDecimalDataEntity merge(RangeDecimalDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.startDate = other.startDate;
			this.endDate = other.endDate;
			this.reading = other.reading;
			this.unitId = other.unitId;
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
		RangeDecimalDataEntity castOther = (RangeDecimalDataEntity) other;
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
				.append("startDate", startDate).append("endDate", endDate).append("reading", reading)
				.append("unitId", unitId).toString();
	}

	public static RangeDecimalDataEntityBuilder builder() {
		return new RangeDecimalDataEntityBuilder();
	}

	public static RangeDecimalDataEntityBuilder builderFrom(RangeDecimalDataEntity rangeDecimalDataEntity) {
		return new RangeDecimalDataEntityBuilder(rangeDecimalDataEntity);
	}

	public static final class RangeDecimalDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant startDate;
		private Instant endDate;
		private BigDecimal reading;
		private Long unitId;

		private RangeDecimalDataEntityBuilder() {
		}

		private RangeDecimalDataEntityBuilder(RangeDecimalDataEntity rangeDecimalDataEntity) {
			this.id = rangeDecimalDataEntity.id;
			this.assetId = rangeDecimalDataEntity.assetId;
			this.elementId = rangeDecimalDataEntity.elementId;
			this.sourceId = rangeDecimalDataEntity.sourceId;
			this.startDate = rangeDecimalDataEntity.startDate;
			this.endDate = rangeDecimalDataEntity.endDate;
			this.reading = rangeDecimalDataEntity.reading;
			this.unitId = rangeDecimalDataEntity.unitId;
		}

		public RangeDecimalDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public RangeDecimalDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public RangeDecimalDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public RangeDecimalDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public RangeDecimalDataEntityBuilder startDate(Instant startDate) {
			this.startDate = startDate;
			return this;
		}

		public RangeDecimalDataEntityBuilder endDate(Instant endDate) {
			this.endDate = endDate;
			return this;
		}

		public RangeDecimalDataEntityBuilder reading(BigDecimal reading) {
			this.reading = reading;
			return this;
		}

		public RangeDecimalDataEntityBuilder unitId(Long unitId) {
			this.unitId = unitId;
			return this;
		}

		public RangeDecimalDataEntity build() {
			return new RangeDecimalDataEntity(this);
		}
	}
}