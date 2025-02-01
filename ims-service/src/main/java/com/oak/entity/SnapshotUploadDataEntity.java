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
@Table(name = "snapshot_upload_data")
public class SnapshotUploadDataEntity extends Auditable implements Mergeable<SnapshotUploadDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "snapshot_upload_data_seq", sequenceName = "snapshot_upload_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "snapshot_upload_data_seq", strategy = SEQUENCE)
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

	@Column(name = "upload_record_id")
	private Long uploadRecordId;

	private SnapshotUploadDataEntity(SnapshotUploadDataEntityBuilder snapshotUploadDataEntityBuilder) {
		this.id = snapshotUploadDataEntityBuilder.id;
		this.assetId = snapshotUploadDataEntityBuilder.assetId;
		this.elementId = snapshotUploadDataEntityBuilder.elementId;
		this.sourceId = snapshotUploadDataEntityBuilder.sourceId;
		this.readingDate = snapshotUploadDataEntityBuilder.readingDate;
		this.uploadRecordId = snapshotUploadDataEntityBuilder.uploadRecordId;
	}

	public SnapshotUploadDataEntity() {
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

	public Long getUploadRecordId() {
		return uploadRecordId;
	}

	public void setUploadRecordId(Long uploadRecordId) {
		this.uploadRecordId = uploadRecordId;
	}
	
	@Override
	public SnapshotUploadDataEntity merge(SnapshotUploadDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.readingDate = other.readingDate;
			this.uploadRecordId = other.uploadRecordId;
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
		SnapshotUploadDataEntity castOther = (SnapshotUploadDataEntity) other;
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
				.append("readingDate", readingDate).append("uploadRecordId", uploadRecordId).toString();
	}

	public static SnapshotUploadDataEntityBuilder builder() {
		return new SnapshotUploadDataEntityBuilder();
	}

	public static SnapshotUploadDataEntityBuilder builderFrom(SnapshotUploadDataEntity snapshotUploadDataEntity) {
		return new SnapshotUploadDataEntityBuilder(snapshotUploadDataEntity);
	}

	public static final class SnapshotUploadDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant readingDate;
		private Long uploadRecordId;

		private SnapshotUploadDataEntityBuilder() {
		}

		private SnapshotUploadDataEntityBuilder(SnapshotUploadDataEntity snapshotUploadDataEntity) {
			this.id = snapshotUploadDataEntity.id;
			this.assetId = snapshotUploadDataEntity.assetId;
			this.elementId = snapshotUploadDataEntity.elementId;
			this.sourceId = snapshotUploadDataEntity.sourceId;
			this.readingDate = snapshotUploadDataEntity.readingDate;
			this.uploadRecordId = snapshotUploadDataEntity.uploadRecordId;
		}

		public SnapshotUploadDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SnapshotUploadDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public SnapshotUploadDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public SnapshotUploadDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public SnapshotUploadDataEntityBuilder readingDate(Instant readingDate) {
			this.readingDate = readingDate;
			return this;
		}

		public SnapshotUploadDataEntityBuilder uploadRecordId(Long uploadRecordId) {
			this.uploadRecordId = uploadRecordId;
			return this;
		}

		public SnapshotUploadDataEntity build() {
			return new SnapshotUploadDataEntity(this);
		}
	}

}