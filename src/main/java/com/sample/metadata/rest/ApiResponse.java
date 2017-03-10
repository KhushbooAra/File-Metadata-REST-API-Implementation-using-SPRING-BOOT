package com.sample.metadata.rest;

public class ApiResponse {

	private String status = "SUCCESS";
	private Object result = null;
	
	public ApiResponse(){
		
	}
	
	public ApiResponse(Object result){
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public void markAsFailed(){
		this.status = "FAILED";
	}

}
