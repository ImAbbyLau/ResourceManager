package com.zac.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//利用接口定义JDBC连接MySQL数据库的一些配置信息
public class JdbcConnect {
	/*
    final static String DRIVER = "com.mysql.jdbc.Driver";
    final static String URL = "jdbc:mysql://localhost:3306/test";
    final static String USERNAME = "root";
    final static String PASSWORD = "111111";
    */
    
    // 获得数据库的连接
	Connection getConnection() {
		Connection conn = null;

		Properties prop = new Properties();
		String driver= null;
		String url = null;
		String username = null;
		String password = null;
		try {
			//prop.load(this.getClass().getClassLoader().getResourceAsStream("DBconfig.properties"));
        	prop.load(new FileInputStream("src/DBconfig.properties"));
			
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
        	
			Class.forName(driver);// 加载数据库驱动
			conn = DriverManager.getConnection(url, username, password);// 获得数据库连接
			return conn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }
}
