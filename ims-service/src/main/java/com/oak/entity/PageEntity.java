package com.oak.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.oak.common.orm.entity.Auditable;
import com.oak.common.orm.entity.Mergeable;
import com.oak.common.validation.constraint.IsNotBlank;
import com.oak.common.validation.constraint.NotEmptyAndNoNullElements;

@Entity
@Table(name = "page")
public class PageEntity extends Auditable implements Mergeable<PageEntity, Long> {
	@Id
	@SequenceGenerator(name = "page_seq", sequenceName = "page_seq", allocationSize = 1)
	@GeneratedValue(generator = "page_seq", strategy = SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@IsNotBlank("Page Name")
	private String name;

	@Column(name = "dashboard")
	private boolean dashboard;

	@OneToMany
	@JoinTable(name = "page_asset_type", joinColumns = {
			@JoinColumn(name = "page_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "asset_type_id") })
	@NotEmptyAndNoNullElements("Page Asset Types")
	private List<AssetTypeEntity> assetTypes = new ArrayList<>();

	@OneToMany
	@JoinTable(name = "page_widget", joinColumns = {
			@JoinColumn(name = "page_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "widget_id") })
	@OrderColumn(name = "widget_order")
	private List<WidgetEntity> widgets = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_page_id", insertable = false, updatable = false)
	private PageEntity parent;

	@OneToMany(cascade = ALL, orphanRemoval = true)
	@OrderColumn(name = "page_order")
	@JoinColumn(name = "parent_page_id")
	private List<PageEntity> children = new ArrayList<>();

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

	public boolean isDashboard() {
		return dashboard;
	}

	public void setDashboard(boolean dashboard) {
		this.dashboard = dashboard;
	}

	public List<AssetTypeEntity> getAssetTypes() {
		return assetTypes;
	}

	public void setAssetTypes(List<AssetTypeEntity> assetTypes) {
		this.assetTypes.clear();
		if (isNotEmpty(assetTypes)) {
			this.assetTypes.addAll(assetTypes);
		}
	}

	public List<WidgetEntity> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<WidgetEntity> widgets) {
		this.widgets.clear();
		if (isNotEmpty(widgets)) {
			this.widgets.addAll(widgets);
		}
	}

	public PageEntity getParent() {
		return parent;
	}

	public void setParent(PageEntity parent) {
		this.parent = parent;
	}

	public List<PageEntity> getChildren() {
		return children;
	}

	public void setChildren(List<PageEntity> children) {
		this.children.clear();
		if (isNotEmpty(children)) {
			this.children.addAll(children);
		}
	}

	@Override
	public PageEntity merge(PageEntity other) {
		if (other != null) {
			this.name = other.name;
			this.dashboard = other.dashboard;
			this.setAssetTypes(other.assetTypes);
			this.setWidgets(other.widgets);
			this.parent = other.parent;
			// Only the immediate children are merged
			List<PageEntity> crn = new ArrayList<>();
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
	
	private PageEntity mergeChild(PageEntity other) {
		Optional<PageEntity> original = this.children.stream().filter(c -> c.id.equals(other.id)).findFirst();
		if (original.isPresent()) {
			PageEntity merged = original.get();
			merged.name = other.name;
			merged.dashboard = other.dashboard;
			merged.setAssetTypes(other.assetTypes);
			merged.setWidgets(other.widgets);
			return merged;
		}
		return other;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("id", id)
				.append("name", name).append("dashboard", dashboard).append("assetTypes", assetTypes)
				.append("widgets", widgets).append("children", children).toString();
	}
}
