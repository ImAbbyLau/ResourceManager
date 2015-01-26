package com.jzh.LRRM;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//利用接口定义JDBC连接MySQL数据库的一些配置信息
public class LRRMJdblConnect {
    final static String DRIVER = "com.mysql.jdbc.Driver";
    final static String URL = "jdbc:mysql://localhost:3306/lrrm";
    final static String USERNAME = "root";
    final static String PASSWORD = "111111";
    
    // 获得数据库的连接
    static Connection getConnection() {
        Connection conn = null;
	try 
		{
			Class.forName(DRIVER);// 加载数据库驱动
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 获得数据库连接
			return conn;
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}