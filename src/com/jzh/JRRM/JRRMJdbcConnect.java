package com.jzh.JRRM;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//���ýӿڶ���JDBC����MySQL���ݿ��һЩ������Ϣ
public class JRRMJdbcConnect {
    final static String DRIVER = "com.mysql.jdbc.Driver";
    final static String URL = "jdbc:mysql://localhost:3306/jrrm";
    final static String USERNAME = "root";
    final static String PASSWORD = "111111";
    
    // ������ݿ������
    static Connection getConnection() {
        Connection conn = null;
	try 
		{
			Class.forName(DRIVER);// �������ݿ�����
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// ������ݿ�����
			return conn;
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
