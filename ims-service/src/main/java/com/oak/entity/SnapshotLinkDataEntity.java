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
@Table(name = "snapshot_link_data")
public class SnapshotLinkDataEntity extends Auditable implements Mergeable<SnapshotLinkDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "snapshot_link_data_seq", sequenceName = "snapshot_link_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "snapshot_link_data_seq", strategy = SEQUENCE)
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

	@Column(name = "link_record_id")
	private Long linkRecordId;

	private SnapshotLinkDataEntity(SnapshotLinkDataEntityBuilder snapshotLinkDataEntityBuilder) {
		this.id = snapshotLinkDataEntityBuilder.id;
		this.assetId = snapshotLinkDataEntityBuilder.assetId;
		this.elementId = snapshotLinkDataEntityBuilder.elementId;
		this.sourceId = snapshotLinkDataEntityBuilder.sourceId;
		this.readingDate = snapshotLinkDataEntityBuilder.readingDate;
		this.linkRecordId = snapshotLinkDataEntityBuilder.linkRecordId;
	}

	public SnapshotLinkDataEntity() {
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

	public Long getLinkRecordId() {
		return linkRecordId;
	}

	public void setLinkRecordId(Long linkRecordId) {
		this.linkRecordId = linkRecordId;
	}
	
	@Override
	public SnapshotLinkDataEntity merge(SnapshotLinkDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.readingDate = other.readingDate;
			this.linkRecordId = other.linkRecordId;
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
		SnapshotLinkDataEntity castOther = (SnapshotLinkDataEntity) other;
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
				.append("readingDate", readingDate).append("linkRecordId", linkRecordId).toString();
	}

	public static SnapshotLinkDataEntityBuilder builder() {
		return new SnapshotLinkDataEntityBuilder();
	}

	public static SnapshotLinkDataEntityBuilder builderFrom(SnapshotLinkDataEntity snapshotLinkDataEntity) {
		return new SnapshotLinkDataEntityBuilder(snapshotLinkDataEntity);
	}

	public static final class SnapshotLinkDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant readingDate;
		private Long linkRecordId;

		private SnapshotLinkDataEntityBuilder() {
		}

		private SnapshotLinkDataEntityBuilder(SnapshotLinkDataEntity snapshotLinkDataEntity) {
			this.id = snapshotLinkDataEntity.id;
			this.assetId = snapshotLinkDataEntity.assetId;
			this.elementId = snapshotLinkDataEntity.elementId;
			this.sourceId = snapshotLinkDataEntity.sourceId;
			this.readingDate = snapshotLinkDataEntity.readingDate;
			this.linkRecordId = snapshotLinkDataEntity.linkRecordId;
		}

		public SnapshotLinkDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SnapshotLinkDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public SnapshotLinkDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public SnapshotLinkDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public SnapshotLinkDataEntityBuilder readingDate(Instant readingDate) {
			this.readingDate = readingDate;
			return this;
		}

		public SnapshotLinkDataEntityBuilder linkRecordId(Long linkRecordId) {
			this.linkRecordId = linkRecordId;
			return this;
		}

		public SnapshotLinkDataEntity build() {
			return new SnapshotLinkDataEntity(this);
		}
	}

}