package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.beans.AdminInfo;
import com.beans.CateInfo;
import com.beans.GoodsInfo;
import com.beans.PageInfo;
import com.dao.GoodsInfoDao;
import com.util.DBUtil;

public class GoodsInfoDaoImpl implements GoodsInfoDao{

	@Override
	public boolean addGoods(GoodsInfo goods) {
		Connection conn=null;
		PreparedStatement stm=null;
		try{
			conn=DBUtil.getConn();
			String sql="insert into goodsinfo(goodsName,bigCateId,smallCateId,price,picture,description,unit,producter,goodsCount,editDate) values(?,?,?,?,?,?,?,?,?,?)";
			stm=conn.prepareStatement(sql);
			stm.setString(1,goods.getGoodsName());
			stm.setInt(2,goods.getBigCateId());
			stm.setInt(3, goods.getSmallCateId());
			stm.setFloat(4, goods.getPrice());
			stm.setString(5, goods.getPicture());
			stm.setString(6, goods.getDescription());
			stm.setString(7, goods.getUnit());
			stm.setString(8, goods.getProducter());
			stm.setInt(9, goods.getGoodsCount());
			stm.setString(10, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			int result=stm.executeUpdate();
			if(result>0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}
		return false;
	}	

	@Override
	public int queryCount() {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = DBUtil.getConn();
			String sql = "select count(*) from goodsinfo";
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			rs.next();
			count = rs.getInt(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		
		return count;
	}
	
	@Override
	public List<GoodsInfo> queryAllGoods(PageInfo pageInfo) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from goodsinfo limit ?,?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, pageInfo.getBeginRow());
			stm.setInt(2, pageInfo.getPageSize());
			rs = stm.executeQuery();
			while(rs.next()){
				GoodsInfo goodsInfo=new GoodsInfo();
				goodsInfo.setId(rs.getInt("id"));
				goodsInfo.setGoodsName(rs.getString("goodsName"));				
				goodsInfo.setBigCate(queryCateName(rs.getInt("bigCateId")));
				goodsInfo.setSmallCate(queryCateName(rs.getInt("smallCateId")));
				goodsInfo.setGoodsCount(rs.getInt("goodsCount"));
				goodsInfo.setPrice(rs.getFloat("price"));
				goodsInfo.setPicture(rs.getString("picture"));
				goodsInfo.setDescription(rs.getString("description"));
				goodsInfo.setUnit(rs.getString("unit"));
				goodsInfo.setProducter(rs.getString("producter"));
				goodsList.add(goodsInfo);
							
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		
		return goodsList;
		
	}

	@Override
	public String queryCateName(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		String cateName = null;
		try{
			conn = DBUtil.getConn();
			String sql = "select cateName from cateinfo where id=?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			if(rs.next()){
				cateName=rs.getString("cateName");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return cateName;
	}

	@Override
	public boolean updateGoods(GoodsInfo goodsInfo, int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "update goodsinfo set goodsName=?,bigCateId=?,smallCateId=?,goodsCount=?,price=?,description=?,unit=?,producter=?,editDate=? where id=?";
			stm = conn.prepareStatement(sql);			
			stm.setString(1, goodsInfo.getGoodsName());
			stm.setInt(2, goodsInfo.getBigCateId());
			stm.setInt(3, goodsInfo.getSmallCateId());
			stm.setInt(4, goodsInfo.getGoodsCount());
			stm.setFloat(5,goodsInfo.getPrice());
			//stm.setString(6, goodsInfo.getPicture());
			stm.setString(6, goodsInfo.getDescription());
			stm.setString(7, goodsInfo.getUnit());
			stm.setString(8, goodsInfo.getProducter());
			stm.setString(9, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			stm.setInt(10, id);
			int result = stm.executeUpdate();
			if(result>0){
				return true;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}		
		return false;
	}

	@Override
	public boolean deleteGoods(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		try{
			conn = DBUtil.getConn();
			String sql = "delete from goodsinfo where id=?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			int result = stm.executeUpdate();
			if(result>0){
				return true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, null);
		}
		return false;
	}

	@Override
	public GoodsInfo queryGoodsById(int id) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		GoodsInfo goodsInfo=new GoodsInfo();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from goodsinfo where id=?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			if(rs.next()){
				
				goodsInfo.setId(rs.getInt("id"));
				goodsInfo.setGoodsName(rs.getString("goodsName"));				
				goodsInfo.setBigCateId(rs.getInt("bigCateId"));
				goodsInfo.setSmallCateId(rs.getInt("smallCateId"));
				goodsInfo.setBigCate(queryCateName(rs.getInt("bigCateId")));
				goodsInfo.setSmallCate(queryCateName(rs.getInt("smallCateId")));
				goodsInfo.setGoodsCount(rs.getInt("goodsCount"));
				goodsInfo.setPrice(rs.getFloat("price"));
				goodsInfo.setPicture(rs.getString("picture"));
				goodsInfo.setDescription(rs.getString("description"));
				goodsInfo.setUnit(rs.getString("unit"));
				goodsInfo.setProducter(rs.getString("producter"));
							
			}
			//System.out.println(goodsInfo);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return goodsInfo;
	}

	@Override
	public List<GoodsInfo> queryGoodsBySmallCate(PageInfo pageInfo,
			int smallCateId) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from goodsinfo where smallCateId=? limit ?,?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, smallCateId);
			stm.setInt(2, pageInfo.getBeginRow());
			stm.setInt(3, pageInfo.getPageSize());
			rs = stm.executeQuery();
			while(rs.next()){
				GoodsInfo goodsInfo=new GoodsInfo();
				goodsInfo.setId(rs.getInt("id"));
				goodsInfo.setGoodsName(rs.getString("goodsName"));				
				goodsInfo.setBigCate(queryCateName(rs.getInt("bigCateId")));
				goodsInfo.setSmallCate(queryCateName(rs.getInt("smallCateId")));
				goodsInfo.setGoodsCount(rs.getInt("goodsCount"));
				goodsInfo.setPrice(rs.getFloat("price"));
				goodsInfo.setPicture(rs.getString("picture"));
				goodsInfo.setDescription(rs.getString("description"));
				goodsInfo.setUnit(rs.getString("unit"));
				goodsInfo.setProducter(rs.getString("producter"));
				goodsList.add(goodsInfo);
							
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		
		return goodsList;
	}

	@Override
	public List<GoodsInfo> queryGoodsByName(String goodsName) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from goodsinfo where goodsName=?";
			stm = conn.prepareStatement(sql);
			stm.setString(1, goodsName);
			rs = stm.executeQuery();
			if(rs.next()){			
				GoodsInfo goodsInfo=new GoodsInfo();
				goodsInfo.setId(rs.getInt("id"));
				goodsInfo.setGoodsName(rs.getString("goodsName"));				
				goodsInfo.setBigCateId(rs.getInt("bigCateId"));
				goodsInfo.setSmallCateId(rs.getInt("smallCateId"));
				goodsInfo.setBigCate(queryCateName(rs.getInt("bigCateId")));
				goodsInfo.setSmallCate(queryCateName(rs.getInt("smallCateId")));
				goodsInfo.setGoodsCount(rs.getInt("goodsCount"));
				goodsInfo.setPrice(rs.getFloat("price"));
				goodsInfo.setPicture(rs.getString("picture"));
				goodsInfo.setDescription(rs.getString("description"));
				goodsInfo.setUnit(rs.getString("unit"));
				goodsInfo.setProducter(rs.getString("producter"));
				goodsList.add(goodsInfo);			
			}
			//System.out.println(goodsInfo);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}		
		return goodsList;
	}

	@Override
	public int queryGoodsCount(int smallCateId) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = DBUtil.getConn();
			String sql = "select count(*) from goodsinfo where smallCateId=?";
			stm = conn.prepareStatement(sql);
			stm.setInt(1, smallCateId);
			rs = stm.executeQuery();
			rs.next();
			count = rs.getInt(1);		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		return count;
	}

	@Override
	public List<GoodsInfo> queryGoods(PageInfo pageInfo, int bigCateId,
			int smallCateId, String goodsName) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
		try{
			conn = DBUtil.getConn();
			String sql = "select * from goodsinfo where 1=1";
			if(bigCateId!=-1){
				sql+= " and bigCateId="+bigCateId;
			}
			if(smallCateId!=-1){
				sql+= " and smallCateId="+smallCateId;
			}
			if(!"".equals(goodsName)){
				sql+= " and goodsName like '%"+goodsName+"%'";
			}
			sql += " limit ?,?";
			System.out.println(sql);
			stm = conn.prepareStatement(sql);
			stm.setInt(1, pageInfo.getBeginRow());
			stm.setInt(2, pageInfo.getPageSize());
			rs = stm.executeQuery();
			while(rs.next()){
				GoodsInfo goodsInfo=new GoodsInfo();
				goodsInfo.setId(rs.getInt("id"));
				goodsInfo.setGoodsName(rs.getString("goodsName"));				
				goodsInfo.setBigCate(queryCateName(rs.getInt("bigCateId")));
				goodsInfo.setSmallCate(queryCateName(rs.getInt("smallCateId")));
				goodsInfo.setGoodsCount(rs.getInt("goodsCount"));
				goodsInfo.setPrice(rs.getFloat("price"));
				goodsInfo.setPicture(rs.getString("picture"));
				goodsInfo.setDescription(rs.getString("description"));
				goodsInfo.setUnit(rs.getString("unit"));
				goodsInfo.setProducter(rs.getString("producter"));
				goodsList.add(goodsInfo);							
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, stm, rs);
		}
		
		return goodsList;
	}

}
