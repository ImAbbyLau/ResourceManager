package com.zac.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//���ýӿڶ���JDBC����MySQL���ݿ��һЩ������Ϣ
public class JdbcConnect {
	/*
    final static String DRIVER = "com.mysql.jdbc.Driver";
    final static String URL = "jdbc:mysql://localhost:3306/test";
    final static String USERNAME = "root";
    final static String PASSWORD = "111111";
    */
    
    // ������ݿ������
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
        	
			Class.forName(driver);// �������ݿ�����
			conn = DriverManager.getConnection(url, username, password);// ������ݿ�����
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
