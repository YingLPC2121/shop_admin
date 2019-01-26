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
    
    <title>新建商品信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="js/jquery.js"></script> 
<script type="text/javascript" src="js/num.util.js"></script> 
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
							/* cache:false,
							async:true, */
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
	function validateInput(){
		var result=true;
		document.getElementById("goodsName_msg").innerHTML="*";
		document.getElementById("unit_msg").innerHTML=""; 
		document.getElementById("price_msg").innerHTML=""; 
		document.getElementById("goodsCount_msg").innerHTML="";
		document.getElementById("bigCateId_msg").innerHTML="";
		document.getElementById("smallCateId_msg").innerHTML="";
		
		document.getElementById("lblMsg").innerHTML="";

		
		
		if(form1.goodsName.value==""){
			document.getElementById("goodsName_msg").innerHTML="商品名称不能为空!";
			result=false;		
		}
		
		if(form1.unit.value==""){
			document.getElementById("unit_msg").innerHTML="商品单位不能为空!";
			result=false;		
		}
		
		if(form1.goodsCount.value!=""){ // 商品数量可以不添,要添必须添正确
			if(IsNoEmptyInteger(form1.goodsCount.value)==false){
				document.getElementById("goodsCount_msg").innerHTML="请输入正确的数字!";
				result=false;		
			}
		}
		

		if(IsNoEmptyFloat(form1.price.value)==false){ //价格
			document.getElementById("price_msg").innerHTML="请输入正确的价格!";
			result=false;		
		}
		
		if(document.getElementById("bigCateId").selectedIndex==0){
			document.getElementById("bigCateId_msg").innerHTML="请选择所属大分类!";
			result=false;	
		}
		
	    if(document.getElementById("smallCateId").selectedIndex==0){
			document.getElementById("smallCateId_msg").innerHTML="请选择所属小分类!";
			result=false;	
		}
		
		
		if(result==true){
			result=confirm("确定提交吗?");
		}
		
		return result;
	}
	
	//重置(清除页面信息)
	function clearInfo()
	{
		document.getElementById("goodsName_msg").innerHTML="*";
		document.getElementById("unit_msg").innerHTML="";
		document.getElementById("goodsCount_msg").innerHTML="";
		document.getElementById("price_msg").innerHTML="";
		document.getElementById("bigCateId_msg").innerHTML="";
		document.getElementById("smallCateId_msg").innerHTML="";
		
		document.getElementById("lblMsg").innerHTML="";
		
		document.form1.goodsName.value="";
		document.form1.unit.value="";
		document.form1.price.value="";
		document.form1.goodsCount.value="";
	}
	
	</script>

<link rel="stylesheet" href="css/style2.css" type="text/css"></link>
</head>

<body class="p10">
	<%
		CateInfoDao cateInfoDao = new CateInfoDaoImpl();
		List<CateInfo> cateList = cateInfoDao.queryAllCateList(0);
		request.setAttribute("cateList", cateList);
		
	 %>
		
	<form name="form1" action="addGoods.do?flag=addGoods" method="post" enctype="multipart/form-data" >
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td class="title2"><img src="images/tb.gif" height="14" width="14" class="ico">添加商品信息</td>
				</tr>
			</tbody>
		</table>
		<div style="margin-top: 8px"></div>
		<table border="0" cellpadding="0" cellspacing="1" width="100%" class="table2">
		<tr>
			<td align="right">商品名称：</td>
			<td><input type="text" style="margin-left: 15px" name="goodsName" value='' />
				<label id="goodsName_msg">*</label>									
			</td>
		</tr>
		<tr>
			<td align="right">所属大分类：</td>
			<td>
				<select name="bigCateId" id="bigCateId" style="margin-left: 15px">	
					<option value="-1">---大分类---</option>				
					<c:forEach var="cate" items="${cateList }">										
						<option value='${cate.id }'>${cate.cateName }</option>					
					</c:forEach>				
				</select><label id="bigCateId_msg"></label>
				<select name="smallCateId" id="smallCateId">
					<option>---小分类---</option>					
				</select><label id="smallCateId_msg"></label>
			</td>
		</tr>
		<tr>
			<td align="right">计量单位：</td>
			<td><input type="text" style="margin-left: 15px" name="unit" value='' />
				<label id="unit_msg">*</label>
			</td>
		</tr>
		<tr>
			<td align="right">商品数量：</td>
			<td><input type="text" style="margin-left: 15px" name="goodsCount" value='' />
				<label id="goodsCount_msg"></label>
			</td>
		</tr>
		<tr>
			<td align="right">商品单价：</td>
			<td><input type="text" style="margin-left: 15px" name=price value='' />
				<label id="price_msg"></label>
			</td>
		</tr>
		<tr>
			<td align="right">商品图片：</td>
			<td><input type="file" name="picture" style="margin-left: 15px" value='' />
				<img alt="" src="/shop-admin/uploadFiles/">
			</td>
		</tr>
		<tr>
			<td align="right">商品描述：</td>
			<td><input type="text" style="margin-left: 15px" name="description" value='' />
			</td>
		</tr>
		<tr>
			<td align="right">商品生产商：</td>
			<td><input type="text" style="margin-left: 15px" name="producter" value='' />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" style="margin-left: 50px" class="bt" value="提交" onclick="return validateInput()" />
				<input type="button" value="重置" class="bt" onclick="clearInfo();" />
				<label id="lblMsg"></label>
			</td>
		</tr>
	 </table>
   </form>
   ${msg }
</body>
</html>
