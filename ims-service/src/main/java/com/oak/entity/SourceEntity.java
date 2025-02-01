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
@Table(name = "source")
public class SourceEntity extends Auditable implements Mergeable<SourceEntity, Long> {
	@Id
	@SequenceGenerator(name = "source_seq", sequenceName = "source_seq", allocationSize = 1)
	@GeneratedValue(generator = "source_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Source Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Source Description")
	private String description;

	@Column(name = "manual_source")
	private boolean manualSource;

	@Column(name = "tag_based")
	private boolean tagBased;

	@Column(name = "editable")
	private boolean editable;

	public SourceEntity() {
	}

	private SourceEntity(SourceEntityBuilder sourceEntityBuilder) {
		this.id = sourceEntityBuilder.id;
		this.name = sourceEntityBuilder.name;
		this.description = sourceEntityBuilder.description;
		this.manualSource = sourceEntityBuilder.manualSource;
		this.tagBased = sourceEntityBuilder.tagBased;
		this.editable = sourceEntityBuilder.editable;
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

	public boolean isManualSource() {
		return manualSource;
	}

	public void setManualSource(boolean manualSource) {
		this.manualSource = manualSource;
	}

	public boolean isTagBased() {
		return tagBased;
	}

	public void setTagBased(boolean tagBased) {
		this.tagBased = tagBased;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	@Override
	public SourceEntity merge(SourceEntity other) {
		if (other != null) {
			this.name = other.name;
			this.description = other.description;
			this.manualSource = other.manualSource;
			this.tagBased = other.tagBased;
			this.editable = other.editable;
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
		SourceEntity other = (SourceEntity) obj;
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).append("manualSource", manualSource)
				.append("tagBased", tagBased).append("editable", editable).appendSuper(super.toString()).toString();
	}

	public static SourceEntityBuilder builder() {
		return new SourceEntityBuilder();
	}

	public static SourceEntityBuilder builderFrom(SourceEntity sourceEntity) {
		return new SourceEntityBuilder(sourceEntity);
	}

	public static final class SourceEntityBuilder {
		private Long id;
		private String name;
		private String description;
		private boolean manualSource;
		private boolean tagBased;
		private boolean editable;

		private SourceEntityBuilder() {
		}

		private SourceEntityBuilder(SourceEntity sourceEntity) {
			this.id = sourceEntity.id;
			this.name = sourceEntity.name;
			this.description = sourceEntity.description;
			this.manualSource = sourceEntity.manualSource;
			this.tagBased = sourceEntity.tagBased;
			this.editable = sourceEntity.editable;
		}

		public SourceEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SourceEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public SourceEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public SourceEntityBuilder manualSource(boolean manualSource) {
			this.manualSource = manualSource;
			return this;
		}

		public SourceEntityBuilder tagBased(boolean tagBased) {
			this.tagBased = tagBased;
			return this;
		}

		public SourceEntityBuilder editable(boolean editable) {
			this.editable = editable;
			return this;
		}

		public SourceEntity build() {
			return new SourceEntity(this);
		}
	}
}
