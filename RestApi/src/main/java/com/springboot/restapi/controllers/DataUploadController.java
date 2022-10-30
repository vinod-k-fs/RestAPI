package com.springboot.restapi.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restapi.entities.JournalLines;
import com.springboot.restapi.entities.journal_header;
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
			return errorResponseVO.buildErrorResponse(e.getMessage());
		}
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
			return errorResponseVO.buildErrorResponse(e.getMessage());
		}
	}
	
	@GetMapping("getAllJournalHeaders")
	public List<journal_header> getAllJournalHeaders() {
		return journalRepo.findAll();
	}

	@PostMapping("submitJournal")
	public ResponseEntity<ResponseVO> submitJournal(@RequestBody Map<String, String> reqParams) {
		try {
			ResponseVO resp = dataUploadService.submitJournal(reqParams.get("FILE_ID"), reqParams.get("WORKGROUP_NAME"),
					reqParams.get("THRESHOLD_LIMIT"));
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			return errorResponseVO.buildErrorResponse(e.getMessage());
		}
	}

	@PostMapping("addApprovers")
	public ResponseEntity<ResponseVO> addApprovers(@RequestBody Map<String, String> reqParams) {
		try {
			ResponseVO resp = dataUploadService.addApprover(reqParams.get("APPROVER_NAME"),
					reqParams.get("WORKGROUP_NAME"), reqParams.get("THRESHOLD_LIMIT"), reqParams.get("IS_ACTIVE"));
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			return errorResponseVO.buildErrorResponse(e.getMessage());
		}
	}

	@PostMapping("/getSubmittedJournals")
	public ResponseEntity<ResponseVO> getSubmittedJournals(@RequestBody Map<String, String> reqParams) {
		try {
			ResponseVO resp = dataUploadService.getSubmittedJournals(reqParams.get("APPROVER_NAME"));
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			return errorResponseVO.buildErrorResponse(e.getMessage());
		}
	}

	@PostMapping("/approveJournal")
	public ResponseEntity<ResponseVO> approveJournal(@RequestBody Map<String, String> reqParams) {
		try {
			ResponseVO resp = dataUploadService.approveJournal(reqParams.get("FILE_ID"));
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			return errorResponseVO.buildErrorResponse(e.getMessage());
		}
	}

}
