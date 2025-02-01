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
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;

@Entity
@Table(name = "limit")
public class LimitEntity extends Auditable implements Mergeable<LimitEntity, Long> {
	@Id
	@SequenceGenerator(name = "limit_seq", sequenceName = "limit_seq", allocationSize = 1)
	@GeneratedValue(generator = "limit_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Limit Name")
	private String name;

	@Column(name = "asset_id")
	@IsNotNull("Asset Id")
	private Long assetId;

	@Column(name = "element_id")
	@IsNotNull("Element Id")
	private Long elementId;

	@Column(name = "limit_type_id")
	@IsNotNull("Limit Type Id")
	private Long limitTypeId;

	@Column(name = "source_id")
	@IsNotNull("Source Id")
	private Long sourceId;

	@Column(name = "start_date")
	@IsNotNull("Start Date")
	private Instant startDate;

	@Column(name = "end_date")
	private Instant endDate;

	@Column(name = "lower_limit")
	private BigDecimal lowerLimit;

	@Column(name = "upper_limit")
	private BigDecimal upperLimit;

	@Column(name = "unit_id")
	private Long unitId;

	private LimitEntity(LimitEntityBuilder limitEntityBuilder) {
		this.id = limitEntityBuilder.id;
		this.name = limitEntityBuilder.name;
		this.assetId = limitEntityBuilder.assetId;
		this.elementId = limitEntityBuilder.elementId;
		this.limitTypeId = limitEntityBuilder.limitTypeId;
		this.sourceId = limitEntityBuilder.sourceId;
		this.startDate = limitEntityBuilder.startDate;
		this.endDate = limitEntityBuilder.endDate;
		this.lowerLimit = limitEntityBuilder.lowerLimit;
		this.upperLimit = limitEntityBuilder.upperLimit;
		this.unitId = limitEntityBuilder.unitId;
	}

	public LimitEntity() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getLimitTypeId() {
		return limitTypeId;
	}

	public void setLimitTypeId(Long limitTypeId) {
		this.limitTypeId = limitTypeId;
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

	public BigDecimal getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public BigDecimal getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(BigDecimal upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	@Override
	public LimitEntity merge(LimitEntity other) {
		if (other != null) {
			this.name = other.name;
			this.assetId = other.assetId;
			this.elementId = other.elementId;
			this.limitTypeId = other.limitTypeId;
			this.sourceId = other.sourceId;
			this.startDate = other.startDate;
			this.endDate = other.endDate;
			this.lowerLimit = other.lowerLimit;
			this.upperLimit = other.upperLimit;
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
		LimitEntity castOther = (LimitEntity) other;
		return new EqualsBuilder().append(assetId, castOther.assetId).append(elementId, castOther.elementId)
				.append(limitTypeId, castOther.limitTypeId).append(sourceId, castOther.sourceId).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(assetId).append(elementId).append(limitTypeId).append(sourceId)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("assetId", assetId).append("elementId", elementId)
				.append("limitTypeId", limitTypeId).append("sourceId", sourceId).append("startDate", startDate)
				.append("endDate", endDate).append("lowerLimit", lowerLimit).append("upperLimit", upperLimit)
				.append("unitId", unitId).toString();
	}

	public static LimitEntityBuilder builder() {
		return new LimitEntityBuilder();
	}

	public static LimitEntityBuilder builderFrom(LimitEntity limitEntity) {
		return new LimitEntityBuilder(limitEntity);
	}

	public static final class LimitEntityBuilder {
		private Long id;
		private String name;
		private Long assetId;
		private Long elementId;
		private Long limitTypeId;
		private Long sourceId;
		private Instant startDate;
		private Instant endDate;
		private BigDecimal lowerLimit;
		private BigDecimal upperLimit;
		private Long unitId;

		private LimitEntityBuilder() {
		}

		private LimitEntityBuilder(LimitEntity limitEntity) {
			this.id = limitEntity.id;
			this.name = limitEntity.name;
			this.assetId = limitEntity.assetId;
			this.elementId = limitEntity.elementId;
			this.limitTypeId = limitEntity.limitTypeId;
			this.sourceId = limitEntity.sourceId;
			this.startDate = limitEntity.startDate;
			this.endDate = limitEntity.endDate;
			this.lowerLimit = limitEntity.lowerLimit;
			this.upperLimit = limitEntity.upperLimit;
			this.unitId = limitEntity.unitId;
		}

		public LimitEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public LimitEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public LimitEntityBuilder assetId(Long assetId) {
			this.assetId = assetId;
			return this;
		}

		public LimitEntityBuilder elementId(Long elementId) {
			this.elementId = elementId;
			return this;
		}

		public LimitEntityBuilder limitTypeId(Long limitTypeId) {
			this.limitTypeId = limitTypeId;
			return this;
		}

		public LimitEntityBuilder sourceId(Long sourceId) {
			this.sourceId = sourceId;
			return this;
		}

		public LimitEntityBuilder startDate(Instant startDate) {
			this.startDate = startDate;
			return this;
		}

		public LimitEntityBuilder endDate(Instant endDate) {
			this.endDate = endDate;
			return this;
		}

		public LimitEntityBuilder lowerLimit(BigDecimal lowerLimit) {
			this.lowerLimit = lowerLimit;
			return this;
		}

		public LimitEntityBuilder upperLimit(BigDecimal upperLimit) {
			this.upperLimit = upperLimit;
			return this;
		}

		public LimitEntityBuilder unitId(Long unitId) {
			this.unitId = unitId;
			return this;
		}

		public LimitEntity build() {
			return new LimitEntity(this);
		}
	}

}