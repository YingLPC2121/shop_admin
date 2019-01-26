package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.AdminInfo;
import com.beans.PageInfo;
import com.dao.AdminInfoDao;
import com.dao.impl.AdminInfoDaoImpl;
import com.util.PageUtil;


public class AdminInfoServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	
		String flag = request.getParameter("flag");
		//登录
		if("login".equals(flag)){
			this.login(request, response);
		}
		//查询所有管理员用户
		if("queryAllAdmin".equals(flag)){
			this.queryAllAdmin(request, response);
		}		
		//修改状态isLock
		if("onLock".equals(flag)){
			this.onLock(request, response);
		}
		//修改状态isLock
		if("unLock".equals(flag)){
			this.unLock(request, response);
		}
		//添加管理员
		if("addAdmin".equals(flag)){
			this.addAdmin(request, response);
		}
		//修改管理员信息
		if("updateAdmin".equals(flag)){
			this.updateAdmin(request, response);
		}
		//删除管理员
		if("deleteAdmin".equals(flag)){
			this.deleteAdmin(request, response);
		}
		
		
	}

	//登录方法
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String adminNo = request.getParameter("adminNo");
		String password = request.getParameter("password");	
		HttpSession session = (HttpSession)request.getSession();
		//调用dao
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		AdminInfo adminInfo = adminInfoDao.queryAll(adminNo);
		//判断用户名是否存在
		if(adminInfo!=null){
			if(adminInfo.getIsLock()==3){
				session.setAttribute("msg", "用户已被锁定，请联系管理员");
				request.getRequestDispatcher("login.jsp").forward(request, response);	
				return;
			}
			if(password.equals(adminInfo.getPassword())){
				//将isLock==0
				adminInfoDao.updateIsLock(0,adminNo);
				session.setAttribute("adminInfo", adminInfo);			
				request.getRequestDispatcher("main.html").forward(request, response);				
			}else{
				//将isLock+1
				adminInfoDao.updateIsLock(adminInfo.getIsLock()+1,adminNo);
				session.setAttribute("msg", "密码错误，您还有"+(3-adminInfo.getIsLock()-1)+"次机会");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				
			}
		}else{
			request.setAttribute("msg", "用户名不存在");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	//查询所有管理员用户
	public void queryAllAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageIndex=Integer.parseInt(request.getParameter("pageIndex")); //获取当前页数
		
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		
		int rowCount = adminInfoDao.queryCount();//总页数
		PageInfo pageInfo = PageUtil.getPageInfo(PageUtil.PAGESIZE, pageIndex, rowCount); //得到分页信息		
		List<AdminInfo> adminList = adminInfoDao.queryAllAdmin(pageInfo);
		request.setAttribute("adminList", adminList);
		request.setAttribute("pageInfo", pageInfo);
		request.getRequestDispatcher("/admin/AdminManage.jsp").forward(request, response);		
		
	}
	
	//上锁
	public void onLock(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String adminNo = (String)request.getParameter("adminNo");
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		if(adminInfoDao.updateIsLock(adminNo)){
			this.queryAllAdmin(request, response);
		}
		
	}
	
	//解锁
	public void unLock(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String adminNo = (String)request.getParameter("adminNo");
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		if(adminInfoDao.updateIsLock(0, adminNo)){
			this.queryAllAdmin(request, response);
		}		
	}
	
	//添加管理员
	public void addAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String adminNo = (String)request.getParameter("adminNo");
		String adminName = (String)request.getParameter("adminName");
		String password = (String)request.getParameter("password");
		//String pwdConfirm = (String)request.getParameter("pwdConfirm");
		String description = (String)request.getParameter("description");
		
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		AdminInfo adminInfo = adminInfoDao.queryAll(adminNo);
		if(adminInfo==null){
			if(adminInfoDao.addAdmin(adminNo, adminName, password, description, 0)){
				//this.queryAllAdmin(request, response);
				//request.setAttribute("msg", "添加成功");
				//request.getRequestDispatcher("/admin/AdminAdd.jsp").forward(request, response);
				response.getWriter().print("添加成功");
				return;
			}else{
				//request.setAttribute("msg", "添加失败");
				//request.getRequestDispatcher("/admin/AdminAdd.jsp").forward(request, response);
				response.getWriter().print("添加失败");
				return;
			}
		}else{
			//request.setAttribute("msg", "用户名已存在");
			//request.getRequestDispatcher("/admin/AdminAdd.jsp").forward(request, response);
			response.getWriter().print("用户名已存在");
		}				
	}
	

	//修改管理员信息
	public void updateAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 拿到传到的信息		
		String adminNo = request.getParameter("adminNo");
		String adminName = request.getParameter("adminName");
		String description = request.getParameter("description");

		// 连接dao
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		if(adminInfoDao.updateAdmin(adminNo, adminName, description)){
			request.setAttribute("msg", "修改成功");
			this.queryAllAdmin(request, response);
		}	
	}
	
	//删除管理员
	public void deleteAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 拿到传到的信息		
		String adminNo = request.getParameter("adminNo");
		AdminInfoDao adminInfoDao = new AdminInfoDaoImpl();
		HttpSession session = (HttpSession)request.getSession();
		AdminInfo adminInfo = (AdminInfo)session.getAttribute("adminInfo");
		if(adminNo.equals(adminInfo.getAdminNo())){
			request.setAttribute("msg", "不能删除自己");
		}else{
			adminInfoDao.deleteAdmin(adminNo);
			request.setAttribute("msg", "删除成功");
			this.queryAllAdmin(request, response);
		}
		
	}
	
	
}
