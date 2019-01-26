package com.util;

import com.beans.PageInfo;

public class PageUtil {

	public static final int PAGESIZE=2;
	
	public static PageInfo getPageInfo(int pageSize,int pageIndex,int rowCount){
		PageInfo pi=new PageInfo();
		pi.setPageSize(pageSize);
		pi.setPageIndex(pageIndex);
		pi.setRowCount(rowCount);
		pi.setBeginRow(pageSize*(pageIndex-1));
		pi.setPageCount((rowCount+pageSize-1)/pageSize);
		
		pi.setHasNext(!(pi.getPageCount()-pageIndex<=0));  //false代表没有下一页
		pi.setHasPre(!(pageIndex==1||rowCount==0));  //false代表没有上一页
		return pi;
	} 
}
