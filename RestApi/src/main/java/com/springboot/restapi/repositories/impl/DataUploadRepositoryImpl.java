package com.springboot.restapi.repositories.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.restapi.constants.AppConstants;
import com.springboot.restapi.mappers.SampleHeaderRowMapper;
import com.springboot.restapi.mappers.SampleLineRowMapper;
import com.springboot.restapi.repositories.DataUploadRepository;
import com.springboot.restapi.vo.SampleHeader;
import com.springboot.restapi.vo.SampleLine;

@Repository
public class DataUploadRepositoryImpl implements DataUploadRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	SampleHeaderRowMapper sampleHeaderRowMapper;
	
	@Autowired
	SampleLineRowMapper sampleLineRowMapper;

	@Override
	public void saveHeaderData(SampleHeader header) {

		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource param = new MapSqlParameterSource();
		String fileId = header.getBusinessUnit() + "::" + header.getJournalId() + "::" + header.getJournalDate();
		String query = "Select count(*) from  "+AppConstants.HEADER_TABLE+" where FILE_ID = :FILE_ID";
		param.addValue("FILE_ID", fileId);
		int count = namedJdbcTemplate.query(query, param, (ResultSet rs) -> {
			return rs.getInt(1);
		});
		if (count == 0) {
			String headerQuery = "INSERT INTO "+ AppConstants.HEADER_TABLE
					+ " (JOURNAL_TYPE,BUSINESS_UNIT,JOURNAL_ID,JOURNAL_DATE,MAKER_ID,TOTAL_DEBIT_AMOUNT,TOTAL_CREDIT_AMOUNT,FILE_ID) "
					+ " values(:JOURNAL_TYPE,:BUSINESS_UNIT,:JOURNAL_ID,:JOURNAL_DATE,:MAKER_ID,:TOTAL_DEBIT_AMOUNT,:TOTAL_CREDIT_AMOUNT,:FILE_ID)";
			MapSqlParameterSource sqlParam = new MapSqlParameterSource();
			param.addValue("JOURNAL_TYPE", header.getJournalType());
			param.addValue("BUSINESS_UNIT", header.getBusinessUnit());
			param.addValue("JOURNAL_ID", header.getJournalId());
			param.addValue("JOURNAL_DATE", header.getJournalDate());
			param.addValue("MAKER_ID", header.getMakerId());
			param.addValue("TOTAL_DEBIT_AMOUNT", header.getTotalDebitAmount());
			param.addValue("TOTAL_CREDIT_AMOUNT", header.getTotalCreditAmount());
			namedJdbcTemplate.update(headerQuery, param);
		}
	}

	@Override
	public void saveLinesData(List<SampleLine> lines, SampleHeader header) {

		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		String fileId = header.getBusinessUnit() + "::" + header.getJournalId() + "::" + header.getJournalDate();
		for (SampleLine line : lines) {
			String lineQuery = "INSERT INTO "+AppConstants.LINES_TABLE
					+ " (BUSINESS_UNIT,ACCOUNT_NUM,PC_CODE,PRODUCT_CODE,GST_CODE,COUNTY_CODE,CURRENCY_CODE,CREDIT_AMOUNT,DEBIT_AMOUNT,FILE_ID) "
					+ " VALUES(:BUSINESS_UNIT,:ACCOUNT_NUM,:PC_CODE,:PRODUCT_CODE,:GST_CODE,:COUNTY_CODE,:CURRENCY_CODE,:CREDIT_AMOUNT,:DEBIT_AMOUNT,:FILE_ID)";
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("BUSINESS_UNIT", line.getBusinessUnit());
			param.addValue("ACCOUNT_NUM", line.getAccountNum());
			param.addValue("PC_CODE", line.getPcCode());
			param.addValue("PRODUCT_CODE", line.getProductCode());
			param.addValue("GST_CODE", line.getGstCode());
			param.addValue("COUNTY_CODE", line.getCountryCode());
			param.addValue("CURRENCY_CODE", line.getCurrencyCode());
			param.addValue("CREDIT_AMOUNT", line.getCreditAmount());
			param.addValue("DEBIT_AMOUNT", line.getDebitAmount());
			param.addValue("FILE_ID", fileId);
			namedJdbcTemplate.update(lineQuery, param);
		}
	}

	@Override
	public List<SampleHeader> getHeaderData(String fileId) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("FILE_ID", fileId);
		String query = "SELECT * FROM "+AppConstants.HEADER_TABLE+" WHERE FILE_ID=:FILE_ID";
		return namedJdbcTemplate.query(query, param, sampleHeaderRowMapper);
	}

	@Override
	public List<SampleLine> getLinesData(String fileId) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("FILE_ID", fileId);
		String query = "SELECT * FROM "+AppConstants.LINES_TABLE+" WHERE FILE_ID=:FILE_ID";
		return namedJdbcTemplate.query(query, param, sampleLineRowMapper);
	}
	
	@Override
	public int submitJournal(String fileId, String workgroupName, String thresholdLimit) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("FILE_ID", fileId);
		params.addValue("WORKGROUP_NAME", workgroupName);
		params.addValue("JOURNAL_STATUS", "P");
		params.addValue("THRESHOLD_LIMIT", Integer.parseInt(thresholdLimit));
		String query = "SELECT APPROVER_NAME FROM "+AppConstants.APPROVERS_TABLE+" WHERE WORKGROUP_NAME=:WORKGROUP_NAME AND THRESHOLD_LIMIT >= :THRESHOLD_LIMIT";
		String approverName = namedJdbcTemplate.query(query, params, (ResultSet rs) -> {
			if (!rs.next()) {
				return null;
			}
			return rs.getString("APPROVER_NAME");
		});
		if (approverName != null) {
			params.addValue("PRIMARY_APPROVAR", approverName);
			String query1 = "UPDATE "+AppConstants.HEADER_TABLE+" SET WORKGROUP_NAME=:WORKGROUP_NAME, JOURNAL_STATUS=:JOURNAL_STATUS,PRIMARY_APPROVAR=:PRIMARY_APPROVAR WHERE FILE_ID=:FILE_ID";
			return namedJdbcTemplate.update(query1, params);
		} else {
			return 0;
		}
	}
	
	@Override
	public int addApprover(String name, String workgroupName, String thresholdLimit, String isActive) {
		NamedParameterJdbcTemplate namesJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		String query = "";
		query = "SELECT APPROVER_NAME from "+AppConstants.APPROVERS_TABLE+" WHERE APPROVER_NAME = :APPROVER_NAME";
		params.addValue("APPROVER_NAME", name);
		boolean isAvailabale = namesJdbcTemplate.query(query, params, (ResultSet rs) -> {
			if (rs.next() && rs.getString("APPROVER_NAME") != null) {
				return true;
			}
			return false;
		});
		if (!isAvailabale) {
			query = "INSERT INTO "+AppConstants.APPROVERS_TABLE+" (APPROVER_NAME,WORKGROUP_NAME,THRESHOLD_LIMIT,IS_ACTIVE) VALUES (:APPROVER_NAME,:WORKGROUP_NAME,:THRESHOLD_LIMIT,:IS_ACTIVE)";
			params.addValue("APPROVER_NAME", name);
			params.addValue("WORKGROUP_NAME", workgroupName);
			params.addValue("THRESHOLD_LIMIT", thresholdLimit);
			params.addValue("IS_ACTIVE", isActive);
			return namesJdbcTemplate.update(query, params);
		}
		return 0;
	}

	@Override
	public List<SampleHeader> getSubmittedJournals(String name) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("PRIMARY_APPROVAR", name);
		String query = "SELECT * FROM "+AppConstants.HEADER_TABLE+" WHERE PRIMARY_APPROVAR =:PRIMARY_APPROVAR";
		return namedJdbcTemplate.query(query, params, sampleHeaderRowMapper);
	}
	
	@Override
	public int approveJournal(String fileId) {
		NamedParameterJdbcTemplate namesJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("FILE_ID", fileId);
		Date date = new Date();
		params.addValue("date", date.toString());
		params.addValue("JOURNAL_STATUS", "APPROVED");
		String query = "UPDATE "+AppConstants.HEADER_TABLE+" SET APPROVED_DATE=:date, JOURNAL_STATUS=:JOURNAL_STATUS WHERE FILE_ID =:FILE_ID";
		return namesJdbcTemplate.update(query, params);
	}
}
























