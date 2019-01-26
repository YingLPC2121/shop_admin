package com.beans;

public class AdminInfo {

	private int id;
	private String adminNo;
	private String password;
	private String adminName;
	private int isLock;
	private String description;
	private int roleId;
	private String roleName;
	private String roleDes;
	
	
	
	@Override
	public String toString() {
		return "AdminInfo [id=" + id + ", adminNo=" + adminNo + ", password="
				+ password + ", adminName=" + adminName + ", isLock=" + isLock
				+ ", description=" + description + ", roleId=" + roleId
				+ ", roleName=" + roleName + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAdminNo() {
		return adminNo;
	}
	public void setAdminNo(String adminNo) {
		this.adminNo = adminNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public int getIsLock() {
		return isLock;
	}
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDes() {
		return roleDes;
	}

	public void setRoleDes(String roleDes) {
		this.roleDes = roleDes;
	}
	
	
	
}
