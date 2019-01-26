<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
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
	<script type="text/javascript"  src="js/validate.js"></script>
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
			
			
			//验证角色名
			function validateRoleName(item){
				var result=true;
				var reg=/^\S{2,20}$/;   //2-20个合法字符
				if(!reg.test(item.value)){
					result=false;
					showError(item,"角色名称格式不正确");
				}	
			
				if(result==true){
					showOk(item);
				}
				return result;	
			}
													
			//总验证
			function validateInput(){
				$("#result_msg").html("");  //清除原来的验证信息
				var result=true;
				
				if(validateRoleName(document.getElementById("roleName"))==false){
					result=false;
				}
				if(result==true){
					result=confirm('确定提交吗');
				}
				
				return result;
			}
  	</script>
  </head>
  
  <body>
     <div class ="div_title">
		 <div class="div_titlename"> <img src="images/san_jiao.gif" ><span>角色修改</span></div>
	 </div>
				 
	 <form action="updateRole.do?flag=updateRole&id=${param.id }" method="post"  onsubmit="return validateInput()">
		 <table class="edit_table" >
		 		<tr>
	 			 	<td class="td_info">角色名称:</td>	
	 			 	<td class="td_input_short"> 
	 			 		<input type="text" class="txtbox" id="roleName" name="roleName" value="${param.roleName }" onfocus="showInfo(this,'2-15位,非空白字符')" onblur="validateRoleName(this)" /> 
	 			 	</td>   
	 			 	<td>
	 			 		<label class="validate_info" id="adminName_msg">2-15位,非空白字符</label>
	 			 	</td> 
		 		</tr>		 				 			
		 		<tr>
		 			<td class="td_info">角色描述:</td>	
		 			<td><textarea rows="4" cols="27" name="des"  class="txtarea">${param.des }</textarea> </td>	
		 			<td><label></label></td>	
		 		</tr>
		 		<tr>
		 			<td class="td_info"> </td>	
		 			<td> 
			 			<input class="form_btn" type="submit" value="提交" /> 
			 			<input type="reset"  class="form_btn" value="重置" /> </td>	
		 			<td>
		 				<label id="result_msg" class="result_msg"></label>
		 			</td>	
		 		</tr>
		</table>
     </form>
     ${msg }
  </body>
</html>