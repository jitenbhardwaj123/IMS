package com.oak.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
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
import com.oak.common.validation.constraint.NoNullElements;
import com.oak.enums.MeasurementType;

@Entity
@Table(name = "element")
@SecondaryTable(name = "measurement_boolean_option", pkJoinColumns = @PrimaryKeyJoinColumn(name = "element_id", referencedColumnName = "id"))
public class ElementEntity extends Auditable implements Mergeable<ElementEntity, Long> {
	@Id
	@SequenceGenerator(name = "element_seq", sequenceName = "element_seq", allocationSize = 1)
	@GeneratedValue(generator = "element_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Element Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Element Description")
	private String description;

	@JoinColumn(name = "measurement_location_id")
	@OneToOne
	@IsNotNull("Measurement Location")
	private MeasurementLocationEntity measurementLocation;

	@JoinColumn(name = "unit_type_id")
	@OneToOne
	private UnitTypeEntity unitType;

	@Enumerated(STRING)
	@Column(name = "measurement_type")
	@IsNotNull("Measurement Type")
	private MeasurementType measurementType;

	@Embedded
	@AttributeOverride(name = "trueOption", column = @Column(table = "measurement_boolean_option", name = "true_option"))
	@AttributeOverride(name = "falseOption", column = @Column(table = "measurement_boolean_option", name = "false_option"))
	private MeasurementBooleanOptionEntity booleanOption;

	@ElementCollection
	@CollectionTable(name = "measurement_text_option", joinColumns = @JoinColumn(name = "element_id"))
	@Column(name = "option")
	@OrderColumn(name = "option_order")
	@NoNullElements("Measurement List Options")
	private List<String> textOptions = new ArrayList<>();

	private ElementEntity(ElementEntityBuilder elementEntityBuilder) {
		this.id = elementEntityBuilder.id;
		this.name = elementEntityBuilder.name;
		this.description = elementEntityBuilder.description;
		this.measurementLocation = elementEntityBuilder.measurementLocation;
		this.unitType = elementEntityBuilder.unitType;
		this.measurementType = elementEntityBuilder.measurementType;
		this.setBooleanOption(elementEntityBuilder.booleanOption);
		this.setTextOptions(elementEntityBuilder.textOptions);
	}

	public ElementEntity() {
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

	public MeasurementLocationEntity getMeasurementLocation() {
		return measurementLocation;
	}

	public void setMeasurementLocation(MeasurementLocationEntity measurementLocation) {
		this.measurementLocation = measurementLocation;
	}

	public UnitTypeEntity getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitTypeEntity unitType) {
		this.unitType = unitType;
	}

	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

	public MeasurementBooleanOptionEntity getBooleanOption() {
		return booleanOption;
	}

	public void setBooleanOption(MeasurementBooleanOptionEntity booleanOption) {
		this.booleanOption = booleanOption;
	}

	public List<String> getTextOptions() {
		return textOptions;
	}

	public void setTextOptions(List<String> textOptions) {
		this.textOptions.clear();
		if (isNotEmpty(textOptions)) {
			textOptions.forEach(this::addTextOption);
		}
	}

	public void addTextOption(String textOption) {
		this.textOptions.add(textOption);
	}

	@Override
	public ElementEntity merge(ElementEntity other) {
		if (other != null) {
			this.name = other.name;
			this.description = other.description;
			this.measurementLocation = other.measurementLocation;
			this.unitType = other.unitType;
			this.measurementType = other.measurementType;
			this.booleanOption = other.booleanOption;
			this.setTextOptions(other.textOptions);
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
		ElementEntity castOther = (ElementEntity) other;
		return new EqualsBuilder().append(name, castOther.name)
				.append(measurementLocation, castOther.measurementLocation).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(measurementLocation).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description)
				.append("measurementLocation", measurementLocation).append("unitType", unitType)
				.append("measurementType", measurementType).append("booleanOption", booleanOption)
				.append("textOptions", textOptions).toString();
	}

	public static ElementEntityBuilder builder() {
		return new ElementEntityBuilder();
	}

	public static ElementEntityBuilder builderFrom(ElementEntity elementEntity) {
		return new ElementEntityBuilder(elementEntity);
	}

	public static final class ElementEntityBuilder {
		private Long id;
		private String name;
		private String description;
		private MeasurementLocationEntity measurementLocation;
		private UnitTypeEntity unitType;
		private MeasurementType measurementType;
		private MeasurementBooleanOptionEntity booleanOption;
		private List<String> textOptions = Collections.emptyList();

		private ElementEntityBuilder() {
		}

		private ElementEntityBuilder(ElementEntity elementEntity) {
			this.id = elementEntity.id;
			this.name = elementEntity.name;
			this.description = elementEntity.description;
			this.measurementLocation = elementEntity.measurementLocation;
			this.unitType = elementEntity.unitType;
			this.measurementType = elementEntity.measurementType;
			this.booleanOption = elementEntity.booleanOption;
			this.textOptions = elementEntity.textOptions;
		}

		public ElementEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public ElementEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ElementEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public ElementEntityBuilder measurementLocation(MeasurementLocationEntity measurementLocation) {
			this.measurementLocation = measurementLocation;
			return this;
		}

		public ElementEntityBuilder unitType(UnitTypeEntity unitType) {
			this.unitType = unitType;
			return this;
		}

		public ElementEntityBuilder measurementType(MeasurementType measurementType) {
			this.measurementType = measurementType;
			return this;
		}

		public ElementEntityBuilder booleanOption(MeasurementBooleanOptionEntity booleanOption) {
			this.booleanOption = booleanOption;
			return this;
		}

		public ElementEntityBuilder textOptions(List<String> textOptions) {
			this.textOptions = textOptions;
			return this;
		}

		public ElementEntity build() {
			return new ElementEntity(this);
		}
	}

}