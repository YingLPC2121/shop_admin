<%@ page language="java" import="java.util.*,com.dao.*,com.dao.impl.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>菜单信息维护</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
		<script>				
			function test(){
				alert(!document.getElementById("toggle-button").checked);
				
			}
		</script>

<script type="text/javascript">
	function validateInput()
	{
		var result=true;

		document.getElementById("lblMenuName").innerHTML="*";
		document.getElementById("lblMsg").innerHTML="";
			
		
		if(form1.menuName.value=="")
		{
			document.getElementById("lblMenuName").innerHTML="用户名不能为空!";
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
		document.getElementById("lblMsg").innerHTML="";
		document.getElementById("lblMenuName").innerHTML="*";
		document.form1.menuName.value="";

	}
	
	$(function(){
		$("#bt").click(function(){
			var id = $("#id").val();
			var roleIdStr = $("#roleIdStr").val();
			var roleIds = $("#roleIds").val();
			alert(id);
			$.ajax({
			
				url:"updateMenu.do",
				type:"post",
				data:{
					flag:"updateMenu",
					id:id,
					roleIdStr:roleIdStr,
					roleIds:roleIds
				},
				success:function(data){
					$("#lblMsg").html(data);
				}
			});
		});
	});
</script>

<link rel="stylesheet" href="css/style2.css" type="text/css"></link></head>
  
<body class="p10">
<%
	int menuId = Integer.parseInt(request.getParameter("id"));
	MenuInfoDao menuInfoDao = new MenuInfoDaoImpl();
	String roleIdStr = menuInfoDao.queryRoleId(menuId);  //这个菜单对应的roleId
	roleIdStr = roleIdStr.substring(0,roleIdStr.length()-1);
	System.out.println(menuId+"---"+roleIdStr+"------"+roleIdStr.length());
	
	RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
	String roleIds = roleInfoDao.queryAllRoleId();  //所有的roleId
	roleIds = roleIds.substring(0,roleIds.length()-1);	
	System.out.println(menuId+"---"+roleIds+"------"+roleIds.length());
	
	request.setAttribute("roleIdStr", roleIdStr);
	request.setAttribute("roleIds", roleIds);
	
 %>  
  <form  name="form1" action=""  method="post" >  
  	<input type="hidden" name="id" id="id" value='${param.id }' />
  	<input type="hidden" name="roleIdStr" id="roleIdStr" value='${roleIdStr }' />
  	<input type="hidden" name="roleIds" id="roleIds" value='${roleIds }' />
 	<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
		 	<tr>
		 		<td class="title2" > <img src="images/tb.gif" height="14" width="14" class="ico">菜单信息维护</td>
		 	</tr>
		</tbody>
 	</table>
	<div style="margin-top: 8px"></div>
		<table  border="0" cellpadding="0" cellspacing="1" width="100%" class="table2">
			<tbody>
				<tr>
					<td align="right" width="100">所属一级菜单</td>
					<td><label >${param.menuName }</label></td>
				</tr>
				<tr>
					<td align="right">菜单名称</td>
					<td>
						<input style="margin-left: 15px" type="text" name="menuName" id="menuName" value='${param.menuName }' />
						<label id="lblMenuName">*</label>
					</td>
				</tr>	
				<tr><td align="center" width="110"><label>是否给所有角色赋予此权限？</label></td>
		  	  		<td>
				  		<input type="checkbox" id="toggle-button" style="display:none;" name="switch" 
				  			<c:if test="${roleIdStr ==roleIds }">
				  				checked="checked"
				  			</c:if>
				  		>
					    <label for="toggle-button" class="button-label"  onclick="test();">
					        <span class="circle"></span>					        
				        	<span class="text on">ON</span>					      
				        	<span class="text off">OFF</span>					        
					    </label>
		  	  		</td>
				</tr>							
				<tr>
					<td colspan="2">
						<input style="margin-left: 50px" id="bt" value="提交"  type="button" onclick="return validateInput()">					
						<input type="button" value="重置" onclick="clearInfo();" class="bt" />
						<label id="lblMsg"></label>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	${msg }
  </body>
</html>
