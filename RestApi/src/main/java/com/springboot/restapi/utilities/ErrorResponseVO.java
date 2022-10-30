package com.springboot.restapi.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseVO  {

	public ResponseEntity buildErrorResponse(String errorMessage) {
		ResponseVO resp = new ResponseVO();
		resp.setStatus(1);
		resp.setMessage(errorMessage);
		resp.setStatusCode("500");
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}
	
	public ResponseEntity buildErrorResponseWithData(String errorMessage,Object data ) {
		ResponseVO resp = new ResponseVO();
		resp.setStatus(1);
		resp.setMessage(errorMessage);
		resp.setStatusCode("500");
		resp.setData(data);
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}

}
