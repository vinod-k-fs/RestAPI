package com.springboot.restapi.repositories;

import java.util.List;

import com.springboot.restapi.vo.SampleHeader;
import com.springboot.restapi.vo.SampleLine;

public interface DataUploadRepository {
	public void saveHeaderData(SampleHeader header);

	public void saveLinesData(List<SampleLine> lines, SampleHeader header);

	public List<SampleHeader> getHeaderData(String fileId);

	public List<SampleLine> getLinesData(String fileId);

	public int submitJournal(String fileId, String workgroupName, String thresholdLimit);
	
	public int addApprover(String name, String workgroupName, String thresholdLimit, String isActive);
	
	public List<SampleHeader> getSubmittedJournals(String name);
	
	public int approveJournal(String fileId);
}
