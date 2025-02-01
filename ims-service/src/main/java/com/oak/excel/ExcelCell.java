package com.oak.excel;

import static java.util.Collections.emptySortedSet;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.util.SortedSet;
import java.util.TreeSet;

public class ExcelCell {
	private String reference;
	private String rawValue;
	private Object parsedValue;
	private SortedSet<String> errors;
	
	public ExcelCell() {
	}

	public ExcelCell(String reference, String rawValue) {
		this.reference = reference;
		this.rawValue = rawValue;
	}

	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getRawValue() {
		return rawValue;
	}
	
	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}
	
	public Object getParsedValue() {
		return parsedValue;
	}

	public void setParsedValue(Object parsedValue) {
		this.parsedValue = parsedValue;
	}

	public SortedSet<String> getErrors() {
		return defaultIfNull(this.errors, emptySortedSet());
	}
	
	public void setErrors(SortedSet<String> errors) {
		this.errors = errors;
	}
	
	public void addError(String error) {
		if (this.errors == null) {
			this.errors = new TreeSet<>();
		}
		this.errors.add(error);
	}
	
	public boolean hasErrors() {
		return ! this.getErrors().isEmpty();
	}
}
