<%@ page language="java" import="java.util.*,com.servlet.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>角色管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link rel="stylesheet" href="css/style2.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script type="text/javascript">

	function firstIndex() {
		window.location.href="queryAdminRole.do?flag=queryAllRole&pageIndex=1";
	}
	
	function upIndex() {
		window.location.href="queryAdminRole.do?flag=queryAllRole&pageIndex="+${pageInfo.pageIndex-1 };
	}
	
	function nextIndex() {
		window.location.href="queryAdminRole.do?flag=queryAllRole&pageIndex="+${pageInfo.pageIndex+1 };
	}
	
	function lastIndex() {
		window.location.href="queryAdminRole.do?flag=queryAllRole&pageIndex="+${pageInfo.pageCount };
	}
	
	function changePageIndex() {
		var sel = document.getElementById("pageIndex");
		var text = sel.options[sel.selectedIndex].text;
		window.location.href="queryAdminRole.do?flag=queryAllRole&pageIndex="+text;
	}

</script>
 </head>
  
<body class="p10">


<form action="UserAction?method=userManage" method="post" name="form1">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td height="30">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tbody>
						<tr>
							<td class="title1" height="24">
								<div class="f_right">
									<span class="White"><img src="images/add.gif" height="10" width="10"> &nbsp;&nbsp;&nbsp; &nbsp;</span>
									<span class="STYLE1"> &nbsp;</span>
								</div>
								<img src="images/tb.gif" height="14" width="14" class="ico">角色信息列表
							</td>
						</tr>
					</tbody>
				</table>
				<div style="margin-top: 8px"></div>
			</td>
		</tr>
		<tr>
    		<td>
    			<table class="table1" width="100%" border="0" cellpadding="0" cellspacing="1" >
				    <tr  class="tr">				
				       <td width="10%"  ><div align="center">角色ID</div></td>
				       <td width="10%"  ><div align="center">角色名称</div></td>
				       <td width="15%"  ><div align="center">角色描述</div></td>
				       <td width="8%"   ><div align="center">操作 </div></td>
				    </tr> 
				    <c:forEach items="${roleList }" var="role">
				    	<tr>
							<td align="center"><input type="checkbox" name="ck_id" value="${role.id }" /></td>
							<td align="center">${role.roleName }</td>						
							<td align="center">${role.des }</td>	
							<td align="center">
								<a href="role/roleREdit.jsp?id=${role.id }&roleName=${role.roleName }&des=${role.des }">修改</a> | 
								<a href="deleteRole.do?flag=deleteRole&id=${role.id }&pageIndex=${pageInfo.pageIndex}" onclick="return confirm('确定要删除该角色吗')" >删除</a> |  
								<a href="role/RoleEdit.jsp?roleId=${role.id }">角色权限分配</a>
							</td>
					</tr>  
				    </c:forEach>         
					
    			</table>
    		</td>
  		</tr>
    <tr>
    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="33%"><div align="left"><span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong> ${pageInfo.rowCount }</strong> 条记录，当前第<strong> ${pageInfo.pageIndex }</strong> 页，共 <strong>${pageInfo.pageCount }</strong> 页</span></div></td>
        <td width="67%"><table width="312" border="0" align="right" cellpadding="0" cellspacing="0">
          <tr>
            <td width="49"><div align="center"><img src="images/main_54.gif" width="40" height="15" onclick="firstIndex();"/></div></td><!--首页 -->
            <td width="49"><div align="center">
            	<c:choose>
			    	<c:when test="${pageInfo.hasPre }">
			    		<img src="images/main_56.gif" width="45" height="15" onclick="upIndex();" />
			    	</c:when>
			    	<c:otherwise>
			    		<img src="images/main_56.gif" width="45" height="15"/>
			    	</c:otherwise>	    	
		   		</c:choose>          
            </div></td><!--前一页 -->      
            <td width="49"><div align="center">
            	<c:choose>
			    	<c:when test="${pageInfo.hasNext }">
			    		<img src="images/main_58.gif" width="45" height="15" onclick="nextIndex();"/>
			    	</c:when>
			    	<c:otherwise>
			    		<img src="images/main_58.gif" width="45" height="15"/>
			    	</c:otherwise>	    	
		   		</c:choose>                  	            	
            	</div></td><!--后一页 -->
            <td width="49"><div align="center"><img src="images/main_60.gif" width="40" height="15" onclick="lastIndex();"/></div></td><!--尾页 -->
           
           
            <td width="37" class="STYLE22"><div align="center">转到</div></td>
            <td width="22"><div align="center">
            	<select id="pageIndex" name="pageIndex" onChange="changePageIndex()" style="width:32px; height:18px; font-size:12px; border:solid 1px #7aaebd;">				
					<c:forEach begin="1" end="${pageInfo.pageCount }" var="i">
						<c:if test="${i==pageInfo.pageIndex }">
							<option value="" selected="selected"> ${i }</option>
						</c:if>
						<c:if test="${i!=pageInfo.pageIndex }">
							<option value="" > ${i }</option>
						</c:if>
					</c:forEach>									
				</select>        
            	</div></td>
            <td width="22" class="STYLE22"><div align="center">页</div></td>
         <!-- <td width="35"><a href="Action?method=Manage"><img src="images/main_62.gif" width="26" height="15" /></a></td> -->   
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>

    
</form>
</body>
</html>

