package com.oak.excel;

import java.util.List;

public class ExcelColumn {
	private final String header;
	private final boolean mandatory;
	private final List<String> options;
	
	public ExcelColumn(String header) {
		this.header = header;
		this.mandatory = false;
		this.options = null;
	}
	public ExcelColumn(String header, boolean mandatory) {
		this.header = header;
		this.mandatory = mandatory;
		this.options = null;
	}
	public ExcelColumn(String header, boolean mandatory, List<String> options) {
		this.header = header;
		this.mandatory = mandatory;
		this.options = options;
	}

	public String getHeader() {
		return header;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	
	public List<String> getOptions() {
		return options;
	}
}
