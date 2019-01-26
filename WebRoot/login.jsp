<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<style type="text/css">
	body {
		margin: 0;
		padding: 0;
		font-size: 12px;
		text-align: center;
		font-family: Arial, Helvetica, sans-serif;
		background: #6eb2e5 url(images/bg_LoginBody.jpg) left top repeat-x;
	}
	
	#container {
		background: url(images/bg_login.jpg) left top no-repeat;
		width: 487px;
		height: 296px;
		margin: 0;
	}
	
	#boxLeft {
		margin: 172px 0 0 208px;
		float: left;
		display: inline;
		width: 166px;
	}
	
	#boxRight {
		margin: 182px 0 0 10px;
		float: left;
		display: inline;
	}
	
	.inputbox {
		margin: 10px 0 0 0;
		background: url(images/bg_login_inputbox.jpg) left top no-repeat;
		border: none;
		width: 170px;
		height: 24px;
		font-size: 12px;
		font-family: Arial, Helvetica, sans-serif;
		padding: 4px 5px 0;
	}
	
	form {
		display: inline;
	}
	
	.msg {
		font-size: 12px;
		color: red;
	}
</style>

<script type="text/javascript">
	function validateInput()
	{
		if(document.getElementsByName("adminNo")[0].value=="")
		{
			alert("请输入用户名！");
			document.getElementsByName("adminNo")[0].focus();
			return false;
		}
		else if (document.getElementsByName("password")[0].value=="")
	    {
				alert("请输入用户密码！");
		    document.getElementsByName("password")[0].focus();
			return false;
		}	
		return true;
	}
</script>

  </head>
  
  <body>
    <table width="100%"  cellspacing="0" cellpadding="0"
			height="100%">
			<tr>
				<td align="center">
					<div id="container">
						<form action="${pageContext.request.contextPath }/login.action?flag=login" method="post" name="form1">

							<div id="boxLeft">
								<input name="adminNo" type="text" class="inputbox"
									maxlength="16" onFocus="this.bgColor='#6eb2e5'" />
								<input name="password" type="password" class="inputbox"
									maxlength="16" />
								
							</div>
							<div id="boxRight">
								<input name="" type="image" src="images/bg_login_btn.jpg"
									onclick="return validateInput();" />
							</div>
						</form>						
					</div>
					${msg }
				</td>
			</tr>
		</table>
		
  </body>
</html>
