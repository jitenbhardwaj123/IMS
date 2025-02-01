package com.oak.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

public class ExcelSheetDataExtractor implements SheetContentsHandler {
	private final List<Class<?>> columnTypes;
	private List<ExcelRow> sheetData = new ArrayList<>();
	private Map<Integer, ExcelCell> rowData;
	private boolean firstRowOfSheet = true;

	public ExcelSheetDataExtractor(List<Class<?>> columnTypes) {
		super();
		this.columnTypes = columnTypes;
	}

	public List<ExcelRow> getSheetData() {
		return sheetData;
	}

	@Override
	public void startRow(int rowNum) {
		firstRowOfSheet = rowNum <= 0;
		rowData = new HashMap<>();
	}

	@Override
	public void endRow(int rowNum) {
		// ignore empty row
		long cellsWithData = rowData.values().stream()
				.map(ExcelCell::getRawValue)
				.filter(StringUtils::isNotBlank)
				.count();
		if (cellsWithData == 0) {
			return;
		}
		
		ExcelRow excelRow = new ExcelRow();
		excelRow.setReference(String.valueOf(rowNum + 1));
		for (int i = 0; i < columnTypes.size(); i++) {
			excelRow.addCell(rowData.getOrDefault(i, new ExcelCell()));
		}
		sheetData.add(excelRow);
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		// don't process header row
		if (firstRowOfSheet) {
			return;
		}

//		// gracefully handle missing CellRef here in a similar way as XSSFCell does
//		if (cellReference == null) {
//			cellReference = new CellAddress(currentRow, currentCol).formatAsString();
//		}

		final int currentCol = (new CellReference(cellReference)).getCol();
		
		// ignore any data outside of the expected columns
		if (currentCol >= columnTypes.size()) {
			return;
		}
		
		rowData.put(currentCol, new ExcelCell(cellReference, formattedValue));
	}
}