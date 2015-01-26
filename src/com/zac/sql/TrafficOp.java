package com.zac.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.zac.bean.TrafficBean;


public class TrafficOp {
	//查询业务信息
    public static List<TrafficBean> queryAll() {
    	String sql = "select * from traffictype;";
        List<TrafficBean> results = new LinkedList<TrafficBean>();
        Connection conn = new JdbcConnect().getConnection();;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	TrafficBean traf = new TrafficBean();
            	traf.setTrafficID(rs.getInt("idtraffictype"));
            	traf.setLatency(rs.getDouble("latency"));
            	traf.setJitter(rs.getDouble("jitter"));
            	traf.setPacketLossRatio(rs.getDouble("packetlossratio"));
            	traf.setTrafficPriority(rs.getInt("priority"));
            	traf.setTrafficTitle(rs.getString("traffictitle"));
                results.add(traf);
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
    
    //查询业务编号为id的业务信息
    /*
    public static TrafficBean query(int trafficid) {
    	String sql = "select * from traffictype idtraffictype = " + trafficid;
    	TrafficBean traf = new TrafficBean();
        Connection conn = JdbcConnect.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	traf.setTrafficId(rs.getInt("idtraffictype"));
            	traf.setLatency(rs.getDouble("latency"));
            	traf.setJitter(rs.getDouble("jitter"));
            	traf.setPacketLossRatio(rs.getDouble("packetlossratio"));
            	traf.setTrafficPriority(rs.getInt("priority"));
            	traf.setTrafficTitle(rs.getString("traffictitle"));
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
        return traf;
    }*/
    
    //修改(更新)业务信息
    public static int update(TrafficBean traf) {
    	//String sql = "update traffictype set idtraffictype = ?, latency = ?, jitter = ?, packetlossratio = ?, priority = ?, traffictitle = ?;";  主键不能覆盖
    	String sql = "update traffictype set latency = ?, jitter = ?, packetlossratio = ?, priority = ?, traffictitle = ?  where idtraffictype = " + traf.getTrafficID();
        Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            //ps.setInt(1, traf.getTrafficID());
            ps.setDouble(1, traf.getLatency());
            ps.setDouble(2, traf.getJitter());
            ps.setDouble(3, traf.getPacketLossRatio());
            ps.setInt(4, traf.getTrafficPriority());
            ps.setString(5, traf.getTrafficTitle());
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
    public static int save(TrafficBean traf) {
        String sql = "insert into traffictype (idtraffictype, latency, jitter, packetlossratio, priority, traffictitle) values (?, ?, ?, ?, ?, ?);";
        Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, traf.getTrafficID());
            ps.setDouble(2, traf.getLatency());
            ps.setDouble(3, traf.getJitter());
            ps.setDouble(4, traf.getPacketLossRatio());
            ps.setInt(5, traf.getTrafficPriority());
            ps.setString(6, traf.getTrafficTitle());
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
    public static int delete(TrafficBean traf) {
    	String sql = "delete from traffictype where idtraffictype = " + traf.getTrafficID();
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