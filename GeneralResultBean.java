package com.fineway.specialReport.bean;

/**
 * 通用返回对象
 * @author ls
 * 2018年9月27日上午11:10:26
 */
public class GeneralResultBean {
	//返回数据
	private Object data;
	
	//0失败，1成功
	private int status;
	
	//信息
	private String message;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "{data:"+this.data+",result:"+this.status+",error:"+this.message+"}";
	}
	
	public GeneralResultBean() {
		
	}

	public GeneralResultBean(Object data, int status, String message) {
		this.data = data;
		this.status = status;
		this.message = message;
	}
	
	
}
