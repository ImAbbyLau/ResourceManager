package com.zac.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 *  ģ�����ӳأ���װ���ӳأ�
 */
public final class JDBCUtil {
    private static DataSourcePool dsp = null;
 
    public JDBCUtil() throws Exception {
         
    }
 
    /**
     * �������
     * @return
     * @throws Exception
     */
    public static Connection getConnection( ) throws Exception {
        if (dsp == null) {
            synchronized (DataSourcePool.class) {
                if (dsp == null) {
                    dsp = new DataSourcePool();
                }
            }
        }
        return dsp.getConnection();
    }
 
    // �ر�����
    public static void freeConnection(ResultSet rs, Statement ps, Connection con)
            throws SQLException {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    dsp.freeConnection(con);
                }
            }
        }
    }
    // �ر�����
    public static void freeConnection(Connection con)
            throws SQLException {
                if (con != null) {
                    dsp.freeConnection(con);
                }
    }
      
}