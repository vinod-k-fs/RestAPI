package com.springboot.restapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.restapi.exception.InvalidDataException;
import com.springboot.restapi.repositories.impl.DataUploadRepositoryImpl;
import com.springboot.restapi.utilities.ResponseVO;
import com.springboot.restapi.vo.JournalUploadRequestVO;
import com.springboot.restapi.vo.SampleHeader;
import com.springboot.restapi.vo.SampleLine;

@Service
public class DataUploadService {

	@Autowired
	DataUploadRepositoryImpl dataUploadRepositoryImpl;

	public ResponseVO uploadJournal(JournalUploadRequestVO journal) throws InvalidDataException {
		SampleHeader header = journal.getHeader();
		List<SampleLine> lines = journal.getLine();
		dataUploadRepositoryImpl.saveHeaderData(header);
		dataUploadRepositoryImpl.saveLinesData(lines, header);
		return new ResponseVO(0, "Success", "200", null);
	}

	public SampleHeader getHeader(String fileId) {
		List<SampleHeader> header = dataUploadRepositoryImpl.getHeaderData(fileId);
		if (header.get(0) != null) {
			return header.get(0);
		} else {
			return new SampleHeader();
		}
	}
	
	public List<SampleLine> getLines(String fileId){
		List<SampleLine> lines = dataUploadRepositoryImpl.getLinesData(fileId);
		return lines;
	}

	public ResponseVO submitJournal(String fileId, String workgroupName, String thresholdLimit) {
		int status = dataUploadRepositoryImpl.submitJournal(fileId, workgroupName, thresholdLimit);
		if (status == 1) {
			return new ResponseVO(0, "Success", "200", null);
		} else {
			return new ResponseVO(1, "Failed to update", "500", null);
		}
	}
	
	public ResponseVO addApprover(String name, String workgroupName, String thresholdLimit, String isActive) {
		int status = dataUploadRepositoryImpl.addApprover(name, workgroupName, thresholdLimit, isActive);
		if (status == 1) {
			return new ResponseVO(0, "Success", "200", null);
		} else {
			return new ResponseVO(1, "Failed to update", "500", null);
		}
	}
	
	public ResponseVO getSubmittedJournals(String name) {
		List<SampleHeader> header = dataUploadRepositoryImpl.getSubmittedJournals(name);
		JournalUploadRequestVO resp = new JournalUploadRequestVO();
		resp.setHeader(header.get(0));
		resp.setLine(new ArrayList());
		return new ResponseVO(0, "Success", "200", resp);
	}
	
	public ResponseVO approveJournal(String fileId) {
		int status = dataUploadRepositoryImpl.approveJournal(fileId);
		if (status == 1) {
			return new ResponseVO(0, "Success", "200", null);
		} else {
			return new ResponseVO(1, "Failed to update", "500", null);
		}
	}
}
