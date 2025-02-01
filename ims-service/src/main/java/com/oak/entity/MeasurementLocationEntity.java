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
@Table(name = "measurement_location")
public class MeasurementLocationEntity extends Auditable implements Mergeable<MeasurementLocationEntity, Long> {
	@Id
	@SequenceGenerator(name = "measurement_location_seq", sequenceName = "measurement_location_seq", allocationSize = 1)
	@GeneratedValue(generator = "measurement_location_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Measurement Location Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Measurement Location Description")
	private String description;

	public MeasurementLocationEntity() {
	}

	private MeasurementLocationEntity(MeasurementLocationEntityBuilder measurementLocationEntityBuilder) {
		this.id = measurementLocationEntityBuilder.id;
		this.name = measurementLocationEntityBuilder.name;
		this.description = measurementLocationEntityBuilder.description;
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
	public MeasurementLocationEntity merge(MeasurementLocationEntity other) {
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
		MeasurementLocationEntity other = (MeasurementLocationEntity) obj;
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).appendSuper(super.toString()).toString();
	}

	public static MeasurementLocationEntityBuilder builder() {
		return new MeasurementLocationEntityBuilder();
	}

	public static MeasurementLocationEntityBuilder builderFrom(MeasurementLocationEntity unitTypeEntity) {
		return new MeasurementLocationEntityBuilder(unitTypeEntity);
	}

	public static final class MeasurementLocationEntityBuilder {
		private Long id;
		private String name;
		private String description;

		private MeasurementLocationEntityBuilder() {
		}

		private MeasurementLocationEntityBuilder(MeasurementLocationEntity unitTypeEntity) {
			this.id = unitTypeEntity.id;
			this.name = unitTypeEntity.name;
			this.description = unitTypeEntity.description;
		}

		public MeasurementLocationEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public MeasurementLocationEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public MeasurementLocationEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public MeasurementLocationEntity build() {
			return new MeasurementLocationEntity(this);
		}
	}
}
