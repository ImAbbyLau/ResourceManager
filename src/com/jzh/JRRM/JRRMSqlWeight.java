package com.jzh.JRRM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JRRMSqlWeight {
	//查询所有信息
    public static List<WeightBean> queryAll() {
    	String sql = "select * from qosweight;";
        List<WeightBean> results = new ArrayList<WeightBean>();
        Connection conn = JRRMJdbcConnect.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	WeightBean res = new WeightBean();
            	res.setTrafficType(rs.getInt("TrafficType"));
            	res.setDelay(rs.getDouble("Delay"));
            	res.setJitter(rs.getDouble("Jitter"));
            	res.setPacketLoss(rs.getDouble("PacketLoss"));
            	res.setBandWidth(rs.getDouble("BandWidth"));
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
    

    //修改(更新)业务信息
    public static int update(WeightBean res) {
    	//String sql = "update Resource set Address = ?, SubnetID = ?, Size = ?;";  主键不能覆盖
    	String sql = "update qosweight set traffictype=?, delay = ?, jitter = ?,packetloss = ?, bandWidth = ? where traffictype = "  + "'" + res.getTrafficType() + "'" + ";" ;
        Connection conn = JRRMJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, res.getTrafficType());
            ps.setDouble(2, res.getDelay());
            ps.setDouble(3, res.getJitter());
            ps.setDouble(4, res.getPacketLoss());
            ps.setDouble(5, res.getBandWidth());
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
    public static int save(WeightBean res) {
        String sql = "insert into qosweight (traffictype,delay,jitter,packetloss,bandwidth) values (?,?,?,?,?);";
        Connection conn = JRRMJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			System.out.println(res.getTrafficType());
            ps.setInt(1, res.getTrafficType());     
            ps.setDouble(2, res.getDelay());
            ps.setDouble(3, res.getJitter());
            ps.setDouble(4, res.getPacketLoss());
            ps.setDouble(5, res.getBandWidth());
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
    public static int delete(WeightBean res) {
    	String sql = "delete from qosweight where TrafficType = "  + "'" + res.getTrafficType() + "'" + ";" ;
        Connection conn = JRRMJdbcConnect.getConnection();
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
    //保存或者插入表现
  	public static int saveOrUpdate(WeightBean res) {
        String sql = "select * from qosweight where traffictype = "  + "'" + res.getTrafficType() + "'" + ";" ;
        Connection conn = JRRMJdbcConnect.getConnection();
  		Statement stat = null;
        ResultSet rs = null;
        int num = -1;
  		try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            rs.last();
			num = rs.getRow();
  			if (0 == num) {
  				return JRRMSqlWeight.save(res);		
  			} else  {
  				return JRRMSqlWeight.update(res);	
  				
  			}
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              if (rs != null) {
                  try {
                      rs.close();
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