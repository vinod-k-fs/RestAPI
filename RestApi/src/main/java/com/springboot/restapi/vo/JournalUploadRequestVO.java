package com.springboot.restapi.vo;

import java.io.Serializable;
import java.util.List;


public class JournalUploadRequestVO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private SampleHeader header;
	private List<SampleLine> line;
	
	public SampleHeader getHeader() {
		return header;
	}
	public void setHeader(SampleHeader header) {
		this.header = header;
	}
	public List<SampleLine> getLine() {
		return line;
	}
	public void setLine(List<SampleLine> line) {
		this.line = line;
	}

}
