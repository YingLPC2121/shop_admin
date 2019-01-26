package com.beans;

import java.util.List;

public class MenuInfo {

	private int id;
	private String menuName;
	private String target;
	private String url;
	private int parentId;
	private String icon;
	private List<MenuInfo> subMenuList; ////所有属于当前父亲的孩子
		
	@Override
	public String toString() {
		return "MenuInfo [id=" + id + ", menuName=" + menuName + ", target="
				+ target + ", url=" + url + ", parentId=" + parentId
				+ ", icon=" + icon + ", subMenuList=" + subMenuList + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<MenuInfo> getSubMenuList() {
		return subMenuList;
	}
	public void setSunMenuList(List<MenuInfo> subMenuList) {
		this.subMenuList = subMenuList;
	}
	
	
}
