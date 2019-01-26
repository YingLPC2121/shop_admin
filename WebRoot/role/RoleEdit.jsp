<%@ page language="java" import="java.util.*,com.dao.impl.*,com.dao.*,com.beans.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>权限管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">

		$(function(){
			$("#btnSubmit").click(function(){		
			 var  menuList = new Array(); 
			 $("input[type='checkbox']:checked").each(function(){ 
			    menuList.push(this.value);
			 }); 
				
			 $("#menusIdStr").val(menuList.join(""));	
		 	 //alert($("#menusIdStr").val());		
			});
		});


		function checkSubItem(parentId){
		 $("."+parentId).attr("checked",$("#"+parentId).attr("checked"));
		}

		
		function checkParent(parentId){
			$("#"+parentId).removeAttr("checked");		
			 $("."+parentId+":checked").each(function(){
			 	$("#"+parentId).attr("checked","checked");
			 });		
		}
	  	

	</script>
<link rel="stylesheet" href="css/style2.css" type="text/css"></link></head>  
<body class="p10">	
<%
	int roleId = Integer.parseInt(request.getParameter("roleId"));
	MenuInfoDao menuInfoDao  = new MenuInfoDaoImpl();
	List<MenuInfo> menuList = menuInfoDao.queryAllMenuList(0);
	request.setAttribute("menuList", menuList);
	
	String menuIdStr = menuInfoDao.queryMenuId(roleId);
	//System.out.print(menuIdStr);
	request.setAttribute("menuIdStr", menuIdStr);
 %>

   <form action="addRoleMenu.do?flag=addRoleMenu" method="post" >
   		<!-- <input type="hidden" id="menusIdStr" name="menusIdStr"> -->
   		<input type="hidden"  name="roleId" value="${param.roleId }"> <!-- 修改权限信息要用到这个id -->
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="30">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td class="title1" height="24">
									<div class="f_right">
										<span class="White"> 
										<img src="images/add.gif" height="10" width="10"> &nbsp;&nbsp;&nbsp; &nbsp;</span>
										<span class="STYLE1"> &nbsp;</span>
									</div>
									<img src="images/tb.gif" height="14" width="14" class="ico">用户权限管理
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
							<td width="30%"><div align="center" >主模块</div></td><td><div align="center">子模块</div></td>
						</tr>
						<c:forEach var="menu" items="${menuList }">
							<tr> 
								<td style="padding-left: 30px" >
									<input id='1' type="checkbox" value='${menu.id }' name="menuIds" onclick="checkSubItem(1)" />${menu.menuName }
							    </td>
							    <td  style="padding-left: 30px">
								    <c:forEach var="subMenu" items="${menu.subMenuList }">								    						
										<input type="checkbox"  onclick="checkParent(1);" class='1' name="menuIds" value='${subMenu.id }' />${subMenu.menuName }<br/>															
								    </c:forEach>
								</td>								
							</tr>							
						</c:forEach>			
												    
					</table>		
					<input type="submit" id="btnSubmit" onclick="return confirm('确定提交吗')" value="提交">			
					<label style="color:red"></label>
  			</tr>
		</table>
   	</form>      
</body>
  <script type="text/javascript">
	
		//给Array类增加一个 contains 方法
  	   Array.prototype.contains = function (element) {    
			for (var i = 0; i < this.length; i++) {    
					if (this[i] == element) {    
						return true;    
					}    
			}    					
			return false;    
		};
   
 		 var menuIdStr = '<%=menuIdStr%>';
 		 //alert(menuIdStr);
 		 var  menuIdList = new Array(); 
  	     menuIdList=menuIdStr.split(",");
  	     alert(menuIdList);
 		 
		 $("input[type='checkbox']").each(function(){ 
		 	 if(menuIdList.contains(this.value)){
		  		 $(this).attr("checked","checked");  //如果传过来的数组中,包含复选框的value (其值是菜单id),则将其勾选
		  	 }
		 }); 
		
	</script>
</html>