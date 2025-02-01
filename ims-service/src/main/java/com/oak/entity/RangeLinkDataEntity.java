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
@Table(name = "range_link_data")
public class RangeLinkDataEntity extends Auditable implements Mergeable<RangeLinkDataEntity, Long> {
	@Id
	@SequenceGenerator(name = "range_link_data_seq", sequenceName = "range_link_data_seq", allocationSize = 1)
	@GeneratedValue(generator = "range_link_data_seq", strategy = SEQUENCE)
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

	@Column(name = "link_record_id")
	private Long linkRecordId;

	private RangeLinkDataEntity(RangeLinkDataEntityBuilder rangeLinkDataEntityBuilder) {
		this.id = rangeLinkDataEntityBuilder.id;
		this.assetId = rangeLinkDataEntityBuilder.assetId;
		this.elementId = rangeLinkDataEntityBuilder.elementId;
		this.sourceId = rangeLinkDataEntityBuilder.sourceId;
		this.startDate = rangeLinkDataEntityBuilder.startDate;
		this.endDate = rangeLinkDataEntityBuilder.endDate;
		this.linkRecordId = rangeLinkDataEntityBuilder.linkRecordId;
	}

	public RangeLinkDataEntity() {
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

	public Long getLinkRecordId() {
		return linkRecordId;
	}

	public void setLinkRecordId(Long linkRecordId) {
		this.linkRecordId = linkRecordId;
	}
	
	@Override
	public RangeLinkDataEntity merge(RangeLinkDataEntity other) {
		if (other != null) {
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.sourceId = other.sourceId;
			this.startDate = other.startDate;
			this.endDate = other.endDate;
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
		RangeLinkDataEntity castOther = (RangeLinkDataEntity) other;
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
				.append("startDate", startDate).append("endDate", endDate).append("linkRecordId", linkRecordId)
				.toString();
	}

	public static RangeLinkDataEntityBuilder builder() {
		return new RangeLinkDataEntityBuilder();
	}

	public static RangeLinkDataEntityBuilder builderFrom(RangeLinkDataEntity rangeLinkDataEntity) {
		return new RangeLinkDataEntityBuilder(rangeLinkDataEntity);
	}

	public static final class RangeLinkDataEntityBuilder {
		private Long id;
		private Long assetId;
		private Long elementId;
		private Long sourceId;
		private Instant startDate;
		private Instant endDate;
		private Long linkRecordId;

		private RangeLinkDataEntityBuilder() {
		}

		private RangeLinkDataEntityBuilder(RangeLinkDataEntity rangeLinkDataEntity) {
			this.id = rangeLinkDataEntity.id;
			this.assetId = rangeLinkDataEntity.assetId;
			this.elementId = rangeLinkDataEntity.elementId;
			this.sourceId = rangeLinkDataEntity.sourceId;
			this.startDate = rangeLinkDataEntity.startDate;
			this.endDate = rangeLinkDataEntity.endDate;
			this.linkRecordId = rangeLinkDataEntity.linkRecordId;
		}

		public RangeLinkDataEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public RangeLinkDataEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public RangeLinkDataEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public RangeLinkDataEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public RangeLinkDataEntityBuilder startDate(Instant startDate) {
			this.startDate = startDate;
			return this;
		}

		public RangeLinkDataEntityBuilder endDate(Instant endDate) {
			this.endDate = endDate;
			return this;
		}

		public RangeLinkDataEntityBuilder linkRecordId(Long linkRecordId) {
			this.linkRecordId = linkRecordId;
			return this;
		}

		public RangeLinkDataEntity build() {
			return new RangeLinkDataEntity(this);
		}
	}
}