package com.easysoft.sma.domain.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import com.easysoft.sma.domain.service.ExcelService;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExcelServiceImpl implements ExcelService {

	private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

	private String exportDirectory;

	private String importDirectory;

	private String getDirectory(String name) {
		ApplicationHome home = new ApplicationHome(getClass());
		String directoryPath = home.getSource().getParentFile().toString() + "\\" + name;
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdir();
		}
		return directoryPath;
	}

	private String getExportDirectory() {

		if (StringUtils.hasText(this.exportDirectory)) {
			return this.exportDirectory;
		}

		this.exportDirectory = this.getDirectory("export");
		return this.exportDirectory;
	}

	@Override
	public String getImportFilePath(String fileName) {

		if (!StringUtils.hasText(this.importDirectory)) {

			this.importDirectory = this.getDirectory("import");
		}

		return this.importDirectory + "\\" + fileName;
	}

	@Override
	public boolean checkImportFile(String fileName) {

		String suffix = fileName.substring(fileName.indexOf('.'));
		if (".xls".equalsIgnoreCase(suffix) || ".xlsx".equalsIgnoreCase(suffix)) {
			return true;
		}
		return false;
	}

	@Override
	public String generateFileName() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Override
	public String getFilePath(String fileName) {
		return this.getExportDirectory() + "\\" + fileName + ".xlsx";
	}


	@Override
	public SXSSFWorkbook createWorkbook(int rowAccessWindowSize) {
		return new SXSSFWorkbook(rowAccessWindowSize);
	}

	@Override
	public SXSSFSheet createSheet(SXSSFWorkbook workbook, String sheetName) {
		return workbook.createSheet(sheetName);
	}

	@Override
	public void createRow(SXSSFSheet sheet, int rowIndex, List<String> values) {
		SXSSFRow row = sheet.createRow(rowIndex);
		for (int i = 0; i < values.size(); i++) {
			SXSSFCell cell = row.createCell(i);
			cell.setCellValue(values.get(i));
		}
	}

	@Override
	public void saveFile(SXSSFWorkbook workbook, String fileName) {
		try {
			FileOutputStream stream = new FileOutputStream(this.getFilePath(fileName));
			workbook.write(stream);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public SXSSFWorkbook readFile(String filePath, int rowAccessWindowSize) {
		try {
			File file = ResourceUtils.getFile(filePath);
			InputStream stream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(stream);
			return new SXSSFWorkbook(workbook, rowAccessWindowSize);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}
