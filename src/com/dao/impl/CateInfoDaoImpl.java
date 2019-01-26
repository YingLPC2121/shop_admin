package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.beans.AdminInfo;
import com.beans.CateInfo;
import com.dao.CateInfoDao;
import com.util.DBUtil;

public class CateInfoDaoImpl implements CateInfoDao{

	@Override
	public boolean addBcAndSc(String cateName,String cateDes,int parentId) {
		Connection conn=null;
		PreparedStatement stm=null;
		try{
			conn=DBUtil.getConn();
			String sql="insert into cateinfo(cateName,cateDes,parentId) values(?,?,?)";
			stm=conn.prepareStatement(sql);
			stm.setString(1, cateName);
			stm.setString(2, cateDes);
			stm.setInt(3, parentId);
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
	public List<CateInfo> queryAllCateList(int parentId) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		List<CateInfo> cateList=new ArrayList<CateInfo>();
		try{
			conn=DBUtil.getConn();
			String sql="select * from cateinfo where parentId=?";
			stm=conn.prepareStatement(sql);
			stm.setInt(1, parentId);
			rs=stm.executeQuery();
			while(rs.next()){
				CateInfo cateInfo=new CateInfo();
				cateInfo.setId(rs.getInt("id"));
				cateInfo.setCateName(rs.getString("cateName"));
				cateInfo.setCateDes(rs.getString("cateDes"));
				cateInfo.setParentId(rs.getInt("parentId"));
				if(cateInfo.getParentId()==0){
					cateInfo.setSubCateList(queryAllCateList(cateInfo.getId()));
				}
				cateList.add(cateInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return cateList;
	}
	

	@Override
	public boolean deleteCate(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from cateinfo where id=?";
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
	public boolean deleteAllCate(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from cateinfo where id=? or parentId=?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			stm.setInt(2, id);
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
	public boolean uodateCate(int id, String cateName) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "update cateinfo set cateName=? where id=?";
			stm = conn.prepareStatement(sql);			
			stm.setString(1, cateName);
			stm.setInt(2, id);
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
