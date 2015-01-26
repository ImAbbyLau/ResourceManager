package com.jzh.Terminal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jzh.Terminal.TerRequestBean;
import com.jzh.Terminal.TerminalJdbcConnect;


public class TerminalSqlRequest {
	//查询所有信息
    public static List<TerRequestBean> queryAll() {
    	String sql = "select * from terrequest;";
        List<TerRequestBean> results = new ArrayList<TerRequestBean>();
        Connection conn = TerminalJdbcConnect.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	TerRequestBean res = new TerRequestBean();
            	res.setRequestId(rs.getInt("RequestId"));
            	res.setTrafficType(rs.getInt("TrafficType"));
            	res.setTrafficImportance(rs.getInt("TrafficImportance"));
            	res.setPreference(rs.getInt("Preference"));
            	res.setVisibleNet(rs.getString("VisibleNet"));
            	res.setBandWidth(rs.getDouble("BandWidth"));
            	res.setDelay(rs.getDouble("Delay"));
            	res.setJitter(rs.getDouble("Jitter"));
            	res.setPacketLoss(rs.getDouble("PacketLoss"));
            	res.setNetType(rs.getInt("NetType"));
            	res.setNetId(rs.getInt("NetId"));
            	res.setLRRMIP(rs.getString("LRRMIP"));
            	
                results.add(res);
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
    // 保存(插入)请求信息 
    public static int save(TerRequestBean res) {
        String sql = "insert into terrequest (traffictype,trafficimportance,preference,visiblenet,BandWidth,Delay,Jitter,PacketLoss) values (?,?,?,?,?,?,?,?);";
        Connection conn = TerminalJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, res.getTrafficType());
            ps.setInt(2, res.getTrafficImportance());
            ps.setInt(3, res.getPreference());
            ps.setString(4, res.getVisibleNet());
            ps.setDouble(5, res.getBandWidth());
            ps.setDouble(6, res.getDelay());
            ps.setDouble(7, res.getJitter());
            ps.setDouble(8, res.getPacketLoss());
            ps.executeUpdate();
            
            List<TerRequestBean> results = queryAll();
            int id;
            if(results.size()==0){
            	id=1;
            } else {
            	id = results.get(results.size() - 1).getRequestId();
            }
            

            
            return id;
            
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
    
    
    
    //删除信息(根据ID)
    public static int delete(TerRequestBean res) {
    	String sql = "delete from terrequest where RequestId = "  + "'" + res.getRequestId() + "'" + ";" ;
        Connection conn = TerminalJdbcConnect.getConnection();
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
    
    
    //修改(更新)应答信息
    public static int update(TerRequestBean res) {
    	String sql = "update terrequest set LRRMIP=?,NetType=?,NetId = ? where RequestId = "  + "'" + res.getRequestId() + "'" + ";" ;
        Connection conn = TerminalJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, res.getLRRMIP());
            ps.setInt(2, res.getNetType());
            ps.setInt(3, res.getNetId());
        
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
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
} 