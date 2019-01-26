<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	function validateInput()
	{
		var result=true;

		document.getElementById("lblAdminName").innerHTML="*";
		document.getElementById("lblMsg").innerHTML="";
			
		
		if(form1.adminName.value=="")
		{
			document.getElementById("lblAdminName").innerHTML="用户名不能为空!";
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
		document.getElementById("lblAdminName").innerHTML="*";
		document.form1.adminName.value="";
		document.form1.description.value="";
	}
	</script>

  <link rel="stylesheet" href="css/style2.css" type="text/css"></link></head>
  
<body class="p10">    
  <form  name="form1" action="updateAdmin.do?flag=updateAdmin&adminNo=${adminNo }&pageIndex=${pageIndex}"  method="post" onsubmit="return validateInput();">  
  	<input type="hidden" name="id" value='1' />
 	<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
		 	<tr>
		 		<td class="title2" > <img src="images/tb.gif" height="14" width="14" class="ico">管理员信息维护</td>
		 	</tr>
	    </tbody>
 	</table>
	<div style="margin-top: 8px"></div>
		<table  border="0" cellpadding="0" cellspacing="1"
			width="100%" class="table2">
			<tbody>
				<tr>
					<td align="right" width="100">管理员账号</td>
					<td>				
						<input style="margin-left: 15px" type="text" name="adminNo" value='${param.adminNo }' readonly="readonly" />
						<label id="lblAdminNo"></label>
					</td>
				</tr>
				<tr>
					<td align="right">管理员姓名</td>
					<td>
						<input style="margin-left: 15px" type="text" name="adminName" value='${param.adminName }' />
						<label id="lblAdminName">*</label>
					</td>
				</tr>
				<tr>
					<td align="right">管理员密码</td>
					<td>
						<input style="margin-left: 15px" type="password" name="password" value='${param.password }' readonly="readonly" />
						<label id="lblPassword"></label>
					</td>
				</tr>
				<tr>
					<td align="right">管理员描述</td>
					<td>
						<input style="margin-left: 15px" type="text" name="description" value='${param.description }' />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" style="margin-left: 50px" class="bt" value="提交"  >						
						<input type="button" value="重置" onclick="clearInfo();" class="bt" />
						<label id="lblMsg"></label>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>