package com.oak.dto;

import java.time.Instant;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AuditableDto {
	@JsonIgnore
	protected Instant dateCreated;
	
	@JsonIgnore
	protected Instant lastUpdated;

	@JsonProperty
	public Instant getDateCreated() {
		return dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonProperty
	public Instant getLastUpdated() {
		return lastUpdated;
	}

	@JsonIgnore
	public void setLastUpdated(Instant lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString())
				.append("dateCreated", dateCreated).append("lastUpdated", lastUpdated).toString();
	}
}
