<%@ page language="java" import="java.util.*,com.dao.*,com.dao.impl.*,com.beans.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户角色管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


	<link rel="stylesheet" type="text/css" href="css/edittable.css"  ></link>  
	<link rel="stylesheet" type="text/css" href="css/validate.css"  ></link>  
  	<script type="text/javascript"  src="js/jquery-1.8.0.js"></script>
	<script type="text/javascript"  src="${pageContext.request.contextPath }/js/validate.js"></script>
			<script>		
				$(function(){
					 $("input[type=text],textarea").focus(function(){
						  $(this).addClass("input_focus");
						}).blur(function(){
								$(this).removeClass("input_focus");
						});
	
					$(".form_btn").hover(function(){
							$(this).css("color","red").css("background","#6FB2DB");
						},
						
						function(){
							$(this).css("color","#295568").css("background","#BAD9E3");
						});
					
				});
			
				
				//总验证
				function validateInput(){
					$("#result_msg").html("");  //清除原来的验证信息
					var result=true;

					if(result==true){
						result=confirm('确定提交吗');
					}
					
					return result;
					
				}
				
				function back(){
					window.history.back();
					
				}
	  	</script>
 </head>
  
<body>
<%
	RoleInfoDao roleInfoDao = new RoleInfoDaoImpl();
	List<RoleInfo> roleList = roleInfoDao.queryAllRole();
	request.setAttribute("roleList", roleList);
 %>
	<div class ="div_title">
		 <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>分配角色</span></div>
	</div>				 
	<form action="assignRole.do?flag=assignRole" method="post"  onsubmit="return validateInput()">
		<table class="edit_table" >
		 	<tr>
			 	<td class="td_info">用户账号:</td>	
			 	<td class="td_input_short"> 
			 		<input type="text" class="txtbox" id="adminNo" name="adminNo"  readonly="readonly" value="${param.adminNo }"/> 
			 	</td>   
			 	<td>
			 		<label class="validate_info"></label>
			 	</td> 
			</tr>
	 		<tr>
	 			<td class="td_info">用户角色:</td>	
	 			<td>
	 				<c:forEach items="${roleList }" var="role">
	 					<input type="radio" name="roleName"  value="${role.id }" 
	 						<c:if test="${param.roleName==role.roleName }">
	 							checked="checked"
	 						</c:if>	 							
	 					/>${role.roleName } <br/>	 				
	 				</c:forEach>	 				  
	 			</td>		 			
	 		</tr>
	 		<tr>
	 			<td class="td_info"> </td>	
	 			<td> 
	 			<input class="form_btn" type="submit" value="提交" /> 
	 			<button id="btnReset" class="form_btn"  onclick="back()">返回 </button></td>	
	 			<td>
	 				<label id="result_msg" class="result_msg">${msg}</label>
	 			</td>	
	 		</tr>
		</table>
	
     </form>
  </body>
</html>
