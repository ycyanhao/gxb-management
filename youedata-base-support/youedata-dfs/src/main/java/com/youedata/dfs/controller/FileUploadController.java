package com.youedata.dfs.controller;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsServerException;
import com.github.tobato.fastdfs.proto.ErrorCodeConstants;
import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.youedata.dfs.common.*;
import com.youedata.dfs.config.FileLogProperties;
import com.youedata.dfs.persistence.model.FileStoreLog;
import com.youedata.dfs.service.FileLogService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(value = "/fileUpload", tags = "文件存储")
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DefaultFastFileStorageClient storageClient;

    @Autowired
    private FileLogProperties fileLogProperties;

    @Autowired
    private FileLogService fileLogService;

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传", notes = "/upload")
    @ResponseBody
    public ApiResult upload(@ApiParam @RequestParam(name = "file", required = true) MultipartFile file) throws IOException {
        logger.info("[文件上传] file={}", file);
        String noGroupPath = "";//存储在fastdfs不带组的路径
        //暂时不支持多文件上传,后续版本可以再加上checkFile
        if (!file.isEmpty()) {
            try {
                StorePath path = storageClient.uploadFile(UpLoadConstant.DEFAULT_GROUP, file.getInputStream(), file.getSize(), FileUtil.extName(file.getOriginalFilename()));
                logger.info("[文件上传]path={}", path);
                if (path == null) {
                    return ApiResult.fail("上传文件服务端操作异常:获取存储后的文件路径异常");
                }
                noGroupPath = path.getPath();
                logger.info("[文件上传] noGroupPath={}", noGroupPath);

                //添加数据库记录：如果开关打开，则进行记录
                boolean dblogOpen = fileLogProperties.isDblogOpen();
                logger.info("[文件上传]数据库记录:dblogOpen={}", dblogOpen);
                if (dblogOpen) {
                    FileStoreLog log = new FileStoreLog();
                    log.setCreateTime(new Date());
                    log.setDelFlag("N");
                    log.setFileName(file.getOriginalFilename());
                    log.setFilePath(UpLoadConstant.DEFAULT_GROUP + "/" + noGroupPath);
                    fileLogService.addLog(log);
                    logger.info("[文件上传]数据库记录:日志信息={}", log);
                }

            } catch (FdfsServerException e) {
                logger.error("[文件上传]服务端操作异常:", e);
                if (ErrorCodeConstants.ERR_NO_ENOSPC == e.getErrorCode()) {
                    return ApiResult.fail("上传文件服务端操作异常:没有足够的存储空间");
                } else {
                    return ApiResult.fail("上传文件服务操作异常");
                }
            } catch (Exception e) {
                logger.error("[文件上传]服务操作异常:", e);
                return ApiResult.fail("上传文件服务端操作异常");
            }
        } else {
            logger.error("上传的文件内容为空");
            return ApiResult.fail("上传的文件内容为空");
        }

        return ApiResult.success(UpLoadConstant.DEFAULT_GROUP + "/" + noGroupPath);
    }

    /**
     * 文件上传-批量
     *
     * @param files
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/upload/batch")
    @ApiOperation(value = "文件上传-批量", notes = "/upload/batch")
    @ResponseBody
    public ApiResult uploadByBatch(@ApiParam() @RequestParam(name = "files") MultipartFile[] files) throws IOException {
        logger.info("[文件上传-批量] files={}", files);
        String noGroupPath = "";//存储在fastdfs不带组的路径
        //暂时不支持多文件上传,后续版本可以再加上checkFile
        List<String> result = new ArrayList<String>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = file.getName();
                try {
                    StorePath path = storageClient.uploadFile(UpLoadConstant.DEFAULT_GROUP, file.getInputStream(), file.getSize(), FileUtil.extName(file.getOriginalFilename()));
                    logger.info("[文件上传] [{}] path={}", fileName, path);
                    if (path == null) {
                        return ApiResult.fail("上传文件服务端操作异常:获取存储后的文件路径异常-" + fileName);
                    }
                    noGroupPath = path.getPath();
                    logger.info("[文件上传] [{}] noGroupPath={}", fileName, noGroupPath);

                    //添加数据库记录：如果开关打开，则进行记录
                    boolean dblogOpen = fileLogProperties.isDblogOpen();
                    logger.info("[文件上传] [{}] 数据库记录:dblogOpen={}", fileName, dblogOpen);
                    if (dblogOpen) {
                        FileStoreLog log = new FileStoreLog();
                        log.setCreateTime(new Date());
                        log.setDelFlag("N");
                        log.setFileName(file.getOriginalFilename());
                        log.setFilePath(UpLoadConstant.DEFAULT_GROUP + "/" + noGroupPath);
                        fileLogService.addLog(log);
                        logger.info("[文件上传] [{}] 数据库记录:日志信息={}", fileName, log);
                    }

                } catch (FdfsServerException e) {
                    logger.error("[文件上传][" + fileName + "]服务端操作异常:", e);
                    if (ErrorCodeConstants.ERR_NO_ENOSPC == e.getErrorCode()) {
                        return ApiResult.fail("上传文件服务端操作异常:没有足够的存储空间");
                    } else {
                        return ApiResult.fail("上传文件服务操作异常");
                    }
                } catch (Exception e) {
                    logger.error("[文件上传][" + fileName + "]服务操作异常:", e);
                    return ApiResult.fail("上传文件服务端操作异常-" + fileName);
                }
                result.add(UpLoadConstant.DEFAULT_GROUP + "/" + noGroupPath);
            } else {
                logger.error("上传的文件内容为空");
                return ApiResult.fail("上传的文件内容为空");
            }
        }

        return ApiResult.success(result);
    }

    /**
     * 文件下载
     * 示例：http://localhost:8081/fileUpload/download?filePath=group1/M00/00/25/wKgK0FzPu1CAHXFIAXXO03NOmaw322.zip&fileName=xxx.zip
     *
     * @param filePath         文件上传返回值，绝对或相对URL地址，必填
     * @param downloadFileName 非必填，指定下载文件名称，包含后缀名：
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/download")
    @ApiOperation(value = "文件下载", notes = "/download")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", value = "文件路径，相对url或绝对url", required = true, defaultValue = "", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "downloadFileName", value = "指定文件下载名称", defaultValue = "", paramType = "query", dataType = "String")
    })
    public void download(String filePath,
                         String downloadFileName,
                         HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        logger.info("下载文件filePath-{},downloadFileName-{}", filePath, downloadFileName);
        //如果是绝对url，则截取处理
        if (filePath.toLowerCase().startsWith("http://")) {
            filePath = filePath.substring(filePath.indexOf("/group") + 1);
        }
        logger.info("下载文件filePath-{}", filePath);

        GroupPath groupPath = parseFromFullPath(filePath);
        //文件大小
        long fileSize = storageClient.queryFileInfo(groupPath.getGroup(), groupPath.getPath()).getFileSize();
        logger.info("下载文件fileSize-{}", fileSize);
        //存储文件名称
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        if (StringUtils.hasText(downloadFileName)) {
            fileName = downloadFileName;
        }

        if (!StringUtils.hasText(fileName)) {
            throw new IllegalArgumentException(String.format("文件[%s]不存在", filePath));
        }

        logger.info("下载文件fileName-{}", fileName);
        try {
            addDownloadHeader(request, response, fileName, fileSize);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("文件[%s]请求头设置异常", fileName));
        }

        OutputStream out = response.getOutputStream();
        storageClient.downloadFile(groupPath.getGroup(), groupPath.getPath(), (DownloadCallback<Void>) in -> {
            try {
                int downloadSize = IOUtils.copy(in, out);
                logger.info("downloadSize-{}", downloadSize);
            } finally {
                IOUtils.closeQuietly(in);
            }
            return null;
        });
    }

//    /**
//     * 范围查询
//     * 
//     */
//    @SuppressWarnings("rawtypes")
//	@GetMapping(value = "/search")
//    @ApiOperation(value = "范围查询", notes = "/search")
//    @ApiImplicitParams({
//    	@ApiImplicitParam(name = "startTime", value = "YYYY-MM-dd HH:mm:ss", required = true, defaultValue="", paramType = "query", dataType = "String"),
//    	@ApiImplicitParam(name = "endTime", value = "YYYY-MM-dd HH:mm:ss", required = true, defaultValue="", paramType = "query", dataType = "String")
//    })
//    @ResponseBody
//    public ApiResult search(String startTime,
//    		String endTime,
//    		HttpServletRequest request, 
//    		HttpServletResponse response)
//    				throws IOException {
//    	logger.info("[范围查询]输入参数:startTime-{},endTime-{}", startTime, endTime);
//    	//参数检查
//    	if( StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime) ) {
//    		return ApiResult.fail("[范围查询]输入参数:参数不能为空");
//    	}
//    	try {
//    		new SimpleDateFormat(dateFormat).parse(startTime);
//    		new SimpleDateFormat(dateFormat).parse(endTime);
//    	}catch(Exception e) {
//    		return ApiResult.fail("[范围查询]输入参数:参数格式异常,格式YYYY-MM-dd HH:mm:ss");
//    	}
//    	
//    	//业务数据获取
//    	int count = fileLogService.getListCount(startTime, endTime);
//    	logger.info("[范围查询]业务校验:获取结果-{}", count);
//    	
//    	return  ApiResult.success(count);
//    }

//    /**
//     * 文件数据回退-针对测试数据
//     * 
//     */
//    @SuppressWarnings("rawtypes")
//	@PostMapping(value = "/recovery")
//    @ApiOperation(value = "文件数据回退", notes = "/recovery")
//    @ApiImplicitParams({
//    	@ApiImplicitParam(name = "startTime", value = "YYYY-MM-dd HH:mm:ss", required = true, defaultValue="", paramType = "query", dataType = "String"),
//    	@ApiImplicitParam(name = "endTime", value = "YYYY-MM-dd HH:mm:ss", required = true, defaultValue="", paramType = "query", dataType = "String")
//    })
//    @ResponseBody
//    public ApiResult recovery(String startTime,
//    		String endTime,
//    		HttpServletRequest request, 
//    		HttpServletResponse response)
//    				throws IOException {
//    	//如果开关打开，则可以执行回退
//        boolean deleteOpen = fileLogProperties.isDeleteOpen();
//        logger.info("[文件数据回退]开关:deleteOpen={}", deleteOpen);
//        if( !deleteOpen ) {
//        	return ApiResult.fail("开关未开启");
//        }
//        
//    	logger.info("[文件数据回退]输入参数:startTime-{},endTime-{}", startTime, endTime);
//    	//参数检查
//    	if( StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime) ) {
//    		return ApiResult.fail("参数不能为空");
//    	}
//    	try {
//    		new SimpleDateFormat(dateFormat).parse(startTime);
//    		new SimpleDateFormat(dateFormat).parse(endTime);
//    	}catch(Exception e) {
//    		return ApiResult.fail("参数格式异常: 格式YYYY-MM-dd HH:mm:ss");
//    	}
//    	
//    	//数据量获取
//    	int count = fileLogService.getListCount(startTime, endTime);
//    	logger.info("[文件数据回退]业务校验:获取需要回退数量的结果-{}", count);
//    	//数据回退
//    	int countByRecovery = fileLogService.recovery(startTime, endTime);
//    	logger.info("[文件数据回退]业务校验:实际回退数量的结果-{}", countByRecovery);
//    	return  ApiResult.success(countByRecovery);
//    }

//	/**
//	 * 批量清理-文件-针对无效数据
//	 * 
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@PostMapping(value = "/clean/file")
//	@ApiOperation(value = "批量清理", notes = "/clean")
//    @ResponseBody
//    public ApiResult cleanForFile(@ApiParam @RequestParam(name = "file", required=true) MultipartFile file) {
//		logger.info("[批量清理] file={}", file);
//		// 如果开关打开，则可以执行回退
//		boolean deleteOpen = fileLogProperties.isDeleteOpen();
//		logger.info("[批量清理]开关:deleteOpen={}", deleteOpen);
//		if (!deleteOpen) {
//			return ApiResult.fail("开关未开启");
//		}
//
//		// 参数检查
//		if (StringUtils.isEmpty(file) ) {
//			return ApiResult.fail("参数不能为空");
//		}
//		
//		logger.info("[批量清理]输入参数:file-{},fileName-{}", file, file.getName());
//		
//		//从文件中获取目标文件的相对地址
//		int rowStartIndex = 0;//起始行号，0表示从第一行开始
//		int cellCount = 1; //有效数据列,读取第一列
//		Map<String, Object> parseResult = POIUtil.parseExcelFile(file, rowStartIndex, cellCount);
//		if( null != parseResult && "200".equals(parseResult.get("code").toString()) ) {
//			Map<String, Object> data = (Map<String, Object>) parseResult.get("data");
//			if( null != data ) {
//				List<List<String>> targetList = (List<List<String>>) data.get("0");//获取第一个sheet
//				List<String> list = new ArrayList<>();
//				for(int i=0; i<targetList.size(); i++) {
//					if( null == targetList.get(i) ) {
//						continue;
//					}
//					list.add(targetList.get(i).get(0));
//				}
//				// 数据回退
//				CleanResponse result = fileService.batchDelete(list);
//				logger.info("[批量清理]业务处理:实际处理的结果-{}", result);
//				return ApiResult.success(result);
//				
//			}
//		}
//		return ApiResult.fail("批量清理操作失败");
//	}
//    
//	/**
//	 * 批量清理-针对无效数据
//	 * 
//	 */
//	@SuppressWarnings({ "rawtypes"})
//	@PostMapping(value = "/clean/list")
//	@ApiOperation(value = "批量清理", notes = "/clean/list")
//	@ResponseBody
//	public ApiResult clean(@ApiParam @RequestParam(name = "files", required=true)List<String> files) {
//		logger.info("[批量清理] files={}", files);
//		// 如果开关打开，则可以执行回退
//		boolean deleteOpen = fileLogProperties.isDeleteOpen();
//		logger.info("[批量清理]开关:deleteOpen={}", deleteOpen);
//		if (!deleteOpen) {
//			return ApiResult.fail("开关未开启");
//		}
//		
//		// 参数检查
//		if (StringUtils.isEmpty(files) ) {
//			return ApiResult.fail("参数不能为空");
//		}
//		
//		logger.info("[批量清理]输入参数:file-{}", files);
//		
//		try {
//			// 数据回退
//			CleanResponse result = fileService.batchDelete(files);
//			logger.info("[批量清理]业务处理:实际处理的结果-{}", result);
//			return ApiResult.success(result);
//		}catch(Exception e) {
//			logger.error("[批量清理]业务处理：操作失败", e);
//			return ApiResult.fail("批量清理操作失败");
//		}
//				
//	}

    private static GroupPath parseFromFullPath(String fullPath) {
        if (!StringUtils.hasText(fullPath)) {
            throw new IllegalArgumentException(String.format("全路径[%s]不能为空", fullPath));
        }

        int firstSlash = fullPath.indexOf("/");
        String group = fullPath.substring(0, firstSlash);
        String path = fullPath.substring(firstSlash + 1);

        return new GroupPath(group, path);
    }

    private static class GroupPath {
        private String group;
        private String path;

        public String getGroup() {
            return group;
        }

        public GroupPath(String group, String path) {
            this.group = group;
            this.path = path;
        }

        @SuppressWarnings("unused")
        public void setGroup(String group) {
            this.group = group;
        }

        public String getPath() {
            return path;
        }

        @SuppressWarnings("unused")
        public void setPath(String path) {
            this.path = path;
        }
    }


    /**
     * 设置文件下载头信息
     *
     * @param request
     * @param response
     * @param fullfileName 文件名
     * @param fileSize 文件大小
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public void addDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fullfileName, long fileSize) throws Exception {

        response.setContentType("application/octet-stream");
        response.setStatus(response.SC_OK);
        response.setHeader("Content-Length", String.valueOf(fileSize));

        String agent = request.getHeader("USER-AGENT");
        String fileName = fullfileName.substring(0, fullfileName.lastIndexOf("."));
        String fileExtend = fullfileName.substring(fullfileName.lastIndexOf(".") + 1);
        if (null != agent && -1 != agent.indexOf("MSIE")) { // IE
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + "." + fileExtend);

        } else if (null != agent && -1 != agent.indexOf("Mozilla")) { // FireFox,Chrome,360
            String codedFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + codedFileName + "." + fileExtend);
        }

    }

}
