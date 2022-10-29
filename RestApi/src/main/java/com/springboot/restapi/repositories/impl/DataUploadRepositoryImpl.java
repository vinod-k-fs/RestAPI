package com.springboot.restapi.repositories.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.springboot.restapi.repositories.DataUploadRepository;
import com.springboot.restapi.utilities.ResponseVO;
import com.springboot.restapi.vo.SampleHeader;
import com.springboot.restapi.vo.SampleLine;

@Repository
public class DataUploadRepositoryImpl implements DataUploadRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void saveHeaderData(SampleHeader header) {

		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource param = new MapSqlParameterSource();
		String fileId = header.getBusinessUnit() + "::" + header.getJournalId() + "::" + header.getJournalDate();
		String query = "Select count(*) from  journal_header where FILE_ID = :FILE_ID";
		param.addValue("FILE_ID", fileId);
		namedJdbcTemplate.query(query, param, (ResultSet rs) -> {
			int count = rs.getInt(1);
			System.out.println("count : " + count);
			if (count == 0) {
				String headerQuery = "INSERT INTO journal_header "
						+ "(JOURNAL_TYPE,BUSINESS_UNIT,JOURNAL_ID,JOURNAL_DATE,MAKER_ID,TOTAL_DEBIT_AMOUNT,TOTAL_CREDIT_AMOUNT,FILE_ID) "
						+ "values(:JOURNAL_TYPE,:BUSINESS_UNIT,:JOURNAL_ID,:JOURNAL_DATE,:MAKER_ID,:TOTAL_DEBIT_AMOUNT,:TOTAL_CREDIT_AMOUNT,:FILE_ID)";
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
		});
	}

	@Override
	public void saveLinesData(List<SampleLine> lines, SampleHeader header) {

		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		String fileId = header.getBusinessUnit() + "::" + header.getJournalId() + "::" + header.getJournalDate();
		for (SampleLine line : lines) {
			String lineQuery = "INSERT INTO journal_lines "
					+ "(BUSINESS_UNIT,ACCOUNT_NUM,PC_CODE,PRODUCT_CODE,GST_CODE,COUNTY_CODE,CURRENCY_CODE,CREDIT_AMOUNT,DEBIT_AMOUNT,FILE_ID) "
					+ "values(:BUSINESS_UNIT,:ACCOUNT_NUM,:PC_CODE,:PRODUCT_CODE,:GST_CODE,:COUNTY_CODE,:CURRENCY_CODE,:CREDIT_AMOUNT,:DEBIT_AMOUNT,:FILE_ID)";
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
	public SampleHeader getHeaderData(String fileId) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("FILE_ID", fileId);
		String query = "SELECT * FROM journal_header WHERE FILE_ID=:FILE_ID";
		SampleHeader header = new SampleHeader();
		namedJdbcTemplate.query(query, param, (ResultSet rs) -> {
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
		});
		return header;
	}

	@Override
	public List<SampleLine> getLinesData(String fileId) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("FILE_ID", fileId);
		String query = "SELECT * FROM journal_lines WHERE FILE_ID=:FILE_ID";
		List<SampleLine> lines = new ArrayList();
		namedJdbcTemplate.query(query, param, (ResultSet rs) -> {
			SampleLine line = new SampleLine();
			line.setId(rs.getLong("ID"));
			line.setBusinessUnit(rs.getString("BUSINESS_UNIT"));
			line.setAccountNum(rs.getString("ACCOUNT_NUM"));
			line.setPcCode(rs.getString("PC_CODE"));
			line.setProductCode(rs.getString("PRODUCT_CODE"));
			line.setGstCode(rs.getString("GST_CODE"));
			line.setCountryCode(rs.getString("COUNTY_CODE"));
			line.setCurrencyCode(rs.getString("CURRENCY_CODE"));
			line.setCreditAmount(rs.getString("CREDIT_AMOUNT"));
			line.setDebitAmount(rs.getString("DEBIT_AMOUNT"));
			line.setFileId(rs.getString("FILE_ID"));
			lines.add(line);
		});
		return lines;
	}
	
	@Override
	public ResponseVO submitJournal(String fileId, String workgroupName, String thresholdLimit) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("FILE_ID", fileId);
		params.addValue("WORKGROUP_NAME", workgroupName);
		params.addValue("JOURNAL_STATUS", "P");
		params.addValue("THRESHOLD_LIMIT", Integer.parseInt(thresholdLimit));
		String query = "SELECT APPROVER_NAME FROM approvers WHERE WORKGROUP_NAME=:WORKGROUP_NAME AND THRESHOLD_LIMIT >= :THRESHOLD_LIMIT";
		String approverName = namedJdbcTemplate.query(query, params, (ResultSet rs) -> {
			if (!rs.next()) {
				return null;
			}
			return rs.getString("APPROVER_NAME");
		});
		if (approverName != null) {
			params.addValue("PRIMARY_APPROVAR", approverName);
			String query1 = "UPDATE journal_header SET WORKGROUP_NAME=:WORKGROUP_NAME, JOURNAL_STATUS=:JOURNAL_STATUS,PRIMARY_APPROVAR=:PRIMARY_APPROVAR WHERE FILE_ID=:FILE_ID";
			namedJdbcTemplate.update(query1, params);
			return new ResponseVO(0, "Success", "200");
		} else {
			return new ResponseVO(1, "No Approver for the selected workgroup", "500");
		}
	}
}
