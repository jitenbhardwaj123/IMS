package com.oak.excel;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySortedSet;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ExcelRow {
	private String reference;
	private List<ExcelCell> cells;
	private SortedSet<String> errors;
	private static final String CELL_REF_MESSAGE = "[ Row: %s, Cell: %s ] %s";
	private static final String ROW_REF_MESSAGE = "[ Row: %s ] %s";
	
	
	public ExcelRow(String reference, List<ExcelCell> cells) {
		this.reference = reference;
		this.cells = cells;
	}

	public ExcelRow() {
	}

	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public List<ExcelCell> getCells() {
		return defaultIfNull(this.cells, emptyList());
	}

	public void setCells(List<ExcelCell> cells) {
		this.cells = cells;
	}
	
	public void addCell(ExcelCell cell) {
		if (this.cells == null) {
			this.cells = new ArrayList<>();
		}
		this.cells.add(cell);
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
				|| this.getCells().stream().anyMatch(ExcelCell :: hasErrors);
	}

	public SortedSet<String> getCumulativeErrors() {
		SortedSet<String> cErrors = new TreeSet<>(this.getErrors().stream().map(error -> format(ROW_REF_MESSAGE, this.getReference(), error)).collect(toSet()));
		for (ExcelCell cell: this.getCells()) {
			if (isNotBlank(cell.getReference())) {
				for (String error: cell.getErrors()) {
					cErrors.add(format(CELL_REF_MESSAGE, this.getReference(), cell.getReference(), error));
				}
			} else {
				for (String error: cell.getErrors()) {
					cErrors.add(format(ROW_REF_MESSAGE, this.getReference(), error));
				}
			}
		}
		return cErrors;
	}
}
