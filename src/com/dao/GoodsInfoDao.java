package com.dao;

import java.util.List;

import com.beans.GoodsInfo;
import com.beans.PageInfo;

public interface GoodsInfoDao {

	//添加商品信息
	public boolean addGoods(GoodsInfo goods);	
	//查询商品所有数据条数
	public int queryCount();
	//查询所有商品
	public List<GoodsInfo> queryAllGoods(PageInfo pageInfo);
	//查询商品分类名称
	public String queryCateName(int id);
	//修改商品信息
	public boolean updateGoods(GoodsInfo goodsInfo,int id);
	//根据id查询商品信息
	public GoodsInfo queryGoodsById(int id);
	//删除商品
	public boolean deleteGoods(int id);
	//根据小分类查询商品信息
	public List<GoodsInfo> queryGoodsBySmallCate(PageInfo pageInfo,int smallCateId);
	//根据商品名称查询商品信息
	public List<GoodsInfo> queryGoodsByName(String goodsName);
	//查询商品数据条数
	public int queryGoodsCount(int smallCateId);
	//查询商品信息 ---- 小分类 大分类 商品名称
	public List<GoodsInfo> queryGoods(PageInfo pageInfo,int bigCateId,int smallCateId,String goodsName);
}
