package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.beans.CateInfo;
import com.beans.GoodsInfo;
import com.beans.PageInfo;
import com.dao.CateInfoDao;
import com.dao.GoodsInfoDao;
import com.dao.impl.CateInfoDaoImpl;
import com.dao.impl.GoodsInfoDaoImpl;
import com.jspsmart.upload.SmartFile;
import com.jspsmart.upload.SmartRequest;
import com.jspsmart.upload.SmartUpload;
import com.util.PageUtil;


public class GoodsInfoServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String flag = request.getParameter("flag");
		//添加商品
		if("addGoods".equals(flag)){
			this.addGoods(request, response);
		}
		//查询二级分类
		if("querySmallCate".equals(flag)){
			this.querySmallCate(request, response);
		}		
		//查询所有商品
		if("queryAllGoods".equals(flag)){
			this.queryAllGoods(request, response);
		}
		//修改商品
		if("updateGoods".equals(flag)){
			this.updateGoods(request, response);
		}
		//删除商品
		if("deleteGoods".equals(flag)){
			this.deleteGoods(request, response);
		}
		//根据小分类或者商品名称查询
		if("queryGoodsBySmallOrName".equals(flag)){
			this.queryGoodsBySmallOrName(request, response);
		}				
	}

	//添加商品
	public void addGoods(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SmartUpload smart=new SmartUpload();
		PageContext pageContext =JspFactory.getDefaultFactory().getPageContext(this, request, response,
				null, true, 8192, true);
		smart.initialize(pageContext); //这个也可以 smart.initialize(getServletConfig(), request, response);
		try {
			smart.upload();
			//拿到上传的文件
			SmartFile file=smart.getFiles().getFile(0);
			SmartRequest req=smart.getRequest();
			//拿商品信息
			String goodsName = req.getParameter("goodsName");
			int bigCateId = Integer.parseInt(req.getParameter("bigCateId"));
			int smallCateId = Integer.parseInt(req.getParameter("smallCateId"));
			float price = Float.parseFloat(req.getParameter("price"));
			String picture = file.getFieldName();
			String description = req.getParameter("description");
			String unit = req.getParameter("unit");
			String producter = req.getParameter("producter");
			int goodsCount = Integer.parseInt(req.getParameter("goodsCount"));
			
			GoodsInfo goods = new GoodsInfo();
			goods.setGoodsName(goodsName);
			goods.setBigCateId(bigCateId);
			goods.setSmallCateId(smallCateId);
			goods.setPrice(price);
			goods.setPicture(picture);
			goods.setDescription(description);
			goods.setUnit(unit);
			goods.setProducter(producter);
			goods.setGoodsCount(goodsCount);
			
			GoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
			if(goodsInfoDao.addGoods(goods)){
				if(file.getSize()!=0){
					file.saveAs("/images/uploadFiles/"+goods.getPicture()); 
				}
				request.setAttribute("msg", "商品添加成功");
				request.setAttribute("goods", goods);
				request.getRequestDispatcher("/goods/GoodsAdd.jsp").forward(request, response);
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
			request.setAttribute("msg", "商品添加失败");
			request.getRequestDispatcher("/goods/GoodsAdd.jsp").forward(request, response);
		}
				
	}			
	//查询二级分类
	public void querySmallCate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("cateId"));
		//System.out.println(id);
		CateInfoDao cateInfoDao = new CateInfoDaoImpl();
		List<CateInfo> subCateList = cateInfoDao.queryAllCateList(id);
		
		JSONArray jsonArray = JSONArray.fromObject(subCateList);
		//System.out.println(jsonArray);
		response.getWriter().print(jsonArray);
				
	}
	
	//查询所有商品
	public void queryAllGoods(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int pageIndex=Integer.parseInt(request.getParameter("pageIndex")); //获取当前页数
		GoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		int rowCount = goodsInfoDao.queryCount();//总页数
		PageInfo pageInfo = PageUtil.getPageInfo(PageUtil.PAGESIZE, pageIndex, rowCount); //得到分页信息
		List<GoodsInfo> goodsList = goodsInfoDao.queryAllGoods(pageInfo);
		request.setAttribute("goodsList", goodsList);
		request.setAttribute("pageInfo", pageInfo);
		request.getRequestDispatcher("goods/GoodsManage.jsp").forward(request, response);
	}
	
	//修改商品
	public void updateGoods(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("aaa");
		int id = Integer.parseInt(request.getParameter("id"));
		String goodsName = request.getParameter("goodsName");
		//System.out.println(goodsName);		
		int bigCateId = Integer.parseInt(request.getParameter("bigCateId"));
		//System.out.println(bigCateId);
		int smallCateId = Integer.parseInt(request.getParameter("smallCateId"));
		float price = Float.parseFloat(request.getParameter("price"));
		//String picture = request.getParameter("picture");
		String description = request.getParameter("description");
		String unit = request.getParameter("unit");
		String producter = request.getParameter("producter");
		int goodsCount = Integer.parseInt(request.getParameter("goodsCount"));
		
		GoodsInfo goods = new GoodsInfo();
		goods.setGoodsName(goodsName);
		goods.setBigCateId(bigCateId);
		goods.setSmallCateId(smallCateId);
		goods.setPrice(price);
		//goods.setPicture(picture);
		goods.setDescription(description);
		goods.setUnit(unit);
		goods.setProducter(producter);
		goods.setGoodsCount(goodsCount);
		
		//System.out.println(goods);
		GoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		if(goodsInfoDao.updateGoods(goods, id)){
			request.setAttribute("msg", "修改成功");
			request.getRequestDispatcher("goods/GoodsEdit.jsp").forward(request, response);
		}		
	}
	
	//删除
	public void deleteGoods(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int id=Integer.parseInt(request.getParameter("id")); 
		GoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		if(goodsInfoDao.deleteGoods(id)){
			
			response.getWriter().print("删除成功");
		}
	}
	
	//根据小分类或者商品名称查询
	public void queryGoodsBySmallOrName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int smallCateId = Integer.parseInt(request.getParameter("smallCateId"));
		int bigCateId = Integer.parseInt(request.getParameter("bigCateId"));
		
		String goodsName = request.getParameter("goodsName");
		System.out.println(bigCateId+","+smallCateId+","+goodsName);
		int pageIndex=Integer.parseInt(request.getParameter("pageIndex")); //获取当前页数
		GoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		int rowCount = goodsInfoDao.queryGoodsCount(smallCateId);//总页数
		PageInfo pageInfo = PageUtil.getPageInfo(PageUtil.PAGESIZE, pageIndex, rowCount); //得到分页信息
		
		List<GoodsInfo> goodsList = goodsInfoDao.queryGoods(pageInfo, bigCateId, smallCateId, goodsName);
		request.setAttribute("goodsList", goodsList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("smallCateId", smallCateId);
		request.setAttribute("bigCateId", bigCateId);
		request.setAttribute("goodsName", goodsName);
		
		request.getRequestDispatcher("goods/GoodsManage.jsp").forward(request, response);

	}
	
}
