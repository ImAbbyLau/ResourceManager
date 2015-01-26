package com.zac.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.LinkedList;
import java.util.List;


import com.zac.algorithm.madmBasedImportance;
import com.zac.algorithm.tableBasedImportance;
import com.zac.bean.*;

public class EvaResultOp {
	//查询评估结果
	public static List<ResultBean>queryAll () {
		
		String sql = "select * from evaluateresult;";
		List<ResultBean> results = new LinkedList<ResultBean>();
        Connection conn = new JdbcConnect().getConnection();;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            	ResultBean result = new ResultBean();
            	result.setResultID(rs.getInt("idresult"));
            	result.setTrafficTitleofResult(rs.getString("trafficname"));
            	result.setUserTitleofResult(rs.getString("username"));
            	result.setMissionTitleofResult(rs.getString("missionname"));
            	result.setTwoDevImp(rs.getInt("twodevimp"));
            	result.setThreeDevImp(rs.getInt("threedevimp"));
            	result.setMADMDevImp(rs.getInt("madmdevimp"));  
            	result.setMADMValue(rs.getInt("madmvalue"));
                results.add(result);
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
	
	//删除所有结果
	public static int deleteAll () {
    	String sql = "delete from evaluateresult;";
        Connection conn = new JdbcConnect().getConnection();
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
	
	//更新全部评估结果
	public static int updateAll () {
		deleteAll();   //先删除所有的原有表格信息		
		
		String sql = "insert into evaluateresult (idresult, trafficname, username, missionname, twodevimp, threedevimp, madmdevimp, madmvalue) values (?, ?, ?, ?, ?, ?, ?, ?);";
		//String sql = "insert into evaluateresult (idresult, trafficname, username, missionname, twodevimp, threedevimp) values (?, ?, ?, ?, ?, ?);";
		Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
		
        List<ResultBean> results = new LinkedList<ResultBean>();
		List<TrafficBean> traf = TrafficOp.queryAll();
		List<UserBean> user = UserOp.queryAll();
		List<MissionBean> mission = MissionOp.queryAll();
		int[] madmImportance = madmBasedImportance.getMADMImportance();
		double[] madmValue = madmBasedImportance.getMADMImportanceOfValue();
		//执行插入操作
		int index = 1;
		for (TrafficBean tempTraf : traf) {
			for (UserBean tempUser : user) {
				for (MissionBean tempMission : mission) {
					ResultBean res = new ResultBean();
					res.setResultID(index);
					res.setTrafficTitleofResult(tempTraf.getTrafficTitle());
					res.setUserTitleofResult(tempUser.getUserTitle());
					res.setMissionTitleofResult(tempMission.getMissionTitle());
					res.setTwoDevImp(tableBasedImportance.twoDev(tempUser, tempTraf));
					res.setThreeDevImp(tableBasedImportance.threeDev(tempUser, tempTraf, tempMission));
					res.setMADMDevImp(madmImportance[index - 1]);
					res.setMADMValue(madmValue[index - 1]);
					index ++;
					results.add(res);
				}
			}
		}
		try {
			for (ResultBean res : results) {
				//存入数据库
				ps = conn.prepareStatement(sql);
				ps.setInt(1, res.getResultID());
				ps.setString(2, res.getTrafficTitleofResult());
				ps.setString(3, res.getUserTitleofResult());
				ps.setString(4, res.getMissionTitleofResult());
				ps.setInt(5, res.getTwoDevImp());
				ps.setInt(6, res.getThreeDevImp());
				ps.setInt(7, res.getMADMDevImp());
				ps.setDouble(8, res.getMADMValue());
				ps.executeUpdate();		
			}	            
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
	
	//更新基于表的重要性评估结果
	public static int updateTableBased () {	

		String sql = "update evaluateresult set twodevimp = ?, threedevimp =? where idresult = ";
		Connection conn = new JdbcConnect().getConnection();;
        PreparedStatement ps = null;
		
		List<ResultBean> results = new LinkedList<ResultBean>();
		List<TrafficBean> traf = TrafficOp.queryAll();
		List<UserBean> user = UserOp.queryAll();
		List<MissionBean> mission = MissionOp.queryAll();
		
		//执行插入操作
		int index = 1;
		for (TrafficBean tempTraf : traf) {
			for (UserBean tempUser : user) {
				for (MissionBean tempMission : mission) {
					ResultBean res = new ResultBean();
					res.setResultID(index);
					res.setTwoDevImp(tableBasedImportance.twoDev(tempUser, tempTraf));
					res.setThreeDevImp(tableBasedImportance.threeDev(tempUser, tempTraf, tempMission));			
					index ++;
					results.add(res);
				}
			}
		}
		try {
			for (ResultBean res : results) {
				//存入数据库
				ps = conn.prepareStatement(sql + res.getResultID());
				ps.setInt(1, res.getTwoDevImp());
				ps.setInt(2, res.getThreeDevImp());
				ps.executeUpdate();		
			}	            
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