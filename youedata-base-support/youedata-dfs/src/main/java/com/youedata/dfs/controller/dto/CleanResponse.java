package com.youedata.dfs.controller.dto;

import java.util.List;

/**
 * 文件删除请求dto
 *
 * @author liujijun
 */
public class CleanResponse {

	private int totalCount = 0;
	private int successCount = 0;
	private int failCount = 0;
    private List<CleanDetailResponse> details;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public List<CleanDetailResponse> getDetails() {
		return details;
	}
	public void setDetails(List<CleanDetailResponse> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "CleanResponse [totalCount=" + totalCount + ", successCount=" + successCount + ", failCount=" + failCount
				+ ", details=" + details + "]";
	}
	
}
