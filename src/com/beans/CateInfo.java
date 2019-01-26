package com.beans;

import java.util.List;

public class CateInfo {

	private Integer id;
	private String cateName;
	private String cateDes;
	private int parentId;
	private List<CateInfo> subCateList; //所有属于当前父亲的孩子
				
	@Override
	public String toString() {
		return "CateInfo [id=" + id + ", cateName=" + cateName + ", cateDes="
				+ cateDes + ", parentId=" + parentId + ", subCateList="
				+ subCateList + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateDes() {
		return cateDes;
	}
	public void setCateDes(String cateDes) {
		this.cateDes = cateDes;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public List<CateInfo> getSubCateList() {
		return subCateList;
	}
	public void setSubCateList(List<CateInfo> subCateList) {
		this.subCateList = subCateList;
	}
	
	
	
	
}
