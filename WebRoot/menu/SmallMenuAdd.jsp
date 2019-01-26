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
    
    <title>二级菜单添加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
		table label {color:red; font-size:12px;margin-left:5px } 
</style>
<style type="text/css">
			#toggle-button{
				display:none;
			}
			.button-label{
				position: relative;
				display: inline-block;
				width: 80px;
				height: 25px;
				background-color: #ccc;
				box-shadow: #ccc 0px 0px 0px 2px;
				border-radius: 25px;
				overflow: hidden;
			}
			.circle{
				position: absolute;
				top: 0;
				left: 0;
				width: 25px;
				height: 25px;
				border-radius: 50%;
				background-color: #fff;
			}
			.button-label .text {
				line-height: 25px;
				font-size: 18px;
				text-shadow: 0 0 2px #ddd;
			}

			.on { color: #fff; display: none; text-indent: 10px;}
			.off { color: #fff; display: inline-block; text-indent: 34px;}
			.button-label .circle{
				left: 0;
				transition: all 0.3s;
			}
			#toggle-button:checked + label.button-label .circle{
				left: 50px;
			}
			#toggle-button:checked + label.button-label .on{ display: inline-block; }
			#toggle-button:checked + label.button-label .off{ display: none; }
			#toggle-button:checked + label.button-label{
				background-color: #51ccee;
			}

		</style>
		<script>
			function test(){
				alert(!document.getElementById("toggle-button").checked);
			}
		</script>
<link rel="stylesheet" href="css/style2.css" type="text/css"></link>
<script type="text/javascript">
	function validateInput()
	{
		var result=true;
		document.getElementById("lblMenuName").innerHTML="*";		
		if(form1.cateName.value=="")
		{
			document.getElementById("lblMenuName").innerHTML="分类名称不能为空";
			document.getElementById("lblTarget").innerHTML="分类名称不能为空";
			document.getElementById("lblUrl").innerHTML="分类名称不能为空";
			document.getElementById("lblIcon").innerHTML="分类名称不能为空";
			result=false;		
		}
		
		if(result==true)
		{
			result=confirm("确定提交吗?");
		}
		
		return result;
			
	}
	
	//重置(清除页面信息)
	function clearInfo()
	{
		document.getElementById("lblMenuName").innerHTML="*";
		document.getElementById("lblTarget").innerHTML="*";			
		document.getElementById("lblUrl").innerHTML="*";
		document.getElementById("lblIcon").innerHTML="*";
		document.form1.menuName.value="";
		document.form1.target.value="";
		document.form1.url.value="";
		document.form1.icon.value="";
	}
	</script>
 </head>
  
<body class="p10"> 
<%
	MenuInfoDao menuInfoDao = new MenuInfoDaoImpl();
	List<MenuInfo> menuList = menuInfoDao.queryAllMenuList(0);
	request.setAttribute("menuList", menuList);
 %>
   
  <form  name="form1" action="addSmallMenu.do?flag=addSmallMenu"  method="post" onsubmit="return validateInput()">
  	<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
	 	<tr><td class="title2" > <img src="images/tb.gif" height="14" width="14" class="ico">添加二级菜单</td></tr>
 	</table>
  	<div style="margin-top: 8px"></div>
  	<table  border="0" cellpadding="0" cellspacing="1" width="100%" class="table2">
  		<tbody>
  			<tr><td align="right" width="100">所属一级菜单：</td>
  				<td>
		  			<select name="parentId" style="margin-left: 15px">
		  				<option value="-1" >-------一级菜单-------</option>
		  				<c:forEach var="menu" items="${menuList }">
		  					<option value="${menu.id }" >${menu.menuName }</option>
		  				</c:forEach>							
					</select>
  				</td> 	
  			</tr>
			<tr><td align="right">二级菜单名称：</td><td><input type="text" style="margin-left: 15px" name="menuName"  value='' /><label id="lblMenuName">*</label></td></tr>
			<tr><td align="right">target：</td><td><input type="text" style="margin-left: 15px" name="target"  value='' /><label id="lblTarget">*</label></td></tr>
			<tr><td align="right">url-href：</td><td><input type="text" style="margin-left: 15px" name="url"  value='' /><label id="lblUrl">*</label></td></tr>
			<tr><td align="right">icon：</td><td><input type="text" style="margin-left: 15px" name="icon"  value='' /><label id="lblIcon">*</label></td></tr>
  			<tr><td align="center" width="110"><label>是否赋予此权限？</label></td>
		  	  	<td>
			  		<input type="checkbox" id="toggle-button" style="display:none;" name="switch">
				    <label for="toggle-button" class="button-label"  onclick="test();">
				        <span class="circle"></span>
				        <span class="text on">ON</span>
				        <span class="text off">OFF</span>
				    </label>
		  	  </td>
			</tr>
  			<tr><td colspan="2"><input type="submit" style="margin-left: 50px"  class="bt" value="提交" />  <input type="button" value="重置" class="bt" onclick="clearInfo();"/><label  id="lblMsg"></label></td></tr>
  		</tbody>
  	</table>
  </form>
  ${msg }
</body>
</html>