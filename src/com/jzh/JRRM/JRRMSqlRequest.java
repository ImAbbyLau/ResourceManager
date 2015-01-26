package com.jzh.JRRM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jzh.Terminal.TerRequestBean;
import com.jzh.Terminal.TerminalJdbcConnect;


public class JRRMSqlRequest {
	//查询所有信息
    public static List<RequestBean> queryAll() {
    	String sql = "select * from request;";
        List<RequestBean> results = new ArrayList<RequestBean>();
        Connection conn = JRRMJdbcConnect.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	RequestBean res = new RequestBean();
            	res.setAddress(rs.getString("Address"));
            	res.setPackageID(rs.getInt("PackageID"));
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
    

    //修改(更新)业务信息
    /*
    public static int update(RequestBean res) {
    	//String sql = "update Request set Address = ?, SubnetID = ?, Size = ?;";  主键不能覆盖
    	String sql = "update Request set Size = ?  where Address = "  + "'" + res.getAddress() + "'" + ";" ;
        Connection conn = JRRMJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            
            ps.setDouble(1,res.getSize());
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
    
    */
    // 保存(插入)业务信息
    public static int save(RequestBean res) {
        String sql = "insert into request (address,traffictype,trafficimportance,preference,visiblenet,packageID,BandWidth,Delay,Jitter,PacketLoss) values (?,?,?,?,?,?,?,?,?,?);";
        Connection conn = JRRMJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, res.getAddress());
            ps.setInt(2, res.getTrafficType());
            ps.setInt(3, res.getTrafficImportance());
            ps.setInt(4, res.getPreference());
            ps.setString(5, res.getVisibleNet());
            ps.setInt(6, res.getPackageID());
            ps.setDouble(7, res.getBandWidth());
            ps.setDouble(8, res.getDelay());
            ps.setDouble(9, res.getJitter());
            ps.setDouble(10, res.getPacketLoss());
           
            
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
    
    
    
    //修改(更新)应答信息
    public static int update(RequestBean res) {
    	String sql = "update request set LRRMIP=?,NetType=?,NetId = ? where Address = "  + "'" + res.getAddress() + "'" +" and PackageID="+ "'" + res.getPackageID()+"'"+";" ;
        Connection conn = JRRMJdbcConnect.getConnection();
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
    
    
    
    
    
    
    
    
    
    
    //删除业务信息(根据业务ID)
    public static int delete(RequestBean res) {
    	String sql = "delete from request where Address = "  + "'" + res.getAddress() + "'" + ";" ;
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
    
  	public static int count(RequestBean res) {
        String sql = "select * from request where Address = "  + "'" + res.getAddress() + "'" + ";" ;
        Connection conn = JRRMJdbcConnect.getConnection();
  		Statement stat = null;
        ResultSet rs = null;
  		int num = 0;
  		try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            rs.last();
			num = rs.getRow();
			//System.out.println("num:"+num);
  			//if (0 == num) {
  			//	return JRRMSqlRequest.save(res);				
  			//} else if (num > 0) {
  			//	return JRRMSqlRequest.update(res);
  			//}
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
          return num+1;
      }
	
}
    