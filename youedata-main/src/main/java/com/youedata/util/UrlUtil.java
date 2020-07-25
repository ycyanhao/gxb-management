package com.youedata.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Url工具类
 * @author liujijun
 *
 */
public class UrlUtil {

	static Logger logger = LoggerFactory.getLogger(UrlUtil.class);

	/**
	 * 获取URL文件的大小（byte）
	 * @param urlPath
	 * @return
	 */
	public static long getFileSize(String urlPath) {
		logger.info("文件路径为：{}",urlPath);
		long fileSize = 0;
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10 * 1000);
			String size = conn.getHeaderField("Content-Length");
			logger.info("文件大小为：{}", size);
			fileSize = Long.parseLong(size);
		} catch (Exception e) {
			logger.error("文件大小获取失败", e);
		}
		return fileSize;
	}
	
	/**
	 * 获取URL文件的大小（MB）
	 * @param urlPath
	 * @return
	 */
	public static double getFileSizeByMB(String urlPath) {
		double fileSize = 0L;
		if( getFileSize(urlPath) > 0 ) {
			fileSize = getFileSize(urlPath) * 1.0 / (1024 * 1024);
		}
		return fileSize;
	}
	
	/**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static File downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();  
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = null;
        try {
        	fos = new FileOutputStream(file);     
        	fos.write(getData); 
        	fos.close();  
        }catch(Exception e) {
        	logger.error("error:",e);
        	if( null != fos ) {
        		try {
        			fos.close();
				} catch (IOException e1) {
					logger.error("close FileOutputStream error:", e1);
				}
        	}
        }
        inputStream.close();
        return new File(savePath, fileName);
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        return bos.toByteArray();  
    }  

    public static void main(String[] args) {
        try{
            downLoadFromUrl("http://101.95.48.97:8005/res/upload/interface/apptutorials/manualstypeico/6f83ce8f-0da5-49b3-bac8-fd5fc67d2725.png",
                    "百度.jpg","d:/resource/images/diaodiao/country/");
        }catch (Exception e) {
        	logger.error("error:",e);
        }
    }
}
