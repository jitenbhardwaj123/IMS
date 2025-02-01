package com.oak.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.IsNotNull;

@Entity
@Table(name = "asset")
public class AssetEntity extends Auditable implements Mergeable<AssetEntity, Long> {
	@Id
	@SequenceGenerator(name = "asset_seq", sequenceName = "asset_seq", allocationSize = 1)
	@GeneratedValue(generator = "asset_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Asset Name")
	private String name;

	@JoinColumn(name = "asset_type_id")
	@OneToOne
	@IsNotNull("Asset Type")
	private AssetTypeEntity assetType;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_asset_id", insertable = false, updatable = false)
	private AssetEntity parent;

	@OneToMany(cascade = ALL, orphanRemoval = true)
	@OrderColumn(name = "child_order")
	@JoinColumn(name = "parent_asset_id")
	private List<AssetEntity> children = new ArrayList<>();

	private AssetEntity(AssetEntityBuilder assetEntityBuilder) {
		this.id = assetEntityBuilder.id;
		this.name = assetEntityBuilder.name;
		this.assetType = assetEntityBuilder.assetType;
		this.parent = assetEntityBuilder.parent;
		this.setChildren(assetEntityBuilder.children);
	}

	public AssetEntity() {
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

	public AssetTypeEntity getAssetType() {
		return assetType;
	}

	public void setAssetType(AssetTypeEntity assetType) {
		this.assetType = assetType;
	}

	public AssetEntity getParent() {
		return parent;
	}

	public void setParent(AssetEntity parent) {
		this.parent = parent;
	}

	public List<AssetEntity> getChildren() {
		return children;
	}

	public void setChildren(List<AssetEntity> children) {
		this.children.clear();
		if (isNotEmpty(children)) {
			children.forEach(this::addChild);
		}
	}

	public void addChild(AssetEntity child) {
		child.setParent(this);
		this.children.add(child);
	}
	
	@Override
	public AssetEntity merge(AssetEntity other) {
		if (other != null) {
			this.name = other.name;
			this.assetType = other.assetType;
			this.parent = other.parent;
			// Only the immediate children are merged
			List<AssetEntity> crn = new ArrayList<>();
			other.children.forEach(child -> {
				if (child.getId() != null) {
					crn.add(this.mergeChild(child));
				} else {
					crn.add(child);
				}
			});
			this.setChildren(crn);
		}
		return this;
	}
	
	private AssetEntity mergeChild(AssetEntity other) {
		Optional<AssetEntity> original = this.children.stream().filter(c -> c.id.equals(other.id)).findFirst();
		if (original.isPresent()) {
			AssetEntity merged = original.get();
			merged.name = other.name;
			merged.setAssetType(other.assetType);
			return merged;
		}
		return other;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("assetType", assetType).append("children", children).toString();
	}

	public static AssetEntityBuilder builder() {
		return new AssetEntityBuilder();
	}

	public static AssetEntityBuilder builderFrom(AssetEntity assetEntity) {
		return new AssetEntityBuilder(assetEntity);
	}

	public static final class AssetEntityBuilder {
		private Long id;
		private String name;
		private AssetTypeEntity assetType;
		private AssetEntity parent;
		private List<AssetEntity> children = Collections.emptyList();

		private AssetEntityBuilder() {
		}

		private AssetEntityBuilder(AssetEntity assetEntity) {
			this.id = assetEntity.id;
			this.name = assetEntity.name;
			this.assetType = assetEntity.assetType;
			this.parent = assetEntity.parent;
			this.children = assetEntity.children;
		}

		public AssetEntityBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public AssetEntityBuilder name(String name) {
			this.name = name;
			return this;
		}

		public AssetEntityBuilder assetType(AssetTypeEntity assetType) {
			this.assetType = assetType;
			return this;
		}

		public AssetEntityBuilder parent(AssetEntity parent) {
			this.parent = parent;
			return this;
		}

		public AssetEntityBuilder children(List<AssetEntity> children) {
			this.children = children;
			return this;
		}

		public AssetEntity build() {
			return new AssetEntity(this);
		}
	}
}