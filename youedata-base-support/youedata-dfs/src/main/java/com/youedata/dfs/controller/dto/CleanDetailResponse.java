package com.youedata.dfs.controller.dto;


/**
 * 文件删除详细请求dto
 *
 * @author liujijun
 */
public class CleanDetailResponse {

    private String filePath;
    private String errorCode;
    private String errorMsg;
    
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	@Override
	public String toString() {
		return "CleanResponse [filePath=" + filePath + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + "]";
	}
	
}
