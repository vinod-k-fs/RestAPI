package com.springboot.restapi.enums;

public enum JournalHeaderColumnsEnum {
	ID("ID", "Id"), BUSINESS_UNIT("BUSINESS_UNIT", "Business Unit"), JOURNAL_ID("JOURNAL_ID", "JournalId"),
	JOURNAL_DATE("JOURNAL_DATE", "JournalDate"), MAKER_ID("MAKER_ID", "makerId"),
	JOURNAL_TYPE("JOURNAL_TYPE", "makerId"), TOTAL_CREDIT_AMOUNT("TOTAL_CREDIT_AMOUNT", "totalCreditAmount"),
	TOTAL_DEBIT_AMOUNT("TOTAL_DEBIT_AMOUNT", "totalDebitAmount"), WORKGROUP_NAME("WORKGROUP_NAME", "workGroupName"),
	JOURNAL_STATUS("JOURNAL_STATUS", "journalStatus"), PRIMARY_APPROVAR("PRIMARY_APPROVAR", "primaryApprover"),
	APPROVED_DATE("APPROVED_DATE", "approvedDate");

	String name;
	String description;

	JournalHeaderColumnsEnum() {
	}

	JournalHeaderColumnsEnum(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
