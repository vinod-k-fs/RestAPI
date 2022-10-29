package com.springboot.restapi.services;

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
		SampleHeader header = dataUploadRepositoryImpl.getHeaderData(fileId);
		return header;
	}
	
	public List<SampleLine> getLines(String fileId){
		List<SampleLine> lines = dataUploadRepositoryImpl.getLinesData(fileId);
		return lines;
	}
	
}
