package com.springboot.restapi.vo;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SampleHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("JOURNAL_TYPE")
	private String journalType;
	
	@JsonProperty("BUSINESS_UNIT")
	private String businessUnit;
	
	@JsonProperty("JOURNAL_ID")
	private String journalId;

	@JsonProperty("JOURNAL_DATE")
	private String journalDate;
	
	@JsonProperty("MAKER_ID")
	private String makerId;
	
	@JsonProperty("WORKGROUP_NAME")
	private String workgroupName;
	
	@JsonProperty("TOTAL_DEBIT_AMOUNT")
	private String totalDebitAmount;
	
	@JsonProperty("TOTAL_CREDIT_AMOUNT")
	private String totalCreditAmount;
	
	@JsonProperty("JOURNAL_STATUS")
	private String journalStatus;
	
	
	@JsonProperty("PRIMARY_APPROVAR")
	private String primaryApprovar;
	
	@JsonProperty("APPROVED_DATE")
	private String approvedDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getJournalType() {
		return journalType;
	}

	public void setJournalType(String journalType) {
		this.journalType = journalType;
	}
	
	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getJournalId() {
		return journalId;
	}

	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}

	public String getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(String journalDate) {
		this.journalDate = journalDate;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getWorkgroupName() {
		return workgroupName;
	}

	public void setWorkgroupName(String workgroupName) {
		this.workgroupName = workgroupName;
	}

	public String getTotalDebitAmount() {
		return totalDebitAmount;
	}

	public void setTotalDebitAmount(String totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}

	public String getTotalCreditAmount() {
		return totalCreditAmount;
	}

	public void setTotalCreditAmount(String totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}

	public String getJournalStatus() {
		return journalStatus;
	}

	public void setJournalStatus(String journalStatus) {
		this.journalStatus = journalStatus;
	}

	public String getPrimaryApprovar() {
		return primaryApprovar;
	}

	public void setPrimaryApprovar(String primaryApprovar) {
		this.primaryApprovar = primaryApprovar;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

}
