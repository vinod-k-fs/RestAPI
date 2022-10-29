package com.springboot.restapi.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SampleLine implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("BUSINESS_UNIT")
	private String businessUnit;
	
	@JsonProperty("ACCOUNT_NUM")
	private String accountNum;
	
	@JsonProperty("PC_CODE")
	private String pcCode;
	
	@JsonProperty("PRODUCT_CODE")
	private String productCode;
	
	@JsonProperty("GST_CODE")
	private String gstCode;
	
	@JsonProperty("COUNTY_CODE")
	private String countryCode;
	
	@JsonProperty("CURRENCY_CODE")
	private String currencyCode;
	
	@JsonProperty("CREDIT_AMOUNT")
	private String creditAmount;
	
	@JsonProperty("DEBIT_AMOUNT")
	private String debitAmount;
	
	@JsonProperty("FILE_ID")
	private String fileId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getPcCode() {
		return pcCode;
	}
	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getGstCode() {
		return gstCode;
	}
	public void setGstCode(String gstCode) {
		this.gstCode = gstCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
