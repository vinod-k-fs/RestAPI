package com.springboot.restapi.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.springboot.restapi.vo.SampleLine;

@Component
public class SampleLineRowMapper implements RowMapper<SampleLine> {

	
	@Override
	public SampleLine mapRow(ResultSet rs, int rowNum) throws SQLException {
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
		return line;
	}

}
