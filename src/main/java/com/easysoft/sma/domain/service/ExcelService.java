package com.easysoft.sma.domain.service;

import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface ExcelService {

	public String generateFileName();

	public SXSSFWorkbook createWorkbook(int rowAccessWindowSize);

	public SXSSFSheet createSheet(SXSSFWorkbook workbook, String sheetName);

	public void createRow(SXSSFSheet sheet, int rowIndex, List<String> values);

	public void saveFile(SXSSFWorkbook workbook, String fileName);

	public String getFilePath(String fileName);

	public String getImportFilePath(String fileName);

	public boolean checkImportFile(String fileName);

	public SXSSFWorkbook readFile(String filePath, int rowAccessWindowSize);
}