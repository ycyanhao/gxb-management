package com.youedata.dfs.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.youedata.dfs.persistence.dao.FileStoreLogMapper;
import com.youedata.dfs.persistence.model.FileStoreLog;
import com.youedata.dfs.service.FileLogService;

@Service
public class FileLogServiceImpl implements FileLogService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private DefaultFastFileStorageClient storageClient;
	
	@Autowired
	private FileStoreLogMapper fileStoreLogMapper;
	
	@Override
	public String addLog(FileStoreLog bean) {
		fileStoreLogMapper.insert(bean);
		return bean.getId();
	}

	@Override
	public int recovery(String startTime, String endTime) {
		logger.info("[recovery]startTime-{},endTime-{}", startTime, endTime);
		int count = 0;
		List<FileStoreLog> list = getList(startTime, endTime);
		if( null != list ) {
			logger.info("[recovery]list的大小-{}", list.size());
			for(int i=0; i<list.size(); i++) {
				FileStoreLog bean = list.get(i);
				String filePath = bean.getFilePath();
				try {
					//dfs删除文件
					storageClient.deleteFile(filePath);
					logger.info("[recovery][ {}/{} ]filePath-{}删除成功", i, list.size(), filePath);
					count++;
					//数据库标记为已删除
					bean.setDelFlag("Y");
					fileStoreLogMapper.updateById(bean);
				}catch(Exception e) {
					logger.info("[recovery][ {}/{} ]filePath-{}删除失败", i, list.size(), filePath);
				}
			}
		}
		return count;
	}

	@Override
	public int getListCount(String startTime, String endTime) {
		EntityWrapper<FileStoreLog> wrapper = new EntityWrapper<FileStoreLog>();
		wrapper.eq("del_flag", "N");
		if( org.apache.commons.lang3.StringUtils.isNotBlank(endTime) ){
			wrapper.lt("create_time", endTime);
		}
		if( org.apache.commons.lang3.StringUtils.isNotBlank(startTime) ){
			wrapper.gt("create_time", startTime);
		}
		Integer count = fileStoreLogMapper.selectCount(wrapper);
		return null == count ? 0 : count.intValue();
	}

	@Override
	public List<FileStoreLog> getList(String startTime, String endTime) {
		EntityWrapper<FileStoreLog> wrapper = new EntityWrapper<FileStoreLog>();
		wrapper.eq("del_flag", "N");
		if( org.apache.commons.lang3.StringUtils.isNotBlank(endTime) ){
			wrapper.lt("create_time", endTime);
		}
		if( org.apache.commons.lang3.StringUtils.isNotBlank(startTime) ){
			wrapper.gt("create_time", startTime);
		}
		List<FileStoreLog> list = fileStoreLogMapper.selectList(wrapper);
		return list;
	}

}
