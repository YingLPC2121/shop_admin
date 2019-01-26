package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.beans.PageInfo;
import com.beans.RoleInfo;
import com.dao.RoleInfoDao;
import com.util.DBUtil;

public class RoleInfoDaoImpl implements RoleInfoDao{

	@Override
	public RoleInfo queryRole(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		RoleInfo roleInfo = new RoleInfo();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from roleinfo where id=?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			if(rs.next()){
				roleInfo.setId(rs.getInt("id"));
				roleInfo.setRoleName(rs.getString("roleName"));
				roleInfo.setDes(rs.getString("des"));
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return roleInfo;
	}

	@Override
	public boolean addRole(String roleName,String des) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "insert into roleinfo(roleName,des) values(?,?)";
			stm = conn.prepareStatement(sql);
			stm.setString(1, roleName);
			stm.setString(2, des);
			int result = stm.executeUpdate();
			if(result>0){
				return true;
			}		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}
		return false;
	}

	@Override
	public List<RoleInfo> queryAllRole(PageInfo pageInfo) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<RoleInfo> rList = new ArrayList<RoleInfo>();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from roleinfo limit ?,?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, pageInfo.getBeginRow());
			stm.setInt(2, pageInfo.getPageSize());
			rs = stm.executeQuery();
			while(rs.next()){
				RoleInfo roleInfo = new RoleInfo();
				roleInfo.setId(rs.getInt("id"));
				roleInfo.setRoleName(rs.getString("roleName"));
				roleInfo.setDes(rs.getString("des"));
				rList.add(roleInfo);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return rList;
	}
	
	@Override
	public List<RoleInfo> queryAllRole() {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<RoleInfo> rList = new ArrayList<RoleInfo>();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from roleinfo";
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			while(rs.next()){
				RoleInfo roleInfo = new RoleInfo();
				roleInfo.setId(rs.getInt("id"));
				roleInfo.setRoleName(rs.getString("roleName"));
				roleInfo.setDes(rs.getString("des"));
				rList.add(roleInfo);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return rList;
	}

	@Override
	public boolean updateRoleName(String adminNo, int roleId) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "update admininfo set roleId=? where adminNo=?";
			stm = conn.prepareStatement(sql);			
			stm.setInt(1, roleId);
			stm.setString(2, adminNo);		
			int result = stm.executeUpdate();
			if(result>0){
				return true;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}		
		return false;
	}

	@Override
	public int queryRoleId(String roleName) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		int roleId = 0;
		try{
			conn = DBUtil.getConn();
			String sql = "select id from roleinfo where roleName=?";
			stm = conn.prepareStatement(sql);
			stm.setString(1, roleName);
			rs = stm.executeQuery();
			if(rs.next()){
				roleId=rs.getInt("roleId");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return roleId;
	}

	@Override
	public int queryCount() {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = DBUtil.getConn();
			String sql = "select count(*) from roleinfo";
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			rs.next();
			count = rs.getInt(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return count;
	}

	@Override
	public boolean deleteRole(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from roleinfo where id=?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			int result = stm.executeUpdate();
			if(result>0){
				return true;
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}		
		return false;
	}

	@Override
	public boolean addLimits(int roleId, String[] menuIds) {
		Connection conn = null;
		PreparedStatement stm = null;
		int result = 0;
		try{
			conn = DBUtil.getConn();
			String sql1 = "delete from rolemenu where roleId=?";
			stm = conn.prepareStatement(sql1);
			stm.setInt(1, roleId);
			//conn.setAutoCommit(false); //手动管理事务----开启事务
			stm.executeUpdate();
			for(String mId:menuIds){
				//System.out.println(mId);
				String sql2 = "insert into rolemenu(roleId,menuId) values(?,?)";
				stm = conn.prepareStatement(sql2);
				stm.setInt(1, roleId);
				stm.setInt(2, Integer.parseInt(mId));
				result = stm.executeUpdate();
			}
			//conn.commit();   //提交事务		
			if(result>0){
				return true;
			}		
		}catch(Exception e){
			/*try {
				conn.rollback();//回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}  */
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}
		return false;
	}

	@Override
	public String queryAllRoleId() {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String str="";
		try{
			conn = DBUtil.getConn();
			String sql = "select id from roleinfo";
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			while(rs.next()){
				str += rs.getInt("id")+",";				
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return str;
	}

	@Override
	public boolean updateRole(int id, String roleName, String des) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "update roleinfo set roleName=?,des=? where id=?";
			stm = conn.prepareStatement(sql);
			stm.setString(1, roleName);		
			stm.setString(2, des);		
			stm.setInt(3, id);		
			int result = stm.executeUpdate();
			if(result>0){
				return true;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}		
		return false;
	}

}
