package com.springboot.restapi.repositories;

import java.util.List;

import com.springboot.restapi.utilities.ResponseVO;
import com.springboot.restapi.vo.SampleHeader;
import com.springboot.restapi.vo.SampleLine;

public interface DataUploadRepository {
	public void saveHeaderData(SampleHeader header);

	public void saveLinesData(List<SampleLine> lines, SampleHeader header);

	public SampleHeader getHeaderData(String fileId);

	public List<SampleLine> getLinesData(String fileId);

	public ResponseVO submitJournal(String fileId, String workgroupName, String thresholdLimit);
}
