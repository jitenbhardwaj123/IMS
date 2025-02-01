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
@Table(name = "snapshot_text_data")
public class SnapshotTextDataEntity extends Auditable implements Mergeable<SnapshotTextDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "snapshot_text_data_seq", sequenceName = "snapshot_text_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "snapshot_text_data_seq", strategy = SEQUENCE)
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
	private String reading;

	private SnapshotTextDataEntity(SnapshotTextDataBuilder snapshotTextDataBuilder) {
		this.id = snapshotTextDataBuilder.id;
		this.assetId = snapshotTextDataBuilder.assetId;
		this.elementId = snapshotTextDataBuilder.elementId;
		this.sourceId = snapshotTextDataBuilder.sourceId;
		this.readingDate = snapshotTextDataBuilder.readingDate;
		this.reading = snapshotTextDataBuilder.reading;
	}

	public SnapshotTextDataEntity() {
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

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}
	
	@Override
	public SnapshotTextDataEntity merge(SnapshotTextDataEntity other) {
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
		SnapshotTextDataEntity castOther = (SnapshotTextDataEntity) other;
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

	public static SnapshotTextDataBuilder builder() {
		return new SnapshotTextDataBuilder();
	}

	public static SnapshotTextDataBuilder builderFrom(SnapshotTextDataEntity snapshotTextDataEntity) {
		return new SnapshotTextDataBuilder(snapshotTextDataEntity);
	}

	public static final class SnapshotTextDataBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant readingDate;
		private String reading;

		private SnapshotTextDataBuilder() {
		}

		private SnapshotTextDataBuilder(SnapshotTextDataEntity snapshotTextDataEntity) {
			this.id = snapshotTextDataEntity.id;
			this.assetId = snapshotTextDataEntity.assetId;
			this.elementId = snapshotTextDataEntity.elementId;
			this.sourceId = snapshotTextDataEntity.sourceId;
			this.readingDate = snapshotTextDataEntity.readingDate;
			this.reading = snapshotTextDataEntity.reading;
		}

		public SnapshotTextDataBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SnapshotTextDataBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public SnapshotTextDataBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public SnapshotTextDataBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public SnapshotTextDataBuilder readingDate(Instant readingDate) {
			this.readingDate = readingDate;
			return this;
		}

		public SnapshotTextDataBuilder reading(String reading) {
			this.reading = reading;
			return this;
		}

		public SnapshotTextDataEntity build() {
			return new SnapshotTextDataEntity(this);
		}
	}

}