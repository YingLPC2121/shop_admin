<%@ page language="java" import="java.util.*,com.dao.*,com.dao.impl.*" pageEncoding="utf-8"%>
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

<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script type="text/javascript">
	function validateInput()
	{
		var result=true;

		document.getElementById("lblCateName").innerHTML="*";
		document.getElementById("lblMsg").innerHTML="";
			
		
		if(form1.cateName.value=="")
		{
			document.getElementById("lblCateName").innerHTML="用户名不能为空!";
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
		document.getElementById("lblCateName").innerHTML="*";
		document.form1.cateName.value="";

	}
	
	$(function(){
		$("#bt").click(function(){
			var id = $("#id").val();
			var cateName = $("#cateName").val();
			$.ajax({
				url:"updateCate.do",
				type:"post",
				data:{
					flag:"updateCate",
					id:id,
					cateName:cateName 
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
	int id = Integer.parseInt(request.getParameter("id"));  //二级菜单的id
	MenuInfoDao menuInfoDao = new MenuInfoDaoImpl();
	
	
 %> 
  <form  name="form1" action=""  method="post" >  
  	<input type="hidden" name="id" id="id" value='${param.id }'>
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
				<tr><td align="right" width="100"> 所属一级菜单</td>
					<td><label >${param.bigMenuName }</label></td>
				</tr>
				<tr><td align="right">菜单名称</td>
					<td>
						<input style="margin-left: 15px" type="text" id="cateName" name="cateName" value='${param.cateName }' />
						<label id="lblCateName">*</label>
					</td>
				</tr>								
				<tr>
					<td colspan="2">
						<input style="margin-left: 50px" id="bt" value="提交" onclick="return validateInput()" type="button">						
						<input type="button" value="重置" onclick="clearInfo();" class="bt" />
						<label id="lblMsg"></label>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
  </body>
</html>
