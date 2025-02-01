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
@Table(name = "range_upload_data")
public class RangeUploadDataEntity extends Auditable implements Mergeable<RangeUploadDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "range_upload_data_seq", sequenceName = "range_upload_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "range_upload_data_seq", strategy = SEQUENCE)
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

	@Column(name = "upload_record_id")
	private Long uploadRecordId;

	private RangeUploadDataEntity(RangeUploadDataEntityBuilder rangeUploadDataEntityBuilder) {
		this.id = rangeUploadDataEntityBuilder.id;
		this.assetId = rangeUploadDataEntityBuilder.assetId;
		this.elementId = rangeUploadDataEntityBuilder.elementId;
		this.sourceId = rangeUploadDataEntityBuilder.sourceId;
		this.startDate = rangeUploadDataEntityBuilder.startDate;
		this.endDate = rangeUploadDataEntityBuilder.endDate;
		this.uploadRecordId = rangeUploadDataEntityBuilder.uploadRecordId;
	}

	public RangeUploadDataEntity() {
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

	public Long getUploadRecordId() {
		return uploadRecordId;
	}

	public void setUploadRecordId(Long uploadRecordId) {
		this.uploadRecordId = uploadRecordId;
	}
	
	@Override
	public RangeUploadDataEntity merge(RangeUploadDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.startDate = other.startDate;
			this.endDate = other.endDate;
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
		RangeUploadDataEntity castOther = (RangeUploadDataEntity) other;
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
				.append("startDate", startDate).append("endDate", endDate).append("uploadRecordId", uploadRecordId)
				.toString();
	}

	public static RangeUploadDataEntityBuilder builder() {
		return new RangeUploadDataEntityBuilder();
	}

	public static RangeUploadDataEntityBuilder builderFrom(RangeUploadDataEntity rangeUploadDataEntity) {
		return new RangeUploadDataEntityBuilder(rangeUploadDataEntity);
	}

	public static final class RangeUploadDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant startDate;
		private Instant endDate;
		private Long uploadRecordId;

		private RangeUploadDataEntityBuilder() {
		}

		private RangeUploadDataEntityBuilder(RangeUploadDataEntity rangeUploadDataEntity) {
			this.id = rangeUploadDataEntity.id;
			this.assetId = rangeUploadDataEntity.assetId;
			this.elementId = rangeUploadDataEntity.elementId;
			this.sourceId = rangeUploadDataEntity.sourceId;
			this.startDate = rangeUploadDataEntity.startDate;
			this.endDate = rangeUploadDataEntity.endDate;
			this.uploadRecordId = rangeUploadDataEntity.uploadRecordId;
		}

		public RangeUploadDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public RangeUploadDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public RangeUploadDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public RangeUploadDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public RangeUploadDataEntityBuilder startDate(Instant startDate) {
			this.startDate = startDate;
			return this;
		}

		public RangeUploadDataEntityBuilder endDate(Instant endDate) {
			this.endDate = endDate;
			return this;
		}

		public RangeUploadDataEntityBuilder uploadRecordId(Long uploadRecordId) {
			this.uploadRecordId = uploadRecordId;
			return this;
		}

		public RangeUploadDataEntity build() {
			return new RangeUploadDataEntity(this);
		}
	}
}