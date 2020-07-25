package com.youedata.dfs.service;

import java.io.File;
import java.util.List;

import com.youedata.dfs.controller.dto.CleanResponse;


/**
 * 文件存储
 *
 * @author liujijun
 **/
public interface FileService {
		
	String upload(File file, String fileName);

	/**
	 * 批量删除文件
	 * @param targetList
	 * @return
	 */
	CleanResponse batchDelete(List<String> list);
	
}
