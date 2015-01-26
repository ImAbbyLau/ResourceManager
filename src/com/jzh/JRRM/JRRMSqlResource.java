package com.jzh.JRRM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JRRMSqlResource {
	//查询所有信息
    public static List<ResourceBean> queryAll(int traffictype) {
    	String sql = "select * from resource"+traffictype+";";
        List<ResourceBean> results = new ArrayList<ResourceBean>();
        Connection conn = JRRMJdbcConnect.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	ResourceBean res = new ResourceBean();
            	res.setAddress(rs.getString("Address"));
            	res.setNetworkType(rs.getInt("NetworkType"));
            	res.setNetworkID(rs.getInt("NetworkID"));
            	res.setBandWidth(rs.getDouble("BandWidth"));
            	res.setDelay(rs.getDouble("Delay"));
            	res.setJitter(rs.getDouble("Jitter"));
            	res.setPacketLoss(rs.getDouble("PacketLoss"));
            	res.setLoadRate(rs.getDouble("LoadRate"));
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
    public static int update(int traffictype,ResourceBean res) throws SQLException {
    	//String sql = "update Resource set Address = ?, SubnetID = ?, Size = ?;";  主键不能覆盖
    	String sql = "update resource"+traffictype+" set networktype=?,networkid=?,bandWidth = ?, delay = ?, jitter = ?,packetloss = ?, loadrate = ? where address = "  + "'" + res.getAddress() + "'" + ";" ;
        Connection conn = JRRMJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, res.getNetworkType());
            ps.setInt(2, res.getNetworkID());
            ps.setDouble(3, res.getBandWidth());
            ps.setDouble(4, res.getDelay());
            ps.setDouble(5, res.getJitter());
            ps.setDouble(6, res.getPacketLoss());
            ps.setDouble(7, res.getLoadRate());
            return ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw e;
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
    }
    
    // 保存(插入)业务信息
    public static int save(int traffictype,ResourceBean res) throws SQLException {
        String sql = "insert into resource"+traffictype+" (address,networktype,networkid,bandwidth,delay,jitter,packetloss,loadrate) values (?,?,?,?,?,?,?,?);";
        Connection conn = JRRMJdbcConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, res.getAddress());
            ps.setInt(2, res.getNetworkType());
            ps.setInt(3, res.getNetworkID());
            ps.setDouble(4, res.getBandWidth());
            ps.setDouble(5, res.getDelay());
            ps.setDouble(6, res.getJitter());
            ps.setDouble(7, res.getPacketLoss());
            ps.setDouble(8, res.getLoadRate());
            return ps.executeUpdate();
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw e;
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
    }
    
    //删除业务信息(根据业务ID)
    public static int delete(int traffictype,ResourceBean res) {
    	String sql = "delete from resource"+traffictype+" where address = "  + "'" + res.getAddress() + "'" + ";" ;
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
  	public static int saveOrUpdate(ResourceBean[] res) {
        //String sql = "select * from resource"+traffictype+" where address = "  + "'" + res.getAddress() + "'" + ";" ;
        String[] sql = new String[4];
        if(sql.length != res.length) {
        	return -1;
        }
        for(int i = 0; i < sql.length; i++) {
        	int traffictype = i + 1;
        	sql[i] = "select * from resource"+traffictype+" where address = "  + "'" + res[i].getAddress() + "'" + ";" ;
        }
        Connection conn = JRRMJdbcConnect.getConnection();
  		Statement stat = null;
        ResultSet rs = null;
  		int num = -1;
  		try {
  			conn.setAutoCommit(false);
            stat = conn.createStatement();
            for(int i = 0; i < sql.length; i++) {
            	rs = stat.executeQuery(sql[i]);
                rs.last();
    			num = rs.getRow();
      			if (0 == num) {
      				JRRMSqlResource.save(i+1, res[i]);				
      			} else if (num > 0) {
      				JRRMSqlResource.update(1+1, res[i]);
      			}
            }
            conn.commit();
            return 1;
        } catch (SQLException e) {
              e.printStackTrace();
              try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
              return -1;
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
      }
	public static ResourceBean SearchVisibleNet(int traffictype,String type, String id) {
        String sql = "select * from resource"+traffictype+" where networktype = "  + "'" + type + "'" + " "+"and" +" "+ "networkid = "  + "'" + id + "'" + ";" ;
       // System.out.println(sql);
        Connection conn = JRRMJdbcConnect.getConnection();
        ResourceBean res = new ResourceBean();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            if (rs.next()) {
            	res.setAddress(rs.getString("address"));
            	res.setNetworkType(rs.getInt("networkType"));
            	res.setNetworkID(rs.getInt("networkID"));
            	res.setBandWidth(rs.getDouble("bandwidth"));
            	res.setDelay(rs.getDouble("Delay"));
            	res.setJitter(rs.getDouble("Jitter"));
            	res.setPacketLoss(rs.getDouble("PacketLoss"));
            	res.setLoadRate(rs.getDouble("LoadRate"));
            	return res;
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
        return null;
    }	
  	
  //	public static void main(String[] args) {
  //		SearchVisibleNet(1, "JUHUA", "2");
	//}

}