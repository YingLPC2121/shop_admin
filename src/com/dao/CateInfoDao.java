package com.dao;

import java.util.List;

import com.beans.CateInfo;

public interface CateInfoDao {

	//添加一、二级分类
	public boolean addBcAndSc(String cateName,String cateDes,int parentId);
	//查询所有分类---分类管理
	public List<CateInfo> queryAllCateList(int parentId);
	//删除分类
	public boolean deleteCate(int id);
	public boolean deleteAllCate(int id);
	//修改分类
	public boolean uodateCate(int id,String cateName);
}
