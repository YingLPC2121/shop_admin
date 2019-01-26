<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" type="text/css" href="css/maintable.css" ></link>
	<script type="text/javascript" src="js/jquery-1.8.0.js"></script> 
	<script type="text/javascript">
		$(function(){	
			$("table tr").mouseover(function(){
				$(this).css("background","#D3EAEF");
				$(this).siblings().css("background","white");
			});
		});
	</script>

	<script>
		function subForm(pageIndex)
		{
		    //pageIndex 不传入,表示,是点击"转到"按钮的时候触发的
		    if(pageIndex){
		    	window.location.href="${pageContext.request.contextPath}/PermServlet.do?flag=listadmin&pageIndex="+pageIndex;
		    }
		    else{
		        window.location.href="${pageContext.request.contextPath}/PermServlet.do?flag=listadmin&pageIndex="+document.getElementById("pageIndex").value;
		    }	
		}
		
		function firstIndex() {
			window.location.href="queryAdminRole.do?flag=queryAdminRole&pageIndex=1";
		}
	
		function upIndex() {
			window.location.href="queryAdminRole.do?flag=queryAdminRole&pageIndex="+${pageInfo.pageIndex-1 };
		}
	
		function nextIndex() {
			window.location.href="queryAdminRole.do?flag=queryAdminRole&pageIndex="+${pageInfo.pageIndex+1 };
		}
		
		function lastIndex() {
			window.location.href="queryAdminRole.do?flag=queryAdminRole&pageIndex="+${pageInfo.pageCount };
		}
	
		function changePageIndex() {
			var sel = document.getElementById("pageIndex");
			var text = sel.options[sel.selectedIndex].text;
			window.location.href="queryAdminRole.do?flag=queryAdminRole&pageIndex="+text;
		}
	</script>
  </head> 
<body>

	<div class ="div_title">
		<div class="div_titlename"><img src="images/san_jiao.gif" ><span>管理人员基本信息列表</span></div>
	</div>
	<table class="main_table" >
       <tr><th>账号</th><th>状态</th><th>用户角色</th><th>操作</th></tr>
       <c:forEach items="${adminList }" var="admin">
       	<tr>
			<td align="center">${admin.adminNo }</td>	
			<td align="center">
				<c:choose>
	    	 			<c:when test="${admin.isLock<3 }">
	    	 				未锁定
	    	 			</c:when>
	    	 			<c:otherwise>
	    	 				已锁定
	    	 			</c:otherwise>
	    	 		</c:choose>
			</td >	
			<td align="center">${admin.roleName }</td>	
			<td align="center"><a href="role/adminRole.jsp?adminNo=${admin.adminNo }&roleName=${admin.roleName}">角色分配</a> </td>
 		</tr>	
       </c:forEach> 							 								 		
	</table>
				
<div class="div_page" >
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
    </table>
	</div>	
  </body>
</html>