<%@ page language="java" import="java.util.*,com.beans.*,com.dao.*,com.dao.impl.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>商品管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript" src="js/jquery.js"> </script>
 <script type="text/javascript" src="js/jquery-1.8.0.js"> </script>
    <script type="text/javascript">
    	$(function(){
					$("#bigCateId").change(function(){ 
						if($("#bigCateId").val()=="-1"){
							$("#smallCateId").empty(); 
							$("#smallCateId").append("<option value='-1'>---小分类--- </option>"); 
							return ;
						}	
						$.ajax({							
							url:"querySmallCate.do",
							type:"post",
							dataType:"json",
							data:{
									cateId:$("#bigCateId").val(),
									flag:"querySmallCate"									
								},			
							cache:false,
							async:true, 
							success:function(data){
								$("#smallCateId").empty(); 
								$.each(data,function(key,cList){
									$("#smallCateId").append("<option value='"+cList.id+"'>"+cList.cateName+"</option>"); 
								});
							}
						});
					});
			});
		</script>
		

<script type="text/javascript">
	function checkAllGoods()
	{
		var checkList=document.getElementsByName("checkGoods");
		for(var i=0;i<checkList.length;i++)
		{
			checkList[i].checked=document.getElementById("checkAll").checked;
		}
	} 

	function delGoodsList()
	{
		var checkList=document.getElementsByName("checkGoods");
		var isSelected=false;
		for(var i=0;i<checkList.length;i++)
		{		
			if(checkList[i].checked==true)
			{
				isSelected=true;
				break;
			}			
		}
		
		if(isSelected==true)
		{
			if(confirm("确定要删除选定项吗?"))
			{
				document.form1.action="GoodsAction?method=delGoodsList";
				document.form1.submit();
			}
		}
		else
		{
			alert("您还没有选择任何项！");
			return false;
		}
	}
	
	function upIndex() {
		window.location.href="queryGoodsBySmallOrName.do?flag=queryGoodsBySmallOrName&bigCateId="+${cate.id}+"&smallCateId="+${subCate.id}+"&goodsName="+${goodsName}+"&pageIndex="+${pageInfo.pageIndex-1 };
	}
	
	function nextIndex() {
		window.location.href="queryGoodsBySmallOrName.do?flag=queryGoodsBySmallOrName&bigCateId="+${cate.id}+"&smallCateId="+${subCate.id}+"&goodsName="+${goodsName}+"&pageIndex="+${pageInfo.pageIndex+1 };
	}
	
	function lastIndex() {
		window.location.href="queryGoodsBySmallOrName.do?flag=queryGoodsBySmallOrName&bigCateId="+${cate.id}+"&smallCateId="+${subCate.id}+"&goodsName="+${goodsName}+"&pageIndex="+${pageInfo.pageCount };
	}
	
	function changePageIndex() {
		var sel = document.getElementById("pageIndex");
		var text = sel.options[sel.selectedIndex].text;
		window.location.href="queryGoodsBySmallOrName.do?flag=queryGoodsBySmallOrName&bigCateId="+${cate.id}+"&smallCateId="+${subCate.id}+"&goodsName="+${goodsName}+"&pageIndex="+text;
	}

</script>
<script type="text/javascript">
	/* $(function(){
		$("#sub").click(function(){
			
			 $.ajax({
				url:"queryGoodsBySmallOrName.do",
				type:"post",
				dataType:"json",
				data:{
					smallCateId:$("#smallCateId").val(),
					flag:"queryGoodsBySmallOrName",
					pageIndex:"1"
				},
				success:function(data){
					
				}
				
			}); 
		
		});
	}); */
</script>
  <link rel="stylesheet" href="css/style2.css" type="text/css"></link>
</head>
  
<body class="p10">
<%
	CateInfoDao cateInfoDao = new CateInfoDaoImpl();
	List<CateInfo> cateList = cateInfoDao.queryAllCateList(0);
	request.setAttribute("cateList", cateList);
	
	Integer bigCateId = (Integer)request.getAttribute("bigCateId");
	if(bigCateId!=null){
		List<CateInfo> subCateList = cateInfoDao.queryAllCateList(bigCateId);
		request.setAttribute("subCateList", subCateList);
	}
	
	
 %>
	<form action="queryGoodsBySmallOrName.do?flag=queryGoodsBySmallOrName&pageIndex=1" method="post" name="form1">
 		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			 <tr>
				 <td height="30" bgcolor="d3eaef" > &nbsp;&nbsp; &nbsp;			  	
				 	<select name="bigCateId" id="bigCateId" style="margin-left: 15px">	
					<option value="-1">---大分类---</option>				
					<c:forEach var="cate" items="${cateList }">										
						<option value='${cate.id }'
							<c:if test="${cate.id==bigCateId }">
								selected="selected"
							</c:if>
						>${cate.cateName }</option>					
					</c:forEach>				
				</select><label id="bigCateId_msg"></label>
				<select name="smallCateId" id="smallCateId">
					<option value="-1">---小分类---</option>
					<c:forEach var="subCate" items="${subCateList }">
						<option value='${subCate.id }'
							<c:if test="${subCate.id==smallCateId }">
								selected="selected"
							</c:if>
						>${subCate.cateName }</option>	
					</c:forEach>					
				</select><label id="smallCateId_msg"></label>&nbsp; &nbsp;
			          商品名称:<input type="text" name="goodsName" value="${goodsName }"/>&nbsp; &nbsp;<input type="submit" id="sub" value="查询" /></td>
 			</tr>
 		</table>
 		<div style="margin-top: 5px"></div>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  			<tr>
    			<td height="30">
    				<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td class="title1" height="24">
									<div class="f_right">
										<span class="White"> <input name="checkAll" id="checkAll" onclick="checkAllGoods();" type="checkbox">全选 &nbsp;&nbsp;
										<img src="images/add.gif" height="10" width="10"> 
										<a style="color: #fff; margin-left: 3px;" href="GoodsAdd.jsp">添加</a>&nbsp;
										<img src="images/del.gif" height="10" width="10">
										<input value="刪除" onclick="delGoodsList();" style="background: none; color: #fff; border: none;" type="button"> &nbsp;&nbsp; &nbsp;
										</span>
										<span class="STYLE1"> &nbsp;</span>
									</div>
									<img src="images/tb.gif" height="14" width="14" class="ico">商品基本信息列表
								</td>
							</tr>
						</tbody>
					</table>
					<div style="margin-top: 8px"></div>    
    			</td>
  			</tr>
  			<tr>
    			<td>
    				<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
					    <tr>
					        <td width="4%" height="20" bgcolor="d3eaef" class="STYLE10"><div align="center"> 选择</div></td>
					        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">商品名称</span></div></td>
					        <td  height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">当前数量</span></div></td>
					        <td height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">所属分类</span></div></td>
					        <td  height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">单位</span></div></td>
					        <td  height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">单价</span></div></td>
					        <td  height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">商品描述</span></div></td>
					        <td  height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">基本操作</span></div></td>
					    </tr>
					    <c:forEach var="goods" items="${goodsList }">
					    	<tr>
						        <td height="20" bgcolor="#FFFFFF"><div align="center">
						        	<input type="checkbox" name="checkGoods" value="" />
						        	<input type="hidden" name="id" value="${goods.id }" />						
						        </div></td>						 
						        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="left"><span class="STYLE19">${goods.goodsName }</span></div></td>
						        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="left"><span class="STYLE19">${goods.goodsCount }</span></div></td>
						        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="left">${goods.bigCate }-${goods.smallCate }</div></td>
						        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="left">${goods.unit }</div></td>
						        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="left">${goods.price }</div></td>
						        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="left">${goods.description }</div></td>
								<td height="20" bgcolor="#FFFFFF">
									<div align="center" class="STYLE21">
										<a href="goods/GoodsEdit.jsp?id=${goods.id }&pageIndex=${pageInfo.pageIndex }">修改</a> | 
										<a href="deleteGoods.do?flag=deleteGoods&id=${goods.id }"  onclick="return confirm('确定删除吗?')">删除</a>
									</div>
								</td>
							</tr>          
					    </c:forEach>           
				       	                           	                
    				</table>
    			</td>
  			</tr>
	    <tr>
    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="33%"><div align="left"><span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong> ${pageInfo.rowCount }</strong> 条记录，当前第<strong> ${pageInfo.pageIndex }</strong> 页，共 <strong>${pageInfo.pageCount }</strong> 页</span></div></td>
        <td width="67%"><table width="312" border="0" align="right" cellpadding="0" cellspacing="0">
          <tr>
            <td width="49"><div align="center"><img src="images/main_54.gif" width="40" height="15" onclick="firstIndex();"/></div></td><!--首页 -->
            <td width="49"><div align="center">
            	<c:choose>
			    	<c:when test="${pageInfo.hasPre }">
			    		<img src="images/main_56.gif" width="45" height="15" onclick="upIndex();" />
			    	</c:when>
			    	<c:otherwise>
			    		<img src="images/main_56.gif" width="45" height="15"/>
			    	</c:otherwise>	    	
		   		</c:choose>          
            </div></td><!--前一页 -->      
            <td width="49"><div align="center">
            	<c:choose>
			    	<c:when test="${pageInfo.hasNext }">
			    		<img src="images/main_58.gif" width="45" height="15" onclick="nextIndex();"/>
			    	</c:when>
			    	<c:otherwise>
			    		<img src="images/main_58.gif" width="45" height="15"/>
			    	</c:otherwise>	    	
		   		</c:choose>                  	            	
            	</div></td><!--后一页 -->
            <td width="49"><div align="center"><img src="images/main_60.gif" width="40" height="15" onclick="lastIndex();"/></div></td><!--尾页 -->
           
           
            <td width="37" class="STYLE22"><div align="center">转到</div></td>
            <td width="22"><div align="center">
            	<select id="pageIndex" name="pageIndex" onChange="changePageIndex()" style="width:32px; height:18px; font-size:12px; border:solid 1px #7aaebd;">				
					<c:forEach begin="1" end="${pageInfo.pageCount }" var="i">
						<c:if test="${i==pageInfo.pageIndex }">
							<option value="" selected="selected"> ${i }</option>
						</c:if>
						<c:if test="${i!=pageInfo.pageIndex }">
							<option value="" > ${i }</option>
						</c:if>
					</c:forEach>									
				</select>        
            	</div></td>
            <td width="22" class="STYLE22"><div align="center">页</div></td>
         <!-- <td width="35"><a href="Action?method=Manage"><img src="images/main_62.gif" width="26" height="15" /></a></td> -->   
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</body>
</html>
