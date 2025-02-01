package com.oak.entity;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class AssetFilterEntity {
	private boolean assetFilterEnabled;

	public boolean isAssetFilterEnabled() {
		return assetFilterEnabled;
	}

	public void setAssetFilterEnabled(boolean assetFilterEnabled) {
		this.assetFilterEnabled = assetFilterEnabled;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("assetFilterEnabled", assetFilterEnabled).toString();
	}
}
