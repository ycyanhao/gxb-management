package com.youedata.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class DatabaseInit {
	
	private Logger log = LoggerFactory.getLogger(DatabaseInit.class);
	
	private static String DB_FILE_PATH = "db"; //脚本存储的父目录
	private static String DB_FILE_TYPE = ".sql"; //脚本文件的后缀类型
	
    @Value("${databaseName}")
    private String databaseName;

    @Value("${spring.datasource.baseUrl}")
    private String baseUrl;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
	
    public void init() {
      log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>初始化库表检查开始... 检查数据库名称: ===> {}", databaseName);
      Statement statement = null;
      Connection connection = null;
      ScriptRunner scriptRunner = null;
      try {
          connection = DriverManager.getConnection(baseUrl, username, password);
          statement = connection.createStatement();
          String sql = "SELECT  1 from SCHEMATA WHERE 1=1 and SCHEMA_NAME = '" + databaseName + "'";
          ResultSet resultSet = statement.executeQuery(sql);
          if (resultSet.next()) {
              log.info("库表检查开始结果： 数据库{}存在，无需初始化。", databaseName);
          } else {
              log.info("库表检查开始结果： 数据库{}不存在，开始初执行初始化SQL...", databaseName);
              statement.executeUpdate("CREATE DATABASE `" + databaseName + "` DEFAULT CHARACTER SET = `utf8` COLLATE `utf8_general_ci`;");
              scriptRunner = new ScriptRunner(DriverManager.getConnection(url, username, password));
              // 语句结束符号设置
              scriptRunner.setDelimiter(";");
              scriptRunner.setStopOnError(true);
              scriptRunner.runScript(new InputStreamReader(new ClassPathResource(DB_FILE_PATH + File.separator + databaseName + DB_FILE_TYPE).getInputStream()));
              log.info("{}初始化SQL执行完成！", databaseName);
          }
          log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>初始化库表检查完成");
      } catch (FileNotFoundException e) {
      	log.error("初始化库表检查异常-脚本文件不存在", e);
      } catch (Exception e) {
      	log.error("初始化库表检查异常", e);
      } finally {
          if (null != statement) {
              try {
				statement.close();
			} catch (SQLException e) {
				log.error("初始化库表检查异常", e);
			}
          }
          if (null != connection) {
              try {
				connection.close();
			} catch (SQLException e) {
				log.error("初始化库表检查异常", e);
			}
          }
          if (null != scriptRunner) {
              scriptRunner.closeConnection();
          }
      }
    }
}
