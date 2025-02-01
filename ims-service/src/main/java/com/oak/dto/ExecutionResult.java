package com.oak.dto;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExecutionResult {
	private Instant applicableDate;
	private Instant endDate;
	private Map<String, Object> results = new HashMap<>();

	public Instant getApplicableDate() {
		return applicableDate;
	}

	public void setApplicableDate(Instant applicableDate) {
		this.applicableDate = applicableDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public Map<String, Object> getResults() {
		return results;
	}

	public void setResults(Map<String, Object> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
				.append("applicableDateMillis", applicableDate.toEpochMilli()).append("applicableDate", applicableDate).append("endDate", endDate).append("results", results)
				.toString();
	}
}