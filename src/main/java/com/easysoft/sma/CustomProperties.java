package com.easysoft.sma;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:custome.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@ConfigurationProperties(prefix = "com.easysoft.sma")
public class CustomProperties {

	private int jpaBatchPageSize;

	private int excelRowAccessWindowSize;

	private int importPageSize;

	private String importPostIdColumn;

	private String importConsigneeTelephoneColumn;
	
	private String importSpecColumn;

	private int exportPageSize;

	private String defaultSender;

	private String defaultSenderTelephone;

	private String defaultSenderAddress;

	private String defaultConsignee;

	private String defaultConsigneeTelephone;

	private String defaultConsigneeAddress;

	public int getJpaBatchPageSize() {
		return jpaBatchPageSize;
	}

	public void setJpaBatchPageSize(int jpaBatchPageSize) {
		this.jpaBatchPageSize = jpaBatchPageSize;
	}

	public int getImportPageSize() {
		return importPageSize;
	}

	public void setImportPageSize(int importPageSize) {
		this.importPageSize = importPageSize;
	}

	public int getExportPageSize() {
		return exportPageSize;
	}

	public void setExportPageSize(int exportPageSize) {
		this.exportPageSize = exportPageSize;
	}

	public int getExcelRowAccessWindowSize() {
		return excelRowAccessWindowSize;
	}

	public void setExcelRowAccessWindowSize(int exportRowAccessWindowSize) {
		this.excelRowAccessWindowSize = exportRowAccessWindowSize;
	}

	public String getDefaultSender() {
		return defaultSender;
	}

	public void setDefaultSender(String defaultSender) {
		this.defaultSender = defaultSender;
	}

	public String getDefaultSenderTelephone() {
		return defaultSenderTelephone;
	}

	public void setDefaultSenderTelephone(String defaultSenderTelephone) {
		this.defaultSenderTelephone = defaultSenderTelephone;
	}

	public String getDefaultConsignee() {
		return defaultConsignee;
	}

	public void setDefaultConsignee(String defaultConsignee) {
		this.defaultConsignee = defaultConsignee;
	}

	public String getDefaultConsigneeTelephone() {
		return defaultConsigneeTelephone;
	}

	public void setDefaultConsigneeTelephone(String defaultConsigneeTelephone) {
		this.defaultConsigneeTelephone = defaultConsigneeTelephone;
	}

	public String getDefaultConsigneeAddress() {
		return defaultConsigneeAddress;
	}

	public void setDefaultConsigneeAddress(String defaultConsigneeAddress) {
		this.defaultConsigneeAddress = defaultConsigneeAddress;
	}

	public String getDefaultSenderAddress() {
		return defaultSenderAddress;
	}

	public void setDefaultSenderAddress(String defaultSenderAddress) {
		this.defaultSenderAddress = defaultSenderAddress;
	}

	public String getImportPostIdColumn() {
		return importPostIdColumn;
	}

	public void setImportPostIdColumn(String importPostIdColumn) {
		this.importPostIdColumn = importPostIdColumn;
	}

	public String getImportConsigneeTelephoneColumn() {
		return importConsigneeTelephoneColumn;
	}

	public void setImportConsigneeTelephoneColumn(String importConsigneeTelephoneColumn) {
		this.importConsigneeTelephoneColumn = importConsigneeTelephoneColumn;
	}

	public String getImportSpecColumn() {
		return importSpecColumn;
	}

	public void setImportSpecColumn(String importSpecColumn) {
		this.importSpecColumn = importSpecColumn;
	}

}
