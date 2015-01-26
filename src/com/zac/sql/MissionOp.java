package com.zac.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.zac.bean.MissionBean;


public class MissionOp {
	//查询业务信息
    public static List<MissionBean> queryAll() {
    	String sql = "select * from missiontype;";
        List<MissionBean> results = new LinkedList<MissionBean>();
        Connection conn = new JdbcConnect().getConnection();;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	MissionBean mission = new MissionBean();
            	mission.setMissionID(rs.getInt("idmissiontype"));
            	mission.setMissionBandWidth(rs.getDouble("bandwidth"));
            	mission.setMissionPriority(rs.getInt("priority"));
            	mission.setMissionReliability(rs.getDouble("reliability"));
            	mission.setMissionResponseTime(rs.getDouble("responsetime"));
            	mission.setMissionTitle(rs.getString("missiontitle"));
                results.add(mission);
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
    public static int update(MissionBean mission) {
    	//String sql = "update missiontype set idmissiontype = ?, bandwidth = ?, priority = ?, reliability = ?, responsetime = ?, missiontitle = ?;";
    	String sql = "update missiontype set bandwidth = ?, priority = ?, reliability = ?, responsetime = ?, missiontitle = ? where idmissiontype =" + mission.getMissionID();
        Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            //ps.setInt(1, mission.getMissionID());
            ps.setDouble(1, mission.getMissionBandWidth());
            ps.setInt(2, mission.getMissionPriority());
            ps.setDouble(3, mission.getMissionReliability());
            ps.setDouble(4, mission.getMissionResponseTime());
            ps.setString(5, mission.getMissionTitle());
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
    public static int save(MissionBean mission) {
        String sql = "insert into missiontype (idmissiontype, bandwidth, priority, reliability, responsetime, missiontitle) values (?, ?, ?, ?, ?, ?);";
        Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, mission.getMissionID());
            ps.setDouble(2, mission.getMissionBandWidth());
            ps.setInt(3, mission.getMissionPriority());
            ps.setDouble(4, mission.getMissionReliability());
            ps.setDouble(5, mission.getMissionResponseTime());
            ps.setString(6, mission.getMissionTitle());
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
    public static int delete(MissionBean mission) {
    	String sql = "delete from missiontype where idmissiontype = " + mission.getMissionID();
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