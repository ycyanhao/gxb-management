package com.youedata.util;

import com.alibaba.fastjson.JSONObject;
import com.youedata.config.properties.DfsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * HttpClient工具类
 *
 * @author liujijun
 */
@Component
@Slf4j
public class DfsUtils {

    private static RequestConfig requestConfig = null;

    @Autowired
    private DfsProperties dfsProperties;

    private static DfsUtils httpClientUtils;

    @PostConstruct
    public void init() {
        httpClientUtils = this;
        httpClientUtils.dfsProperties = this.dfsProperties;
    }

    private static RequestConfig getRequestConfig() {
        if (null == requestConfig) {
            // 设置请求和传输超时时间
            requestConfig = RequestConfig.custom().setSocketTimeout(httpClientUtils.dfsProperties.getSocketTimeout())
                    .setConnectTimeout(httpClientUtils.dfsProperties.getConnectTimeout()).build();
        }

        return requestConfig;
    }

    /**
     * post请求传输json参数
     *
     * @param jsonParam 参数
     * @return JSONObject JSONObject
     */
    public static JSONObject httpPost(JSONObject jsonParam) {
        return httpPost(httpClientUtils.dfsProperties.getSendRequest(), jsonParam);
    }
    /**
     * post请求传输file
     *
     * @param file 参数
     * @return
     */
    public static JSONObject httpPost(File file) {
    	return httpPost(httpClientUtils.dfsProperties.getSendRequest(), file);
    }

    /**
     * post请求传输文件
     *
     * @param url url地址
     * @param targetFile 参数
     * @return
     */
    public static JSONObject httpPost(String url, File targetFile) {
    	JSONObject jsonResult = null;
    	HttpPost httpPost = null;
    	CloseableHttpClient httpClient = null;
        try {
        	// post请求返回结果
        	httpClient = HttpClients.createDefault();
        	httpPost = new HttpPost(url);
        	// 设置请求和传输超时时间
        	httpPost.setConfig(getRequestConfig());
			// 把文件转换成流对象FileBody
			FileBody bin = new FileBody(targetFile);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					// 相当于<input type="file" name="file"/>
					.addPart("file", bin).build();
			httpPost.setEntity(reqEntity);
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    log.error("post请求提交失败:{}", url, e);
                }
            }
        } catch (IOException e) {
            log.error("post请求提交失败:{}", url, e);
        } finally {
        	if( null != httpClient ) {
        		try {
					httpClient.close();
				} catch (IOException e) {
					log.error("httpClient close error");
				}
        	}
        	if( null != httpPost ) {
        		try {
        			httpPost.releaseConnection();
				} catch (Exception e) {
					log.error("httpPost close error");
				}
        	}
        }
        return jsonResult;
    }
    
    /**
     * post请求传输json参数
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
    	JSONObject jsonResult = null;
    	HttpPost httpPost = null;
    	CloseableHttpClient httpClient = null;
        try {
        	// post请求返回结果
        	httpClient = HttpClients.createDefault();
        	httpPost = new HttpPost(url);
        	// 设置请求和传输超时时间
        	httpPost.setConfig(getRequestConfig());
    		if (null != jsonParam) {
    			// 解决中文乱码问题
    			StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
    			entity.setContentEncoding("UTF-8");
    			entity.setContentType("application/json");
    			httpPost.setEntity(entity);
    		}
    		CloseableHttpResponse result = httpClient.execute(httpPost);
    		// 请求发送成功，并得到响应
    		if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
    			String str = "";
    			try {
    				// 读取服务器返回过来的json字符串数据
    				str = EntityUtils.toString(result.getEntity(), "utf-8");
    				// 把json字符串转换成json对象
    				jsonResult = JSONObject.parseObject(str);
    			} catch (Exception e) {
    				log.error("post请求提交失败:{}", url, e);
    			}
    		}
    	 } catch (IOException e) {
             log.error("post请求提交失败:{}", url, e);
         } finally {
         	if( null != httpClient ) {
         		try {
 					httpClient.close();
 				} catch (IOException e) {
 					log.error("httpClient close error");
 				}
         	}
         	if( null != httpPost ) {
         		try {
         			httpPost.releaseConnection();
 				} catch (Exception e) {
 					log.error("httpPost close error");
 				}
         	}
         }
    	return jsonResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url      url地址
     * @param strParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, String strParam) {
    	JSONObject jsonResult = null;
    	HttpPost httpPost = null;
    	CloseableHttpClient httpClient = null;
        try {
        	// post请求返回结果
        	httpClient = HttpClients.createDefault();
        	httpPost = new HttpPost(url);
        	// 设置请求和传输超时时间
        	httpPost.setConfig(getRequestConfig());
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    log.error("post请求提交失败:{}", url, e);
                }
            }
        } catch (IOException e) {
            log.error("post请求提交失败:{}", url, e);
        } finally {
        	if( null != httpClient ) {
        		try {
					httpClient.close();
				} catch (IOException e) {
					log.error("httpClient close error");
				}
        	}
        	if( null != httpPost ) {
        		try {
        			httpPost.releaseConnection();
				} catch (Exception e) {
					log.error("httpPost close error");
				}
        	}
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient httpClient = null;
        HttpGet request = null;
        try {
        	httpClient = HttpClients.createDefault();
        	// 发送get请求
        	request = new HttpGet(url);
        	request.setConfig(getRequestConfig());
            CloseableHttpResponse response = httpClient.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
            } else {
                log.error("get请求提交失败:{}", url);
            }
        } catch (IOException e) {
            log.error("post请求提交失败:{}", url, e);
        } finally {
        	if( null != httpClient ) {
        		try {
					httpClient.close();
				} catch (IOException e) {
					log.error("httpClient close error");
				}
        	}
        	if( null != request ) {
        		try {
        			request.releaseConnection();
				} catch (Exception e) {
					log.error("httpPost close error");
				}
        	}
        }
        return jsonResult;
    }

}