package com.youedata.dfs.service;

import java.util.List;

import com.youedata.dfs.persistence.model.FileStoreLog;


/**
 * 文件记录服务
 *
 * @author liujijun
 **/
public interface FileLogService {
		
	String addLog(FileStoreLog bean);

	int recovery(String startTime, String endTime);
	
	int getListCount(String startTime, String endTime);
	
	List<FileStoreLog> getList(String startTime, String endTime);

}
