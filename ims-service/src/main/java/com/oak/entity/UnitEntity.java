package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "unit")
public class UnitEntity extends Auditable implements Mergeable<UnitEntity, Long> {
	@Id
	@SequenceGenerator(name = "unit_seq", sequenceName = "unit_seq", allocationSize = 1)
	@GeneratedValue(generator = "unit_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Unit Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Unit Description")
	private String description;

	@JoinColumn(name = "unit_type_id")
	@OneToOne
	@IsNotNull("Unit Type")
	private UnitTypeEntity unitType;

	private UnitEntity(UnitEntityBuilder unitEntityBuilder) {
		this.id = unitEntityBuilder.id;
		this.name = unitEntityBuilder.name;
		this.description = unitEntityBuilder.description;
		this.unitType = unitEntityBuilder.unitType;
	}

	public UnitEntity() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UnitTypeEntity getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitTypeEntity unitType) {
		this.unitType = unitType;
	}
	
	@Override
	public UnitEntity merge(UnitEntity other) {
		if (other != null) {
			this.name = other.name;
			this.description = other.description;
			this.unitType = other.unitType;
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
		UnitEntity castOther = (UnitEntity) other;
		return new EqualsBuilder().append(name, castOther.name).append(unitType, castOther.unitType).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(unitType).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).append("unitType", unitType).toString();
	}

	public static UnitEntityBuilder builder() {
		return new UnitEntityBuilder();
	}

	public static UnitEntityBuilder builderFrom(UnitEntity unitEntity) {
		return new UnitEntityBuilder(unitEntity);
	}

	public static final class UnitEntityBuilder {
		private Long id;
		private String name;
		private String description;
		private UnitTypeEntity unitType;

		private UnitEntityBuilder() {
		}

		private UnitEntityBuilder(UnitEntity unitEntity) {
			this.id = unitEntity.id;
			this.name = unitEntity.name;
			this.description = unitEntity.description;
			this.unitType = unitEntity.unitType;
		}

		public UnitEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public UnitEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public UnitEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public UnitEntityBuilder unitType(UnitTypeEntity unitType) {
			this.unitType = unitType;
			return this;
		}

		public UnitEntity build() {
			return new UnitEntity(this);
		}
	}
}