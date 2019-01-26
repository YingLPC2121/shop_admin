package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.beans.AdminInfo;
import com.beans.PageInfo;
import com.dao.AdminInfoDao;
import com.dao.RoleInfoDao;
import com.util.DBUtil;

public class AdminInfoDaoImpl implements AdminInfoDao{


	@Override
	public AdminInfo queryAll(String adminNo) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		AdminInfo adminInfo=null;
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		try{
			conn=DBUtil.getConn();
			String sql="select * from admininfo where adminNo=?";
			stm=conn.prepareStatement(sql);
			stm.setString(1, adminNo);
			rs=stm.executeQuery();
			if(rs.next()){
				adminInfo=new AdminInfo();
				adminInfo.setId(rs.getInt("id"));
				adminInfo.setAdminNo(rs.getString("adminNo"));
				adminInfo.setPassword(rs.getString("password"));
				adminInfo.setAdminName(rs.getString("adminName"));
				adminInfo.setIsLock(rs.getInt("isLock"));
				adminInfo.setDescription(rs.getString("description"));
				adminInfo.setRoleId(rs.getInt("roleId"));
				adminInfo.setRoleName(roleInfoDao.queryRole(rs.getInt("roleId")).getRoleName());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return adminInfo;
	}

	@Override
	public boolean updateIsLock(int isLock,String adminNo) {
		Connection conn=null;
		PreparedStatement stm=null;
		try{
			conn=DBUtil.getConn();
			String sql="update admininfo set isLock=? where adminNo=?";
			stm=conn.prepareStatement(sql);
			stm.setInt(1, isLock);
			stm.setString(2, adminNo);
			int result=stm.executeUpdate();
			if(result>0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}
		return false;
	}

	@Override
	public List<AdminInfo> queryAllAdmin() {		
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		List<AdminInfo> adminList = new ArrayList<AdminInfo>();
		try{
			conn=DBUtil.getConn();
			String sql="select * from admininfo ";
			stm=conn.prepareStatement(sql);
			rs=stm.executeQuery();
			while(rs.next()){
				AdminInfo adminInfo=new AdminInfo();
				adminInfo.setId(rs.getInt("id"));
				adminInfo.setAdminNo(rs.getString("adminNo"));
				adminInfo.setPassword(rs.getString("password"));
				adminInfo.setAdminName(rs.getString("adminName"));
				adminInfo.setIsLock(rs.getInt("isLock"));
				adminInfo.setDescription(rs.getString("description"));
				adminList.add(adminInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return adminList;
	}

	@Override
	public int queryCount() {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = DBUtil.getConn();
			String sql = "select count(*) from admininfo";
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
	public List<AdminInfo> queryAllAdmin(PageInfo pageInfo) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<AdminInfo> adminList = new ArrayList<AdminInfo>();
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from admininfo limit ?,?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, pageInfo.getBeginRow());
			stm.setInt(2, pageInfo.getPageSize());
			rs = stm.executeQuery();
			while(rs.next()){
				AdminInfo adminInfo=new AdminInfo();
				adminInfo.setId(rs.getInt("id"));
				adminInfo.setAdminNo(rs.getString("adminNo"));
				adminInfo.setPassword(rs.getString("password"));
				adminInfo.setAdminName(rs.getString("adminName"));
				adminInfo.setIsLock(rs.getInt("isLock"));
				adminInfo.setDescription(rs.getString("description"));
				adminInfo.setRoleName(roleInfoDao.queryRole(rs.getInt("roleId")).getRoleName());
				adminInfo.setRoleDes(roleInfoDao.queryRole(rs.getInt("roleId")).getDes());
				adminInfo.setRoleId(rs.getInt("roleId"));
				adminList.add(adminInfo);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		
		return adminList;
	}

	@Override
	public boolean updateIsLock(String adminNo) {
		Connection conn=null;
		PreparedStatement stm=null;
		try{
			conn=DBUtil.getConn();
			String sql="update admininfo set isLock=3 where adminNo=?";
			stm=conn.prepareStatement(sql);
			stm.setString(1, adminNo);
			int result=stm.executeUpdate();
			if(result>0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}
		return false;
	}

	@Override
	public boolean addAdmin(String adminNo, String adminName, String password,
			String description, int isLock) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "insert into admininfo(adminNo,adminName,password,description,isLock) values(?,?,?,?,?)";
			stm = conn.prepareStatement(sql);
			stm.setString(1, adminNo);
			stm.setString(2, adminName);
			stm.setString(3, password);
			stm.setString(4, description);
			stm.setInt(5, isLock);
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
	public boolean updateAdmin(String adminNo,String adminName,String description) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "update admininfo set adminName=?,description=? where adminNo=?";
			stm = conn.prepareStatement(sql);			
			stm.setString(1, adminName);
			stm.setString(2, description);
			stm.setString(3, adminNo);
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
	public boolean deleteAdmin(String adminNo) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from admininfo where adminNo=?";
			stm = conn.prepareStatement(sql);
			stm.setString(1, adminNo);
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
