<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>商品分类管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" href="css/style2.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script type="text/javascript">
	function deleteById(id){
		if(confirm("确定删除吗")){
			$.ajax({
				url:"deleteCate.do",
				type:"post",
				data:{
					flag:"deleteCate",
					delflag:"del2",
					id:id
				},
				success:function(data){
					$("#trs"+id).remove();
					$("#msg").html(data);
				}		
			});
		}		
	}
	
	function deleteByPid(pid,id){
		if(confirm("确定删除吗")){
			$.ajax({
				url:"deleteCate.do",
				type:"post",
				data:{
					flag:"deleteCate",
					delflag:"del1",
					pid:pid,
					id:id
				},
				success:function(data){
					if(data=="1"){
						$("#msg").html("删除成功");
						deleteById(id);
					}
					if(data=="2"){
						/* $("#msg").html("一级分类不为空，不能删除！"); */
						if(confirm("一级分类不为空,确定删除吗？")){
							deleteAllById(id);
						}
					}					
				}		
			});
		}		
	}
	
	function deleteAllById(id){
		if(confirm("确定删除吗")){
			$.ajax({
				url:"deleteCate.do",
				type:"post",
				data:{
					flag:"deleteCate",
					delflag:"del3",
					id:id
				},
				success:function(data){
					
					$("#trs"+id).remove();					
					$("#msg").html(data);
				}		
			});
		}
		
	}

</script>
</head>
  
<body class="p10">    		
   <form action="/shop-admin/RoleServlet?method=update" method="post" >
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="30">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td class="title1" height="24">
										<div class="f_right">
											<span class="White"> 
											<img src="images/add.gif" height="10" width="10"> &nbsp; &nbsp;&nbsp; &nbsp;</span>
											<span class="STYLE1"> &nbsp;</span>
										</div>
										<img src="images/tb.gif" height="14" width="14" class="ico">商品分类管理
								</td>
							</tr>
						</tbody>
					</table>
					<div style="margin-top: 8px"></div>
				</td>
			</tr>
			<tr>
    			<td>
			 		<table class="table1" width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr  class="tr" > 
							<td width="30%"><div align="center" >一级分类</div></td>
							<td><div align="center">二级分类</div></td>
						</tr>
						<c:forEach var="cate" items="${cateList}">
							<tr id="trs${cate.id }"> 
								<td style="padding-left: 30px" >
									<table>
										<tr id="tr${cate.id }" >
											<td width="170px">${cate.cateName }</td>
							 				<td>
							 					<a href="goods/BigCateEdit.jsp?cateName=${cate.cateName }&id=${cate.id}">修改</a>&nbsp;|&nbsp;
							 					<a href="javascript:void(0);" onclick="deleteByPid(${cate.parentId},${cate.id });">删除</a>
							 				</td>
						 	  			</tr>
							 		</table>
					    		</td>
								<td  style="padding-left: 30px">
									<table>	
										<c:forEach var="subCate" items="${cate.subCateList }">
											<tr id="tr${subCate.id }" >
												<td width="170px">${subCate.cateName }</td>
												<td>
													<a href="goods/SmallCateEdit.jsp?cateName=${subCate.cateName }&id=${subCate.id}">修改</a>&nbsp;|&nbsp;
													<a href="javascript:void(0);" onclick="deleteById(${subCate.id});">删除</a>
												</td>
											</tr>	
										</c:forEach>																																																																										
									</table>
								</td>
							</tr>	
						</c:forEach>												    							    
				</table>
			</td>		
  		</tr>
	</table>
	<br/>&nbsp;&nbsp;<label style="color:red"></label>
  </form> 
  <label id="msg">${msg }</label>     
</body>
</html>