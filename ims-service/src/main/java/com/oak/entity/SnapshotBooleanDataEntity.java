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
@Table(name = "snapshot_boolean_data")
public class SnapshotBooleanDataEntity extends Auditable implements Mergeable<SnapshotBooleanDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "snapshot_boolean_data_seq", sequenceName = "snapshot_boolean_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "snapshot_boolean_data_seq", strategy = SEQUENCE)
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
	@IsNotNull("LineChartReading Date")
	private Instant readingDate;

	@Column(name = "reading")
	private Boolean reading;

	private SnapshotBooleanDataEntity(SnapshotBooleanDataEntityBuilder snapshotBooleanDataEntityBuilder) {
		this.id = snapshotBooleanDataEntityBuilder.id;
		this.assetId = snapshotBooleanDataEntityBuilder.assetId;
		this.elementId = snapshotBooleanDataEntityBuilder.elementId;
		this.sourceId = snapshotBooleanDataEntityBuilder.sourceId;
		this.readingDate = snapshotBooleanDataEntityBuilder.readingDate;
		this.reading = snapshotBooleanDataEntityBuilder.reading;
	}

	public SnapshotBooleanDataEntity() {
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

	public Boolean getReading() {
		return reading;
	}

	public void setReading(Boolean reading) {
		this.reading = reading;
	}
	
	@Override
	public SnapshotBooleanDataEntity merge(SnapshotBooleanDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.readingDate = other.readingDate;
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
		SnapshotBooleanDataEntity castOther = (SnapshotBooleanDataEntity) other;
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
				.append("readingDate", readingDate).append("reading", reading).toString();
	}

	public static SnapshotBooleanDataEntityBuilder builder() {
		return new SnapshotBooleanDataEntityBuilder();
	}

	public static SnapshotBooleanDataEntityBuilder builderFrom(SnapshotBooleanDataEntity snapshotBooleanDataEntity) {
		return new SnapshotBooleanDataEntityBuilder(snapshotBooleanDataEntity);
	}

	public static final class SnapshotBooleanDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant readingDate;
		private Boolean reading;

		private SnapshotBooleanDataEntityBuilder() {
		}

		private SnapshotBooleanDataEntityBuilder(SnapshotBooleanDataEntity snapshotBooleanDataEntity) {
			this.id = snapshotBooleanDataEntity.id;
			this.assetId = snapshotBooleanDataEntity.assetId;
			this.elementId = snapshotBooleanDataEntity.elementId;
			this.sourceId = snapshotBooleanDataEntity.sourceId;
			this.readingDate = snapshotBooleanDataEntity.readingDate;
			this.reading = snapshotBooleanDataEntity.reading;
		}

		public SnapshotBooleanDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SnapshotBooleanDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public SnapshotBooleanDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public SnapshotBooleanDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public SnapshotBooleanDataEntityBuilder readingDate(Instant readingDate) {
			this.readingDate = readingDate;
			return this;
		}

		public SnapshotBooleanDataEntityBuilder reading(Boolean reading) {
			this.reading = reading;
			return this;
		}

		public SnapshotBooleanDataEntity build() {
			return new SnapshotBooleanDataEntity(this);
		}
	}

}