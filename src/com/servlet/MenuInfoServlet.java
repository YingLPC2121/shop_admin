package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.MenuInfo;
import com.dao.MenuInfoDao;
import com.dao.RoleInfoDao;
import com.dao.impl.MenuInfoDaoImpl;
import com.dao.impl.RoleInfoDaoImpl;

public class MenuInfoServlet extends HttpServlet{

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String flag = request.getParameter("flag");
		
		//添加一级菜单
		if("addBigMenu".equals(flag)){
			this.addBigMenu(request, response);
		}
		//添加二级菜单
		if("addSmallMenu".equals(flag)){
			this.addSmallMenu(request, response);
		}
		//查询所有菜单
		if("queryAllMenuList".equals(flag)){
			this.queryAllMenuList(request, response);
		}
		//删除菜单
		if("deleteMenu".equals(flag)){
			this.deleteMenu(request, response);
		}
		//修改菜单
		if("updateMenu".equals(flag)){
			this.updateMenu(request, response);
		}
		
	}

	
	public void addBigMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String menuName = request.getParameter("menuName");
		
		MenuInfoDao menuInfoDao  = new MenuInfoDaoImpl();
		
		if(menuInfoDao.addBigMenu(menuName, 0)){			
			request.setAttribute("msg","一级菜单添加成功！" );
			request.getRequestDispatcher("menu/BigMenuAdd.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "一级菜单添加失败！");
			request.getRequestDispatcher("menu/BigMenuAdd.jsp").forward(request, response);
		}
	}
	
	public void addSmallMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String menuName = request.getParameter("menuName");
		String target = request.getParameter("target");
		String url = request.getParameter("url");
		String icon = request.getParameter("icon");
		int parentId = Integer.parseInt(request.getParameter("parentId")); //小菜单的父亲id ,大菜单的id
		
		MenuInfo menuInfo  = new MenuInfo();
		menuInfo.setMenuName(menuName);
		menuInfo.setTarget(target);
		menuInfo.setUrl(url);
		menuInfo.setIcon(icon);
		menuInfo.setParentId(parentId);
						
		RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
		String idStr = roleInfoDao.queryAllRoleId();
		if(!"".equals(idStr)){
			idStr =  idStr.substring(0, idStr.length()-1);
		}
		String [] roleIdStr = idStr.split(",");
		String switchs = request.getParameter("switch");		
		System.out.println(switchs+"-----"+idStr);
		
		MenuInfoDao menuInfoDao  = new MenuInfoDaoImpl();
		
		if(menuInfoDao.addSmallMenu(menuInfo)){
			if("on".equals(switchs)){
				int menuId = menuInfoDao.queryMenuId(menuName);
				menuInfoDao.deleteRoleMenu(parentId);				
				menuInfoDao.giveLimits(parentId, roleIdStr);
				menuInfoDao.giveLimits(menuId, roleIdStr);				
			}
			request.setAttribute("msg","二级菜单添加成功！" );
			request.getRequestDispatcher("menu/SmallMenuAdd.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "二级菜单添加失败！");
			request.getRequestDispatcher("menu/SmallMenuAdd.jsp").forward(request, response);
		}
	}
	
	public void queryAllMenuList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		MenuInfoDao menuInfoDao  = new MenuInfoDaoImpl();
		List<MenuInfo> menuList = menuInfoDao.queryAllMenuList(0);
		
		request.setAttribute("menuList", menuList);
		request.getRequestDispatcher("menu/MenuManage.jsp").forward(request, response);
	}
	
	//删除菜单
	public void deleteMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));		
		String delflag = request.getParameter("delflag");
		
		//System.out.println(id);
		MenuInfoDao menuInfoDao = new MenuInfoDaoImpl();
		if("del1".equals(delflag)){
			int pid = Integer.parseInt(request.getParameter("pid")); //0
			List<MenuInfo> menuList = menuInfoDao.queryAllMenuList(pid);
			for(MenuInfo m:menuList){
				int ids = m.getId();  
				if(ids==id){  //26==26
					if(m.getSubMenuList().isEmpty()){  //空
						response.getWriter().print("1");
					}else{								//不空
						response.getWriter().print("2");
					}
				}
			}		
		}else if("del3".equals(delflag)){
			List<MenuInfo> menuList = menuInfoDao.queryAllMenuList(id); 
			for(MenuInfo m:menuList){
				menuInfoDao.deleteRoleMenu(m.getId()); //27
			}
			menuInfoDao.deleteRoleMenu(id);  //26
			menuInfoDao.deleteAllMenu(id);  //26 --- id/parentId			
			response.getWriter().print("删除成功");
			
		}else{
			if(menuInfoDao.deleteMenu(id)){   //27  26    
				menuInfoDao.deleteRoleMenu(id);  //27  26
				response.getWriter().print("删除成功");
			}
		}							
	}
	
	public void updateMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String menuName = request.getParameter("menuName");
		int menuId = Integer.parseInt(request.getParameter("id")); //一级菜单的id
		String switchs = request.getParameter("switch");
		
		String roleIdStr = request.getParameter("roleIdStr");  //要修改的菜单对应的roleId
		String roleIdAll = request.getParameter("roleIds");
		System.out.println(roleIdStr);
		System.out.println(roleIdAll);
		
		MenuInfoDao menuInfoDao  = new MenuInfoDaoImpl();
		if(menuInfoDao.updateMenu(menuName, menuId)){
			if("on".equals(switchs)){
				if(roleIdStr.equals(roleIdAll)){
					request.setAttribute("msg", "一级菜单修改成功！");
				}else{
					String roleIdLast = roleIdAll.substring(roleIdStr.length()+1, roleIdAll.length());
					String [] roleIdArray = roleIdLast.split(",");
					System.out.println(roleIdArray);
					menuInfoDao.giveLimits(menuId, roleIdArray);
					response.getWriter().print("一级菜单修改成功！");			
				}
			}else{
				if(roleIdStr.equals(roleIdAll)){
					//通过一级菜单找二级 id
					List<MenuInfo> menuList = menuInfoDao.queryAllMenuList(menuId);
					for(MenuInfo m:menuList){
						System.out.println("++++"+m.getId());
						menuInfoDao.deleteRoleMenu(m.getId());
					}
					menuInfoDao.deleteRoleMenu(menuId);
					response.getWriter().print("一级菜单修改成功！");		
				}else{
					response.getWriter().print("一级菜单修改成功！");		
				}
			}			
			
		}	
	}
	
}
