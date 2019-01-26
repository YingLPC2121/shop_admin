<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户添加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
		table label {color:red; font-size:12px;margin-left:5px } //将table下所有的拉示信息标签颜色置红
</style>
<link rel="stylesheet" href="css/style2.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script type="text/javascript">
	function validateInput()
	{
		var result=true;
		document.getElementById("lblAdminNo").innerHTML="*";
		document.getElementById("lblAdminName").innerHTML="*";
		document.getElementById("lblPassword").innerHTML="*";
		document.getElementById("lblPwdConfirm").innerHTML="*";
		document.getElementById("lblMsg").innerHTML="";
			
		if(form1.adminNo.value=="")
		{
			document.getElementById("lblAdminNo").innerHTML="用户账号不能为空!";
			result=false;		
		}
		
		if(form1.adminName.value=="")
		{
			document.getElementById("lblAdminName").innerHTML="用户名不能为空!";
			result=false;		
		}
		
		if(form1.password.value=="")
		{
			document.getElementById("lblPassword").innerHTML="密码不能为空!";
			result=false;		
		}
		else {
			if(form1.pwdConfirm.value=="")
			{
				document.getElementById("lblPwdConfirm").innerHTML="请重复输入密码!";
			    result=false;		
			}
			else if(form1.password.value!=form1.pwdConfirm.value)
			{
				document.getElementById("lblPwdConfirm").innerHTML="两次输入的密码不一致!";
				result=false;		
			}
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
		document.getElementById("lblAdminNo").innerHTML="*";
		document.getElementById("lblAdminName").innerHTML="*";
		document.getElementById("lblPassword").innerHTML="*";
		document.getElementById("lblPwdConfirm").innerHTML="*";
		document.getElementById("lblMsg").innerHTML="";
		
		document.form1.adminNo.value="";
		document.form1.adminName.value="";
		document.form1.password.value="";
		document.form1.pwdConfirm.value="";
		document.form1.description.value="";
	}
	
	$(function(){
		$("#but").click(function(){
			$.ajax({
				url:"addAdmin.do",
				type:"post",
				data:$("#form1").serialize(),
				success:function(data){
					$("#lblMsg").html(data);
				}
			});
		});
	});
	</script>
 </head>
  
<body class="p10">   
  <form  name="form1" id="form1" action=""  method="post" >
  	<input type="hidden" name="flag" value="addAdmin"/>
  	<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
	 	<tr>
	 		<td class="title2" > <img src="images/tb.gif" height="14" width="14" class="ico">添加管理员</td>
	 	</tr>
 	</table>
  <div style="margin-top: 8px"></div>
	  <table  border="0" cellpadding="0" cellspacing="1" width="100%" class="table2">
	  	<tbody>
	  		
	  	  <tr><td align="right" width="100">管理员账号：</td><td><input type="text" style="margin-left: 15px" name="adminNo" id="adminNo"  /><label id="lblAdminNo">*</label></td></tr>
		  <tr><td align="right">管理员姓名：</td><td><input type="text" style="margin-left: 15px" name="adminName"  id="adminName" /><label id="lblAdminName">*</label></td></tr>
		  <tr><td align="right">管理员密码：</td><td><input type="password" style="margin-left: 15px" name="password" id="password" /><label id="lblPassword">*</label></td></tr>
		  <tr><td align="right">重复密码：</td><td><input type="password" style="margin-left: 15px" name="pwdConfirm"  id="pwdConfirm"/><label id="lblPwdConfirm">*</label></td></tr>
		  <tr><td align="right">管理员描述：</td><td><input type="text" style="margin-left: 15px"  name="description" id="description" /></td></tr>
		  <tr><td colspan="2"><input type="button" style="margin-left: 50px"  class="bt" id="but" value="提交" onclick="return validateInput();"/>  <input type="button" value="重置" class="bt" onclick="clearInfo();"/><label  id="lblMsg"></label></td></tr>
	  	</tbody>
	  </table>
 </form>
 ${msg }
</body>
</html>
