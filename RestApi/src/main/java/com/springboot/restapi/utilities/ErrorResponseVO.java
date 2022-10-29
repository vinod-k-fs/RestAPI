package com.springboot.restapi.utilities;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseVO  {

	public ResponseEntity buildErrorResponse(String errorMessage,String statusCode) {
		ResponseVO resp = new ResponseVO();
		resp.setStatus(1);
		resp.setMessage(errorMessage);
		resp.setStatusCode(statusCode);
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}
	
	public ResponseEntity buildErrorResponseWithData(String errorMessage,String statusCode,Object data ) {
		ResponseVO resp = new ResponseVO();
		resp.setStatus(1);
		resp.setMessage(errorMessage);
		resp.setStatusCode(statusCode);
		resp.setData(data);
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}

}
