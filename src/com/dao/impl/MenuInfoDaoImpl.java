package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.beans.MenuInfo;
import com.dao.MenuInfoDao;
import com.util.DBUtil;

public class MenuInfoDaoImpl implements MenuInfoDao{

	@Override
	public boolean addBigMenu(String menuName,int parentId) {
		Connection conn=null;
		PreparedStatement stm=null;
		try{
			conn=DBUtil.getConn();
			String sql="insert into menuinfo(menuName,parentId) values(?,?)";
			stm=conn.prepareStatement(sql);
			stm.setString(1, menuName);
			stm.setInt(2, parentId);
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
	public List<MenuInfo> queryAllMenuList(int parentId) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		List<MenuInfo> menuList=new ArrayList<MenuInfo>();
		try{
			conn=DBUtil.getConn();
			String sql="select * from menuinfo where parentId=?";
			stm=conn.prepareStatement(sql);
			stm.setInt(1, parentId);
			rs=stm.executeQuery();
			while(rs.next()){
				MenuInfo menuInfo=new MenuInfo();
				menuInfo.setId(rs.getInt("id"));
				menuInfo.setMenuName(rs.getString("menuName"));
				menuInfo.setTarget(rs.getString("target"));
				menuInfo.setUrl(rs.getString("url"));
				menuInfo.setIcon(rs.getString("icon"));
				menuInfo.setParentId(rs.getInt("parentId"));
				if(menuInfo.getParentId()==0){
					menuInfo.setSunMenuList(queryAllMenuList(menuInfo.getId()));
				}
				menuList.add(menuInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return menuList;
	}

	@Override
	public boolean addSmallMenu(MenuInfo menuInfo) {
		Connection conn=null;
		PreparedStatement stm=null;
		try{
			conn=DBUtil.getConn();
			String sql="insert into menuinfo(menuName,target,url,icon,parentId) values(?,?,?,?,?)";
			stm=conn.prepareStatement(sql);
			stm.setString(1, menuInfo.getMenuName());
			stm.setString(2, menuInfo.getTarget());
			stm.setString(3, menuInfo.getUrl());
			stm.setString(4, menuInfo.getIcon());
			stm.setInt(5, menuInfo.getParentId());
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
	public String queryMenuId(int roleId) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		String mIdStr = "";
		try{
			conn=DBUtil.getConn();
			String sql="select menuId from rolemenu where roleId=?";
			stm=conn.prepareStatement(sql);
			stm.setInt(1, roleId);
			rs=stm.executeQuery();
			while(rs.next()){
				mIdStr+=rs.getInt(1)+",";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return mIdStr;
	}

	@Override
	public List<MenuInfo> queryAllMenuList(int parentId, String ids) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		List<MenuInfo> menuList=new ArrayList<MenuInfo>();
		try{
			conn=DBUtil.getConn();
			String sql="select * from menuinfo where parentId=? and id in ("+ids+")";
			stm=conn.prepareStatement(sql);
			stm.setInt(1, parentId);
			rs=stm.executeQuery();
			while(rs.next()){
				MenuInfo menuInfo=new MenuInfo();
				menuInfo.setId(rs.getInt("id"));
				menuInfo.setMenuName(rs.getString("menuName"));
				menuInfo.setTarget(rs.getString("target"));
				menuInfo.setUrl(rs.getString("url"));
				menuInfo.setIcon(rs.getString("icon"));
				menuInfo.setParentId(rs.getInt("parentId"));
				if(menuInfo.getParentId()==0){
					menuInfo.setSunMenuList(queryAllMenuList(menuInfo.getId()));
				}
				menuList.add(menuInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return menuList;
	}

	@Override
	public int queryMenuId(String menuName) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		int id = 0;
		try{
			conn=DBUtil.getConn();
			String sql="select id from menuinfo where menuName=?";
			stm=conn.prepareStatement(sql);
			stm.setString(1, menuName);
			rs=stm.executeQuery();
			rs.next();
			id = rs.getInt("id");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return id;
	}

	@Override
	public boolean giveLimits(int menuId, String[] idStr) {
		Connection conn = null;
		PreparedStatement stm = null;
		int result = 0;
		try{
			conn = DBUtil.getConn();
			for(String roleId:idStr){
				//System.out.println(roleId);
				String sql = "insert into rolemenu(roleId,menuId) values(?,?)";
				stm = conn.prepareStatement(sql);
				stm.setInt(1, Integer.parseInt(roleId));
				stm.setInt(2, menuId);
				result = stm.executeUpdate();
			}			
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
	public boolean deleteMenu(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from menuinfo where id=?";
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
	public boolean deleteRoleMenu(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from rolemenu where menuId=?";
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
	public boolean deleteAllMenu(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from menuinfo where id=? or parentId=?";
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
	public boolean updateMenu(String menuName, int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "update menuinfo set menuName=? where id=?";
			stm = conn.prepareStatement(sql);
			stm.setString(1, menuName);		
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
	public MenuInfo queryMenu(int id) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		MenuInfo menuInfo=new MenuInfo();
		try{
			conn=DBUtil.getConn();
			String sql="select * from menuinfo where id=?";
			stm=conn.prepareStatement(sql);
			stm.setInt(1, id);
			rs=stm.executeQuery();
			if(rs.next()){
				
				menuInfo.setId(rs.getInt("id"));
				menuInfo.setMenuName(rs.getString("menuName"));
				menuInfo.setTarget(rs.getString("target"));
				menuInfo.setUrl(rs.getString("url"));
				menuInfo.setIcon(rs.getString("icon"));
				menuInfo.setParentId(rs.getInt("parentId"));
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return menuInfo;
	}

	@Override
	public String queryRoleId(int menuId) {
		Connection conn=null;
		PreparedStatement stm=null;
		ResultSet rs=null;
		String rIdStr = "";
		try{
			conn=DBUtil.getConn();
			String sql="select roleId from rolemenu where menuId=?";
			stm=conn.prepareStatement(sql);
			stm.setInt(1, menuId);
			rs=stm.executeQuery();
			while(rs.next()){
				rIdStr+=rs.getInt(1)+",";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return rIdStr;
	}

}
