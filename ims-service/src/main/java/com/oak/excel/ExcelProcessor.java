package com.oak.excel;

import static java.lang.String.format;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.poi.ss.SpreadsheetVersion.EXCEL97;
import static org.apache.poi.ss.util.WorkbookUtil.createSafeSheetName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.crypt.CipherAlgorithm;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.oak.common.exception.model.UploadDatasheetException;

public class ExcelProcessor {
	private static final boolean ENABLE_EXCEL_PROTECTION = false;
	private static final String EXCEL_PWD = "DUMMY";
	private static final double MIN_INFLATE_RATIO_VALUE = 0.0001d;
	
	public ExcelSheetData importReportData(String sheetName, List<Class<?>> cellTypes, byte[] rawData) {
		try (InputStream inputStream = new ByteArrayInputStream(rawData);
				OPCPackage pkg = OPCPackage.open(inputStream)) {
            XSSFReader reader = new XSSFReader(pkg);
            SharedStringsTable strings = reader.getSharedStringsTable();
            StylesTable styles = reader.getStylesTable();

            XMLReader parser = XMLHelper.newXMLReader();
            ExcelSheetDataExtractor sheetContentsHandler = new ExcelSheetDataExtractor(cellTypes);
			ContentHandler contentsHandler = new XSSFSheetXMLHandler(styles, strings, sheetContentsHandler, false);
            parser.setContentHandler(contentsHandler);

            SheetIterator sheetIterator = (SheetIterator) reader.getSheetsData();
            while (sheetIterator.hasNext()) {
				InputSource sheetSource = new InputSource(sheetIterator.next());
				if (sheetIterator.getSheetName().equalsIgnoreCase(sheetName)) {
					parser.parse(sheetSource);
					return new ExcelSheetData(sheetName, sheetContentsHandler.getSheetData());
				}
            }
			throw new UploadDatasheetException(format("Error importing data. Unable to find sheet [ %s ]", sheetName));
		} catch (UploadDatasheetException e) {
			throw e;
		} catch (Exception e) {
			throw new UploadDatasheetException("Error importing data", e.getMessage());
		}
	}

	public byte[] generateWorkbook(String sheetName, List<ExcelColumn> headers) {
		ZipSecureFile.setMinInflateRatio(MIN_INFLATE_RATIO_VALUE);
		if (ENABLE_EXCEL_PROTECTION) {
			return generateProtectedTemplate(sheetName, headers);
		} else {
			return generateTemplate(sheetName, headers);
		}
	}

	private byte[] generateProtectedTemplate(String sheetName, List<ExcelColumn> headers) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(sheetName);
			createHeaderRows(workbook, headers, sheet);

			EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile, CipherAlgorithm.aes256, HashAlgorithm.sha512,
					-1, -1, null);
			Encryptor enc = info.getEncryptor();
			enc.confirmPassword(EXCEL_PWD);

			try (ByteArrayOutputStream fos = new ByteArrayOutputStream()) {
				try (POIFSFileSystem fs = new POIFSFileSystem()) {
					try (ByteArrayOutputStream workBookStream = new ByteArrayOutputStream()) {
						workbook.write(workBookStream);
						try (InputStream is = new ByteArrayInputStream(workBookStream.toByteArray())) {
							try (OPCPackage opc = OPCPackage.open(is)) {
								try (OutputStream encStream = enc.getDataStream(fs)) {
									opc.save(encStream);
								}
							}
						}
						fs.writeFilesystem(fos);
					}
				}
				return fos.toByteArray();
			}
		} catch (Exception e) {
			throw new RuntimeException(
					format("Error generating password private template for widget [ %s ] - %s", sheetName, e.getMessage()), e);
		}
	}

	private byte[] generateTemplate(String sheetName, List<ExcelColumn> headers) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(createSafeSheetName(sheetName, '-'));
			createHeaderRows(workbook, headers, sheet);

			try (ByteArrayOutputStream workBookStream = new ByteArrayOutputStream()) {
				workbook.write(workBookStream);
				return workBookStream.toByteArray();
			}
		} catch (Exception e) {
			throw new RuntimeException(format("Error generating template for widget [ %s ] - %s", sheetName, e.getMessage()), e);
		}
	}

	private int createHeaderRows(XSSFWorkbook workbook, List<ExcelColumn> headers, XSSFSheet sheet) {
		int rowIndex = 0;
		
		for (int i = 0; i < headers.size(); i++) {
			List<String> options = headers.get(i).getOptions();
			if (isNotEmpty(options)) {
				setDropdownOptions(sheet, rowIndex + 1, i, options);
			}
		}
		createHeaderRow(workbook, sheet, rowIndex, headers);

		// set column level default styles
//		((XSSFSheet)sheet).getColumnHelper().setColDefaultStyle(col, myStyle);

		return rowIndex;
	}

	private void setDropdownOptions(XSSFSheet sheet, int rowIndex, int colIndex, List<String> options) {
		DataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
		DataValidationConstraint constraint = validationHelper
				.createExplicitListConstraint(options.toArray(new String[0]));
		CellRangeAddressList addressList = new CellRangeAddressList(rowIndex, EXCEL97.getMaxRows(), colIndex, colIndex);
		DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
		dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		dataValidation.setSuppressDropDownArrow(true);
		dataValidation.setShowErrorBox(true);
		dataValidation.createErrorBox("Error!", "Please choose from the drop-down options");
		sheet.addValidationData(dataValidation);
	}

	private final void createHeaderRow(XSSFWorkbook workbook, Sheet sheet, int rowIndex, List<ExcelColumn> headers) {
		XSSFFont font = getHeaderFont(workbook);
		XSSFCellStyle optionalHeaderStyle = getOptionalHeaderStyle(workbook, font);
		XSSFCellStyle mandatoryHeaderStyle = getMandatoryHeaderStyle(workbook, font);
		Row currentRow = sheet.createRow(rowIndex);
		int cellIndex = 0;
		for (ExcelColumn header : headers) {
			Cell cell = currentRow.createCell(cellIndex);
			cell.setCellStyle(header.isMandatory() ? mandatoryHeaderStyle : optionalHeaderStyle);
			cell.setCellValue(header.getHeader());
            sheet.autoSizeColumn(cellIndex);
			XSSFCellStyle textStyle = workbook.createCellStyle();
			textStyle.setDataFormat(BuiltinFormats.getBuiltinFormat("TEXT"));
			sheet.setDefaultColumnStyle(cellIndex, textStyle);
			cellIndex++;
		}
	}

	private XSSFFont getHeaderFont(XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Calibri");
        font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(true);
		return font;
	}

	private XSSFCellStyle getOptionalHeaderStyle(XSSFWorkbook workbook, XSSFFont font) {
		XSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		style.setFont(font);
		style.setWrapText(true);

		return style;
	}
	
	private XSSFCellStyle getMandatoryHeaderStyle(XSSFWorkbook workbook, XSSFFont font) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.ORANGE.index);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		
		style.setFont(font);
		style.setWrapText(true);
		
		return style;
	}
}
