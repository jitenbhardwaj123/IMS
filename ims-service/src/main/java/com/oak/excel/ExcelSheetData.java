package com.oak.excel;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySortedSet;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ExcelSheetData {
	private String sheetName;
	private List<ExcelRow> rows;
	private SortedSet<String> errors;
	
	public ExcelSheetData(String sheetName, List<ExcelRow> rows) {
		this.sheetName = sheetName;
		this.rows = rows;
	}

	public String getSheetName() {
		return sheetName;
	}
	
	public void setSheetName(String reference) {
		this.sheetName = reference;
	}
	
	public List<ExcelRow> getRows() {
		return defaultIfNull(this.rows, emptyList());
	}

	public void setRows(List<ExcelRow> rows) {
		this.rows = rows;
	}
	
	public void addRow(ExcelRow row) {
		if (this.rows == null) {
			this.rows = new ArrayList<>();
		}
		this.rows.add(row);
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
		return (!this.getErrors().isEmpty()) 
				|| this.getRows().stream().anyMatch(ExcelRow :: hasErrors);
	}

	public SortedSet<String> getCumulativeErrors() {
		SortedSet<String> cErrors = new TreeSet<>(this.getErrors());
		this.getRows().forEach(row -> cErrors.addAll(row.getCumulativeErrors()));
		return cErrors;
	}
}