package com.zac.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.zac.bean.UserBean;


public class UserOp {
	//查询业务信息
    public static List<UserBean> queryAll() {
    	String sql = "select * from usertype;";
        List<UserBean> results = new LinkedList<UserBean>();
        Connection conn = new JdbcConnect().getConnection();;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	UserBean user = new UserBean();
            	user.setUserID(rs.getInt("idusertype"));
            	user.setUserClassification(rs.getInt("classification"));
            	user.setUserPriority(rs.getInt("priority"));
            	user.setUserSecurityPolicy(rs.getInt("securitypolicy"));
            	user.setUserTitle(rs.getString("usertitle"));
                results.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {// 释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }	
    
    //修改(更新)业务信息
    public static int update(UserBean user) {
    	//String sql = "update usertype set idusertype = ?, classification = ?, priority = ?, securitypolicy = ?, usertitle = ?;";
    	String sql = "update usertype set classification = ?, priority = ?, securitypolicy = ?, usertitle = ? where idusertype = " + user.getUserID();
        Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            //ps.setInt(1, user.getUserID());
            ps.setInt(1, user.getUserClassification());
            ps.setInt(2, user.getUserPriority());
            ps.setInt(3, user.getUserSecurityPolicy());
            ps.setString(4, user.getUserTitle());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    
    // 保存(插入)业务信息
    public static int save(UserBean user) {
        String sql = "insert into usertype (idusertype, classification, priority, securitypolicy, usertitle) values (?, ?, ?, ?, ?);";
        Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getUserID());
            ps.setInt(2, user.getUserClassification());
            ps.setInt(3, user.getUserPriority());
            ps.setInt(4, user.getUserSecurityPolicy());
            ps.setString(5, user.getUserTitle());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    
    //删除业务信息(根据业务ID)
    public static int delete(UserBean user) {
    	String sql = "delete from usertype where idusertype = " + user.getUserID();
        Connection conn = new JdbcConnect().getConnection();;
        Statement stat = null;
        try {
            stat = conn.createStatement();
            return stat.executeUpdate(sql);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {// 释放资源
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
}