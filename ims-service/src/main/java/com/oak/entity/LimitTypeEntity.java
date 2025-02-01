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
@Table(name = "limit_type")
public class LimitTypeEntity extends Auditable implements Mergeable<LimitTypeEntity, Long> {
	@Id
	@SequenceGenerator(name = "limit_type_seq", sequenceName = "limit_type_seq", allocationSize = 1)
	@GeneratedValue(generator = "limit_type_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Limit Type Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Limit Type Description")
	private String description;

	public LimitTypeEntity() {
	}

	private LimitTypeEntity(LimitTypeEntityBuilder limitTypeEntityBuilder) {
		this.id = limitTypeEntityBuilder.id;
		this.name = limitTypeEntityBuilder.name;
		this.description = limitTypeEntityBuilder.description;
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
	public LimitTypeEntity merge(LimitTypeEntity other) {
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
		LimitTypeEntity other = (LimitTypeEntity) obj;
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).appendSuper(super.toString()).toString();
	}

	public static LimitTypeEntityBuilder builder() {
		return new LimitTypeEntityBuilder();
	}

	public static LimitTypeEntityBuilder builderFrom(LimitTypeEntity limitTypeEntity) {
		return new LimitTypeEntityBuilder(limitTypeEntity);
	}

	public static final class LimitTypeEntityBuilder {
		private Long id;
		private String name;
		private String description;

		private LimitTypeEntityBuilder() {
		}

		private LimitTypeEntityBuilder(LimitTypeEntity limitTypeEntity) {
			this.id = limitTypeEntity.id;
			this.name = limitTypeEntity.name;
			this.description = limitTypeEntity.description;
		}

		public LimitTypeEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public LimitTypeEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public LimitTypeEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public LimitTypeEntity build() {
			return new LimitTypeEntity(this);
		}
	}
}
