package com.beans;

public class GoodsInfo {

	private int id;
	private String goodsName;
	private int bigCateId;		//大分类
	private int smallCateId;	//小分类
	private int goodsCount;
	private float price;      
	private String picture;		//商品图片
	private String description; //商品描述
	private String unit; 		//计量单位
	private String producter;	//生产商
	private String editDate;	//时间
	private String bigCate;
	private String smallCate;
	
	
	@Override
	public String toString() {
		return "GoodsInfo [id=" + id + ", goodsName=" + goodsName
				+ ", bigCateId=" + bigCateId + ", smallCateId=" + smallCateId
				+ ", goodsCount=" + goodsCount + ", price=" + price
				+ ", picture=" + picture + ", description=" + description
				+ ", unit=" + unit + ", producter=" + producter + ", editDate="
				+ editDate + ", bigCate=" + bigCate + ", smallCate="
				+ smallCate + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getBigCateId() {
		return bigCateId;
	}
	public void setBigCateId(int bigCateId) {
		this.bigCateId = bigCateId;
	}
	public int getSmallCateId() {
		return smallCateId;
	}
	public void setSmallCateId(int smallCateId) {
		this.smallCateId = smallCateId;
	}
	public int getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getProducter() {
		return producter;
	}
	public void setProducter(String producter) {
		this.producter = producter;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public String getBigCate() {
		return bigCate;
	}
	public void setBigCate(String bigCate) {
		this.bigCate = bigCate;
	}
	public String getSmallCate() {
		return smallCate;
	}
	public void setSmallCate(String smallCate) {
		this.smallCate = smallCate;
	}
	
	
}
