package com.springboot.restapi.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.springboot.restapi.vo.SampleHeader;

@Component
public class SampleHeaderRowMapper implements RowMapper<SampleHeader> {

	@Override
	public SampleHeader mapRow(ResultSet rs, int rowNum) throws SQLException {
		SampleHeader header = new SampleHeader();
		header.setId(rs.getLong("ID"));
		header.setBusinessUnit(rs.getString("BUSINESS_UNIT"));
		header.setJournalId(rs.getString("JOURNAL_ID"));
		header.setJournalDate(rs.getString("JOURNAL_DATE"));
		header.setMakerId(rs.getString("MAKER_ID"));
		header.setJournalType(rs.getString("JOURNAL_TYPE"));
		header.setTotalCreditAmount(rs.getString("TOTAL_CREDIT_AMOUNT"));
		header.setTotalDebitAmount(rs.getString("TOTAL_DEBIT_AMOUNT"));
		header.setWorkgroupName(rs.getString("WORKGROUP_NAME"));
		header.setJournalStatus(rs.getString("JOURNAL_STATUS"));
		header.setPrimaryApprovar(rs.getString("PRIMARY_APPROVAR"));
		header.setApprovedDate(rs.getString("APPROVED_DATE"));
		return header;
	}
	
}
