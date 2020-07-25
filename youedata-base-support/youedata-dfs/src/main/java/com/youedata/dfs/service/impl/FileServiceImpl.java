package com.youedata.dfs.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.youedata.dfs.common.UpLoadConstant;
import com.youedata.dfs.controller.dto.CleanDetailResponse;
import com.youedata.dfs.controller.dto.CleanResponse;
import com.youedata.dfs.service.FileService;

import cn.hutool.core.io.FileUtil;

@Service
public class FileServiceImpl implements FileService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DefaultFastFileStorageClient storageClient;
	
	private static final String FILE_NAME_KEY = "fileName";
	
	@Override
	public String upload(File file, String fileName) {
	    if (file.exists()) {
	    	String noGroupPath = "";//存储在fastdfs不带组的路径
	    	FileInputStream fi = null;
            try {
            	fi = new FileInputStream(file);
            	StorePath path = storageClient.uploadFile(UpLoadConstant.DEFAULT_GROUP, fi, file.length(), FileUtil.extName(fileName));
                if (path == null) {
                    return "";
                }
                noGroupPath = path.getPath();
                Set<MataData> metaData = buildMetaData(fileName);
                storageClient.overwriteMetadata(UpLoadConstant.DEFAULT_GROUP,noGroupPath,metaData);

            } catch (FileNotFoundException e) {
        		logger.error("文件找不到：path-{},filename-{}", file.getAbsolutePath(), fileName);
        		return "";
        	
            } catch (Exception e) {
            	logger.error("文件存储异常：", e);
                //还原历史块
                return "";
            } finally {
        		try {
					if( null != fi) {
						fi.close();
					}
				} catch (IOException e) {
					logger.error("FileInputStream close error");
				}
        	}
            return UpLoadConstant.DEFAULT_GROUP + "/" + noGroupPath;
        }else {
        	return "";
        }
	}
	
	private Set<MataData> buildMetaData(String fileName) {
	     Set<MataData> set = new HashSet<>();
	     set.add(new MataData(FILE_NAME_KEY, fileName));
	     return set;
	}

	@Override
	public CleanResponse batchDelete(List<String> list) {
		CleanResponse result = new CleanResponse();
		if( null != list ) {
			List<CleanDetailResponse> detailList = new ArrayList<>();
			logger.info("[批量删除]list的大小-{}", list.size());
			int successCount = 0;//删除成功数量
			int failCount = 0;//删除失败数量
			for(int i=0; i<list.size(); i++) {
				String filePath = list.get(i);
				try {
					if( StringUtils.isEmpty(filePath)) {
						logger.info("[批量删除][ {}/{} ]filePath-{}删除失败-文件地址为空", i, list.size(), filePath);
						failCount++;
						CleanDetailResponse detail = new CleanDetailResponse();
						detail.setFilePath(filePath);
						detail.setErrorMsg("文件地址为空");
						detail.setErrorCode("");
						detailList.add(detail);
						continue;
					}
					//dfs删除文件
					storageClient.deleteFile(filePath);
					logger.info("[批量删除][ {}/{} ]filePath-{}删除成功", i, list.size(), filePath);
					successCount++;
				}catch(FdfsServerException e) {
					logger.error("[批量删除][ {}/{} ]filePath-{}删除失败", i, list.size(), filePath);
					failCount++;
					CleanDetailResponse detail = new CleanDetailResponse();
					detail.setFilePath(filePath);
					detail.setErrorMsg(e.getMessage());
					detail.setErrorCode(e.getErrorCode()+"");
					detailList.add(detail);
				}catch(Exception e) {
					logger.error("[批量删除][ {}/{} ]filePath-{}删除失败", i, list.size(), filePath);
					logger.error("[批量删除]失败原因：", e);
					failCount++;
					CleanDetailResponse detail = new CleanDetailResponse();
					detail.setFilePath(filePath);
					detail.setErrorMsg(e.getMessage());
					detail.setErrorCode("");
					detailList.add(detail);
				}
			}
			result.setDetails(detailList);
			result.setSuccessCount(successCount);
			result.setFailCount(failCount);
			result.setTotalCount(list.size());
		}
		return result;
	}

}
