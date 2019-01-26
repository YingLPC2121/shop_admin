package com.dao;

import java.util.List;

import com.beans.MenuInfo;


public interface MenuInfoDao {

	//添加一、二级菜单
	public boolean addBigMenu(String menuName,int parentId);
	public boolean addSmallMenu(MenuInfo menuInfo);
	//查询所有菜单
	public List<MenuInfo> queryAllMenuList(int parentId);
	public List<MenuInfo> queryAllMenuList(int parentId,String ids );
	//通过roleId查找menuId
	public String queryMenuId(int roleId);
	//通过菜单名找Id
	public int queryMenuId(String menuName);
	//添加权限 -- 分配权限
	public boolean giveLimits(int menuId,String [] idStr);
	//删除菜单 
	public boolean deleteMenu(int id);
	public boolean deleteRoleMenu(int id);
	public boolean deleteAllMenu(int id);
	//修改菜单
	public boolean updateMenu(String menuName,int id);
	public MenuInfo queryMenu(int id);  //menuinfo表查	
	public String queryRoleId(int menuId); //通过menuId查找
	
	
}
