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
@Table(name = "range_boolean_data")
public class RangeBooleanDataEntity extends Auditable implements Mergeable<RangeBooleanDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "range_boolean_data_seq", sequenceName = "range_boolean_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "range_boolean_data_seq", strategy = SEQUENCE)
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
	private Boolean reading;

	private RangeBooleanDataEntity(RangeBooleanDataEntityBuilder rangeBooleanDataEntityBuilder) {
		this.id = rangeBooleanDataEntityBuilder.id;
		this.assetId = rangeBooleanDataEntityBuilder.assetId;
		this.elementId = rangeBooleanDataEntityBuilder.elementId;
		this.sourceId = rangeBooleanDataEntityBuilder.sourceId;
		this.startDate = rangeBooleanDataEntityBuilder.startDate;
		this.endDate = rangeBooleanDataEntityBuilder.endDate;
		this.reading = rangeBooleanDataEntityBuilder.reading;
	}

	public RangeBooleanDataEntity() {
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

	public Boolean getReading() {
		return reading;
	}

	public void setReading(Boolean reading) {
		this.reading = reading;
	}
	
	@Override
	public RangeBooleanDataEntity merge(RangeBooleanDataEntity other) {
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
		RangeBooleanDataEntity castOther = (RangeBooleanDataEntity) other;
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

	public static RangeBooleanDataEntityBuilder builder() {
		return new RangeBooleanDataEntityBuilder();
	}

	public static RangeBooleanDataEntityBuilder builderFrom(RangeBooleanDataEntity rangeBooleanDataEntity) {
		return new RangeBooleanDataEntityBuilder(rangeBooleanDataEntity);
	}

	public static final class RangeBooleanDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant startDate;
		private Instant endDate;
		private Boolean reading;

		private RangeBooleanDataEntityBuilder() {
		}

		private RangeBooleanDataEntityBuilder(RangeBooleanDataEntity rangeBooleanDataEntity) {
			this.id = rangeBooleanDataEntity.id;
			this.assetId = rangeBooleanDataEntity.assetId;
			this.elementId = rangeBooleanDataEntity.elementId;
			this.sourceId = rangeBooleanDataEntity.sourceId;
			this.startDate = rangeBooleanDataEntity.startDate;
			this.endDate = rangeBooleanDataEntity.endDate;
			this.reading = rangeBooleanDataEntity.reading;
		}

		public RangeBooleanDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public RangeBooleanDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public RangeBooleanDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public RangeBooleanDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public RangeBooleanDataEntityBuilder startDate(Instant startDate) {
			this.startDate = startDate;
			return this;
		}

		public RangeBooleanDataEntityBuilder endDate(Instant endDate) {
			this.endDate = endDate;
			return this;
		}

		public RangeBooleanDataEntityBuilder reading(Boolean reading) {
			this.reading = reading;
			return this;
		}

		public RangeBooleanDataEntity build() {
			return new RangeBooleanDataEntity(this);
		}
	}
}