package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.AdminInfo;
import com.beans.PageInfo;
import com.beans.RoleInfo;
import com.dao.AdminInfoDao;
import com.dao.RoleInfoDao;
import com.dao.impl.AdminInfoDaoImpl;
import com.dao.impl.RoleInfoDaoImpl;
import com.util.PageUtil;

public class RoleInfoServlet extends HttpServlet{

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	
		String flag = request.getParameter("flag");
		//创建角色
		if("roleAdd".equals(flag)){
			this.roleAdd(request, response);
		}
		//查询所有的管理员
		if("queryAdminRole".equals(flag)){
			this.queryAdminRole(request, response);
		}		
		if("queryAllRole".equals(flag)){
			this.queryAllRole(request, response);
		}
		//角色分配  
		if("assignRole".equals(flag)){
			this.assignRole(request, response);
		}
		//删除角色
		if("deleteRole".equals(flag)){
			this.deleteRole(request, response);
		}
		//角色分配权限
		if("addRoleMenu".equals(flag)){			
			this.addRoleMenu(request, response);
		}
		//角色分配权限
		if("updateRole".equals(flag)){			
			this.updateRole(request, response);
		}
		
		
	}
	
	public void roleAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String roleName = request.getParameter("roleName");
		String des = request.getParameter("des");
		
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		if(roleInfoDao.addRole(roleName, des)){
			request.setAttribute("msg", "角色创建成功");
			request.getRequestDispatcher("role/roleAdd.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "角色创建失败");
			request.getRequestDispatcher("role/roleAdd.jsp").forward(request, response);
		}
	}
	
	public void queryAdminRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		int pageIndex=Integer.parseInt(request.getParameter("pageIndex")); //获取当前页数
		
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		
		int rowCount = adminInfoDao.queryCount();//总页数
		PageInfo pageInfo = PageUtil.getPageInfo(PageUtil.PAGESIZE, pageIndex, rowCount); //得到分页信息
		List<AdminInfo> adminList = adminInfoDao.queryAllAdmin(pageInfo);
		request.setAttribute("adminList", adminList);
		request.setAttribute("pageInfo", pageInfo);		
		request.getRequestDispatcher("role/allAdmin.jsp").forward(request, response);		
		
	}
	
	public void queryAllRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		int pageIndex=Integer.parseInt(request.getParameter("pageIndex")); //获取当前页数
		
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		int rowCount = roleInfoDao.queryCount();//总页数
		PageInfo pageInfo = PageUtil.getPageInfo(PageUtil.PAGESIZE, pageIndex, rowCount); //得到分页信息
		List<RoleInfo> roleList = roleInfoDao.queryAllRole(pageInfo);
				
		request.setAttribute("roleList", roleList);
		request.setAttribute("pageInfo", pageInfo);	
		
		request.getRequestDispatcher("role/roleManage.jsp").forward(request, response);		
		
	}
	
	public void assignRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String adminNo = request.getParameter("adminNo");
		int roleId = Integer.parseInt(request.getParameter("roleName"));
		//System.out.println(roleId);
		
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		
		if(roleInfoDao.updateRoleName(adminNo, roleId)){
			request.setAttribute("msg", "角色分配成功！");
			request.getRequestDispatcher("role/adminRole.jsp").forward(request, response);
		}				
	}
			
	public void deleteRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		//System.out.println(roleId);
		
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		
		if(roleInfoDao.deleteRole(id)){
			request.setAttribute("msg", "删除成功！");
			this.queryAllRole(request, response);
		}				
	}
	
	public void addRoleMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int  roleId = Integer.parseInt(request.getParameter("roleId"));		
		String [] menuIds = request.getParameterValues("menuIds");
		//System.out.println(roleId+"-------"+menuIds);
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		roleInfoDao.addLimits(roleId, menuIds);
		
		request.setAttribute("msg", "角色权限分配成功");
		request.getRequestDispatcher("role/RoleEdit.jsp").forward(request, response);

	}
	
	public void updateRole(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int  id = Integer.parseInt(request.getParameter("id"));		
		String roleName = request.getParameter("roleName");
		String des = request.getParameter("des");
		
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		if(roleInfoDao.updateRole(id, roleName, des)){
			request.setAttribute("msg", "修改成功！");
			request.getRequestDispatcher("role/roleREdit.jsp").forward(request, response);
		}

	}
	
}
