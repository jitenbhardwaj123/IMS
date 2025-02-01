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
@Table(name = "asset_type")
public class AssetTypeEntity extends Auditable implements Mergeable<AssetTypeEntity, Long> {
	@Id
	@SequenceGenerator(name = "asset_type_seq", sequenceName = "asset_type_seq", allocationSize = 1)
	@GeneratedValue(generator = "asset_type_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Asset Type Name")
	private String name;

	@Column(name = "description")
	@IsNotBlank("Asset Type Description")
	private String description;

	public AssetTypeEntity() {
	}

	private AssetTypeEntity(AssetTypeEntityBuilder assetTypeEntityBuilder) {
		this.id = assetTypeEntityBuilder.id;
		this.name = assetTypeEntityBuilder.name;
		this.description = assetTypeEntityBuilder.description;
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
	public AssetTypeEntity merge(AssetTypeEntity other) {
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
		AssetTypeEntity other = (AssetTypeEntity) obj;
		return new EqualsBuilder().append(this.name, other.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("description", description).appendSuper(super.toString()).toString();
	}

	public static AssetTypeEntityBuilder builder() {
		return new AssetTypeEntityBuilder();
	}

	public static AssetTypeEntityBuilder builderFrom(AssetTypeEntity assetTypeEntity) {
		return new AssetTypeEntityBuilder(assetTypeEntity);
	}

	public static final class AssetTypeEntityBuilder {
		private Long id;
		private String name;
		private String description;

		private AssetTypeEntityBuilder() {
		}

		private AssetTypeEntityBuilder(AssetTypeEntity assetTypeEntity) {
			this.id = assetTypeEntity.id;
			this.name = assetTypeEntity.name;
			this.description = assetTypeEntity.description;
		}

		public AssetTypeEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public AssetTypeEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public AssetTypeEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public AssetTypeEntity build() {
			return new AssetTypeEntity(this);
		}
	}
}
