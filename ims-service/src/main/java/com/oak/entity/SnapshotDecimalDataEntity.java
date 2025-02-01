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
@Table(name = "snapshot_decimal_data")
public class SnapshotDecimalDataEntity extends Auditable implements Mergeable<SnapshotDecimalDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "snapshot_decimal_data_seq", sequenceName = "snapshot_decimal_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "snapshot_decimal_data_seq", strategy = SEQUENCE)
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

	@Column(name = "reading_date")
	@IsNotNull("Reading Date")
	private Instant readingDate;

	@Column(name = "reading")
	private BigDecimal reading;

	@Column(name = "unit_id")
	private Long unitId;

	private SnapshotDecimalDataEntity(SnapshotDecimalDataBuilder snapshotDecimalDataBuilder) {
		this.id = snapshotDecimalDataBuilder.id;
		this.assetId = snapshotDecimalDataBuilder.assetId;
		this.elementId = snapshotDecimalDataBuilder.elementId;
		this.sourceId = snapshotDecimalDataBuilder.sourceId;
		this.readingDate = snapshotDecimalDataBuilder.readingDate;
		this.reading = snapshotDecimalDataBuilder.reading;
		this.unitId = snapshotDecimalDataBuilder.unitId;
	}

	public SnapshotDecimalDataEntity() {
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

	public Instant getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Instant readingDate) {
		this.readingDate = readingDate;
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
	public SnapshotDecimalDataEntity merge(SnapshotDecimalDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.readingDate = other.readingDate;
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
		SnapshotDecimalDataEntity castOther = (SnapshotDecimalDataEntity) other;
		return new EqualsBuilder().append(assetId, castOther.assetId).append(elementId, castOther.elementId)
				.append(sourceId, castOther.sourceId).append(readingDate, castOther.readingDate).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(assetId).append(elementId).append(sourceId).append(readingDate)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("assetId", assetId).append("elementId", elementId).append("sourceId", sourceId)
				.append("readingDate", readingDate).append("reading", reading).append("unitId", unitId).toString();
	}

	public static SnapshotDecimalDataBuilder builder() {
		return new SnapshotDecimalDataBuilder();
	}

	public static SnapshotDecimalDataBuilder builderFrom(SnapshotDecimalDataEntity snapshotDecimalDataEntity) {
		return new SnapshotDecimalDataBuilder(snapshotDecimalDataEntity);
	}

	public static final class SnapshotDecimalDataBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant readingDate;
		private BigDecimal reading;
		private Long unitId;

		private SnapshotDecimalDataBuilder() {
		}

		private SnapshotDecimalDataBuilder(SnapshotDecimalDataEntity snapshotDecimalDataEntity) {
			this.id = snapshotDecimalDataEntity.id;
			this.assetId = snapshotDecimalDataEntity.assetId;
			this.elementId = snapshotDecimalDataEntity.elementId;
			this.sourceId = snapshotDecimalDataEntity.sourceId;
			this.readingDate = snapshotDecimalDataEntity.readingDate;
			this.reading = snapshotDecimalDataEntity.reading;
			this.unitId = snapshotDecimalDataEntity.unitId;
		}

		public SnapshotDecimalDataBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SnapshotDecimalDataBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public SnapshotDecimalDataBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public SnapshotDecimalDataBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public SnapshotDecimalDataBuilder readingDate(Instant readingDate) {
			this.readingDate = readingDate;
			return this;
		}

		public SnapshotDecimalDataBuilder reading(BigDecimal reading) {
			this.reading = reading;
			return this;
		}

		public SnapshotDecimalDataBuilder unitId(Long unitId) {
			this.unitId = unitId;
			return this;
		}

		public SnapshotDecimalDataEntity build() {
			return new SnapshotDecimalDataEntity(this);
		}
	}
}