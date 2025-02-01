package com.oak.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;

@Entity
@Table(name = "unit_type")
public class UnitTypeEntity extends Auditable implements Mergeable<UnitTypeEntity, Long> {
	@Id
	@SequenceGenerator(name = "unit_type_seq", sequenceName = "unit_type_seq", allocationSize = 1)
	@GeneratedValue(generator = "unit_type_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Unit Type Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Unit Type Description")
	private String description;

	public UnitTypeEntity() {
	}

	private UnitTypeEntity(UnitTypeEntityBuilder unitTypeEntityBuilder) {
		this.id = unitTypeEntityBuilder.id;
		this.name = unitTypeEntityBuilder.name;
		this.description = unitTypeEntityBuilder.description;
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
	
	@Override
	public UnitTypeEntity merge(UnitTypeEntity other) {
		if (other != null) {
			this.name = other.name;
			this.description = other.description;
		}
		return this;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		UnitTypeEntity other = (UnitTypeEntity) obj;
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).appendSuper(super.toString()).toString();
	}

	public static UnitTypeEntityBuilder builder() {
		return new UnitTypeEntityBuilder();
	}

	public static UnitTypeEntityBuilder builderFrom(UnitTypeEntity unitTypeEntity) {
		return new UnitTypeEntityBuilder(unitTypeEntity);
	}

	public static final class UnitTypeEntityBuilder {
		private Long id;
		private String name;
		private String description;

		private UnitTypeEntityBuilder() {
		}

		private UnitTypeEntityBuilder(UnitTypeEntity unitTypeEntity) {
			this.id = unitTypeEntity.id;
			this.name = unitTypeEntity.name;
			this.description = unitTypeEntity.description;
		}

		public UnitTypeEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public UnitTypeEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public UnitTypeEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public UnitTypeEntity build() {
			return new UnitTypeEntity(this);
		}
	}
}
