package com.oak.entity;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class ElementFilterEntity {
	private boolean elementFilterEnabled;

	public boolean isElementFilterEnabled() {
		return elementFilterEnabled;
	}

	public void setElementFilterEnabled(boolean elementFilterEnabled) {
		this.elementFilterEnabled = elementFilterEnabled;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("elementFilterEnabled", elementFilterEnabled).toString();
	}
}
