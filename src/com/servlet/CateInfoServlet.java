package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.beans.CateInfo;
import com.beans.GoodsInfo;
import com.dao.CateInfoDao;
import com.dao.GoodsInfoDao;
import com.dao.impl.CateInfoDaoImpl;
import com.dao.impl.GoodsInfoDaoImpl;
import com.jspsmart.upload.SmartFile;
import com.jspsmart.upload.SmartRequest;
import com.jspsmart.upload.SmartUpload;

public class CateInfoServlet extends HttpServlet{

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String flag = request.getParameter("flag");
		//添加一级分类
		if("addBigCate".equals(flag)){
			this.addBigCate(request, response);
		}			
		//添加二级分类
		if("addSmallCate".equals(flag)){
			this.addSmallCate(request, response);
		}		
		//查询所有分类 ----分类管理
		if("queryAllCateList".equals(flag)){
			this.queryAllCateList(request, response);
		}	
		//删除分类
		if("deleteCate".equals(flag)){
			this.deleteCate(request, response);
		}		
		//修改分类
		if("updateCate".equals(flag)){
			this.updateCate(request, response);
		}	
		
	}

	//添加一级分类
	public void addBigCate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String cateName = request.getParameter("cateName");
		String cateDes = request.getParameter("cateDes");
		
		CateInfoDao cateInfoDao = new CateInfoDaoImpl();
		if(cateInfoDao.addBcAndSc(cateName, cateDes, 0)){
			request.setAttribute("msg", "一级分类添加成功");
			request.getRequestDispatcher("/goods/BigCateAdd.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "一级分类添加失败");
			request.getRequestDispatcher("/goods/BigCateAdd.jsp").forward(request, response);
		}
		
	}
	
	//查询所有分类 ----分类管理
	public void queryAllCateList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CateInfoDao cateInfoDao = new CateInfoDaoImpl();
		List<CateInfo> cateList = cateInfoDao.queryAllCateList(0);
		/*HttpSession session = (HttpSession)request.getSession();
		session.setAttribute("cateList", cateList);	*/
		request.setAttribute("cateList", cateList);
		request.getRequestDispatcher("/goods/CateManage.jsp").forward(request, response);
		
	}
	//添加二级分类
	public void addSmallCate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String cateName = request.getParameter("cateName");
		String cateDes = request.getParameter("cateDes");
		int parentId = Integer.parseInt(request.getParameter("parentId"));
				
		//System.out.println(parentId);
		
		CateInfoDao cateInfoDao = new CateInfoDaoImpl();
		if(cateInfoDao.addBcAndSc(cateName, cateDes, parentId)){
			request.setAttribute("msg", "二级分类添加成功");
			request.getRequestDispatcher("/goods/SmallCateAdd.jsp").forward(request, response);
			
		}						
	}
	
	//删除分类
	public void deleteCate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));		
		String delflag = request.getParameter("delflag");
		
		//System.out.println(id);
		
		CateInfoDao cateInfoDao = new CateInfoDaoImpl();
		if("del1".equals(delflag)){
			int pid = Integer.parseInt(request.getParameter("pid"));
			List<CateInfo> cateList = cateInfoDao.queryAllCateList(pid);
			for(CateInfo c:cateList){
				int ids = c.getId();
				if(ids==id){
					if(c.getSubCateList().isEmpty()){
						response.getWriter().print("1");
					}else{
						response.getWriter().print("2");
					}
				}
			}		
		}else if("del3".equals(delflag)){
			cateInfoDao.deleteAllCate(id);
			
		}else{
			if(cateInfoDao.deleteCate(id)){
				response.getWriter().print("删除成功");
			}
		}							
	}
	
	//修改分类
	public void updateCate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));	
		String cateName = request.getParameter("cateName");
		
		CateInfoDao cateInfoDao = new CateInfoDaoImpl();
		
		if(cateInfoDao.uodateCate(id, cateName)){
			/*request.setAttribute("msg", "修改成功!");
			request.getRequestDispatcher("goods/BigCateEdit.jsp").forward(request, response);*/
			response.getWriter().print("修改成功");			
		}
		
	}
	
}









