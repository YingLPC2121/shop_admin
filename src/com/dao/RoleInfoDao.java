package com.dao;

import java.util.List;

import com.beans.PageInfo;
import com.beans.RoleInfo;

public interface RoleInfoDao {

	//根据roleId查询
	public RoleInfo queryRole(int id);
	//添加角色
	public boolean addRole(String roleName,String des);
	//查询所有角色
	public List<RoleInfo> queryAllRole(PageInfo pageInfo);
	public List<RoleInfo> queryAllRole();
	public String queryAllRoleId(); //roleinfo
	//角色分配
	public boolean updateRoleName(String adminNo,int roleId);
	//根据roleName查id
	public int queryRoleId(String roleName);
	//查询所有角色条数
	public int queryCount();
	//根据roleId删除 
	public boolean deleteRole(int id);
	//角色权限分配
	public boolean addLimits(int roleId,String [] menuIds);
	//修改角色
	public boolean updateRole(int id,String roleName,String des);
}
