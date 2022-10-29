package com.springboot.restapi.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restapi.entities.JournalLines;
import com.springboot.restapi.entities.journal_header;
import com.springboot.restapi.exception.InvalidDataException;
import com.springboot.restapi.repositories.JournalRespository;
import com.springboot.restapi.services.DataUploadService;
import com.springboot.restapi.utilities.ErrorResponseVO;
import com.springboot.restapi.utilities.ResponseVO;
import com.springboot.restapi.vo.JournalUploadRequestVO;
import com.springboot.restapi.vo.SampleHeader;
import com.springboot.restapi.vo.SampleLine;

@RestController
public class DataUploadController {

	@Autowired
	JournalRespository journalRepo;

	@Autowired
	JournalLines journalLines;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataUploadService dataUploadService;

	@Autowired
	ErrorResponseVO errorResponseVO;

	@PostMapping(value = "uploadJournal", produces = "application/json")
	public ResponseEntity<ResponseVO> uploadJournal(@RequestBody JournalUploadRequestVO journal) throws Exception {

		try {
			ResponseVO resp = dataUploadService.uploadJournal(journal);
			return ResponseEntity.ok(resp);

		} catch (Exception e) {
			// e.printStackTrace();
			return errorResponseVO.buildErrorResponse(e.getMessage(), "500");
		}
	}

	@GetMapping("getAllJournalHeaders")
	public List<journal_header> getAllJournalHeaders() {
		return journalRepo.findAll();
	}

	@PostMapping("getJournal")
	public ResponseEntity<JournalUploadRequestVO> getJournal(@RequestBody Map<String, String> reqParams) {
		try {
			SampleHeader header = dataUploadService.getHeader(reqParams.get("FILE_ID"));
			List<SampleLine> lines = dataUploadService.getLines(reqParams.get("FILE_ID"));
			JournalUploadRequestVO resp = new JournalUploadRequestVO();
			resp.setHeader(header);
			resp.setLine(lines);
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			return errorResponseVO.buildErrorResponse(e.getMessage(), "500");
		}
	}

	@PostMapping("submitJournal")
	public ResponseVO submitJournal(@RequestBody Map<String, String> reqParams) {
		ResponseVO resp = new ResponseVO();
		try {
			NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("FILE_ID", reqParams.get("FILE_ID"));
			params.addValue("WORKGROUP_NAME", reqParams.get("WORKGROUP_NAME"));
			params.addValue("JOURNAL_STATUS", "P");
			params.addValue("THRESHOLD_LIMIT", Integer.parseInt(reqParams.get("THRESHOLD_LIMIT")));

			// get the approver
			String query = "SELECT APPROVER_NAME FROM approvers WHERE WORKGROUP_NAME=:WORKGROUP_NAME AND THRESHOLD_LIMIT >= :THRESHOLD_LIMIT";
			String approverName = namedJdbcTemplate.query(query, params, (ResultSet rs) -> {
				rs.next();
				return rs.getString("APPROVER_NAME");
			});
			System.out.println("======APPROVER NAME: " + approverName);
			// update the work group and approver in header
			if (approverName != null) {
				params.addValue("PRIMARY_APPROVAR", approverName);
				String query1 = "UPDATE journal_header SET WORKGROUP_NAME=:WORKGROUP_NAME, JOURNAL_STATUS=:JOURNAL_STATUS,PRIMARY_APPROVAR=:PRIMARY_APPROVAR WHERE FILE_ID=:FILE_ID";
				namedJdbcTemplate.update(query1, params);
				resp.setStatus(0);
				resp.setMessage("Journal Submitted successfully");
				resp.setStatusCode(approverName);
			} else {
				resp.setStatus(1);
				resp.setMessage("No Approver for the selected workgroup");
				resp.setStatusCode("500");
			}
			return resp;

		} catch (Exception e) {
			resp.setStatus(1);
			resp.setMessage("Something went wrong");
			resp.setStatusCode("500");
			resp.setData(e);
			return resp;
		}
	}

	@PostMapping("addApprovers")
	public ResponseVO addApprovers(@RequestBody Map<String, String> reqParams) {
		try {
			NamedParameterJdbcTemplate namesJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
			MapSqlParameterSource params = new MapSqlParameterSource();
			String query = "";
			query = "SELECT APPROVER_NAME from approvers WHERE APPROVER_NAME = :APPROVER_NAME";
			params.addValue("APPROVER_NAME", reqParams.get("APPROVER_NAME"));
			boolean isAvailabale = namesJdbcTemplate.query(query, params, (ResultSet rs) -> {
				if (rs.next() && rs.getString("APPROVER_NAME") != null) {
					return true;
				}
				return false;
			});
			if (isAvailabale) {
				ResponseVO resp = new ResponseVO();
				resp.setStatus(1);
				resp.setMessage("APPROVER_NAME already exists");
				return resp;
			} else {
				query = "INSERT INTO approvers (APPROVER_NAME,WORKGROUP_NAME,THRESHOLD_LIMIT,IS_ACTIVE) VALUES (:APPROVER_NAME,:WORKGROUP_NAME,:THRESHOLD_LIMIT,:IS_ACTIVE)";
				params.addValue("APPROVER_NAME", reqParams.get("APPROVER_NAME"));
				params.addValue("WORKGROUP_NAME", reqParams.get("WORKGROUP_NAME"));
				params.addValue("THRESHOLD_LIMIT", reqParams.get("THRESHOLD_LIMIT"));
				params.addValue("IS_ACTIVE", reqParams.get("IS_ACTIVE"));

				namesJdbcTemplate.update(query, params);
				ResponseVO resp = new ResponseVO();
				resp.setStatus(0);
				resp.setMessage("Success");
				return resp;
			}

		} catch (Exception e) {
			ResponseVO resp = new ResponseVO();
			resp.setStatus(1);
			resp.setMessage("Something went wrong while adding approvers");
			resp.setData(e);
			return resp;
		}
	}

	@PostMapping("/getSubmittedJournals")
	public JournalUploadRequestVO getSubmittedJournals(@RequestBody Map<String, String> reqParams) {
		NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("PRIMARY_APPROVAR", reqParams.get("NAME"));
		String query = "SELECT * FROM journal_header WHERE PRIMARY_APPROVAR =:PRIMARY_APPROVAR";
		JournalUploadRequestVO resp = new JournalUploadRequestVO();
		namedJdbcTemplate.query(query, params, (ResultSet rs) -> {
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
			resp.setHeader(header);
		});
		return resp;
	}

	@PostMapping("/approveJournal")
	public ResponseVO approveJournal(@RequestBody Map<String, String> reqParams) {
		ResponseVO resp = new ResponseVO();
		try {
			NamedParameterJdbcTemplate namesJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("FILE_ID", reqParams.get("FILE_ID"));
			Date date = new Date();
			params.addValue("date", date.toString());
			params.addValue("JOURNAL_STATUS", "APPROVED");
			String query = "UPDATE journal_header SET APPROVED_DATE=:date, JOURNAL_STATUS=:JOURNAL_STATUS WHERE FILE_ID =:FILE_ID";
			namesJdbcTemplate.update(query, params);
			resp.setStatus(0);
			resp.setMessage("Journal Apporved at:" + date.toString());
		} catch (Exception e) {
			resp.setStatus(1);
			resp.setMessage("Something went wrong while adding approvers");
			resp.setData(e);

		}
		return resp;
	}

}
