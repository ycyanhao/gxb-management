package com.youedata.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * dfs配置相关配置
 *
 * @author liujijun
 */
@Configuration
@ConfigurationProperties(prefix = DfsProperties.REST_PREFIX)
public class DfsProperties {

    public static final String REST_PREFIX = "dfs";

	/**
	 * 短信发送请求
	 */
	private String sendRequest;
	/**
	 * 请求超时时间：与服务器建立连接的超时时间
	 */
	private int connectTimeout;
	/**
	 * 传输超时时间：Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间
	 */
	private int socketTimeout;
	
	public String getSendRequest() {
		return sendRequest;
	}
	public void setSendRequest(String sendRequest) {
		this.sendRequest = sendRequest;
	}
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public int getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	
}
