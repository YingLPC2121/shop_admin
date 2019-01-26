package com.dao;

import java.util.List;

import com.beans.AdminInfo;
import com.beans.PageInfo;

public interface AdminInfoDao {

	//根据用户名查询
	public AdminInfo queryAll(String adminNo);
	//将isLock归0/+1
	public boolean updateIsLock(int isLock,String adminNo);
	//查询所有管理员用户
	public List<AdminInfo> queryAllAdmin();
	//查询管理员所有数据条数
	public int queryCount();
	//根据分页的信息查询所有管理员用户
	public List<AdminInfo> queryAllAdmin(PageInfo pageInfo);
	//上锁
	public boolean updateIsLock(String adminNo);
	//添加管理员
	public boolean addAdmin(String adminNo,String adminName,String password,String description,int isLock);
	//根据管理员用户名(adminNo)修改信息
	public boolean updateAdmin(String adminNo,String adminName,String description);
	//根据管理员adminNo删除
	public boolean deleteAdmin(String adminNo);
}
