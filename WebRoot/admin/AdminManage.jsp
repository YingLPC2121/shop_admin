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
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <script type="text/javascript">
	function checkAllAdmin()
	{
		var checkList=document.getElementsByName("checkAdmin");
		for(var i=0;i<checkList.length;i++)
		{
			checkList[i].checked=document.getElementById("checkAll").checked;
		}
	} 

	function delAdmin()
	{
		var checkList=document.getElementsByName("checkAdmin");
		
		for(var i=0;i<checkList.length;i++)
		{
			var isSelected=false;
			if(checkList[i].checked==true)
			{
				isSelected=true;
				break;
			}			
		}
		
		if(isSelected==true)
		{
			if(confirm("确定要删除选定项吗?"))
			{
				document.form1.action="Action?method=delAdmin";
				document.form1.submit();
			}
		}
		else
		{
			alert("您还没有选择任何项！");
			return false;
		}	
	}
	
	function firstIndex() {
		window.location.href="queryAllAdmin.do?flag=queryAllAdmin&pageIndex=1";
	}
	
	function upIndex() {
		window.location.href="queryAllAdmin.do?flag=queryAllAdmin&pageIndex="+${pageInfo.pageIndex-1 };
	}
	
	function nextIndex() {
		window.location.href="queryAllAdmin.do?flag=queryAllAdmin&pageIndex="+${pageInfo.pageIndex+1 };
	}
	
	function lastIndex() {
		window.location.href="queryAllAdmin.do?flag=queryAllAdmin&pageIndex="+${pageInfo.pageCount };
	}
	
	function changePageIndex() {
		var sel = document.getElementById("pageIndex");
		var text = sel.options[sel.selectedIndex].text;
		window.location.href="queryAllAdmin.do?flag=queryAllAdmin&pageIndex="+text;
	}

</script>
  <link rel="stylesheet" href="css/style2.css" type="text/css"></link>
 </head>
  
<body class="p10">

<form action="" method="post" name="form1">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tbody>
									<tr>
										<td class="title1" height="24">
											<div class="f_right">
												<span class="White"> 
													<input name="checkAll" id="checkAll" onclick="checkAllAdmin();" type="checkbox">全选 &nbsp;&nbsp;
													<img src="images/add.gif" height="10" width="10"> 
													<a style="color: #fff; margin-left: 3px;" href="Add.jsp">添加</a>&nbsp; 
													<img src="images/del.gif" height="10" width="10">
													<input value="刪除" onclick="return del();" style="background: none; color: #fff; border: none;" type="button"> &nbsp;&nbsp; &nbsp;
												</span>
												<span class="STYLE1"> &nbsp;</span>
											</div>
											<img src="images/tb.gif" height="14" width="14" class="ico">
											用户基本信息列表
										</td>
									</tr>
								</tbody>
							</table>
								<div style="margin-top: 8px"></div>
					</td>
				</tr>
				<tr>
    <td><table class="table1" width="100%" border="0" cellpadding="0" cellspacing="1" >
      <tr  class="tr">
        <td width="4%"    ><div align="center"> 选择   </div></td>
        <td width="10%"  ><div align="center">用户ID</div></td>
        <td width="10%"  ><div align="center">用户账号</div></td>
        <td width="15%"  ><div align="center">用名姓名</div></td>
        <td width="15%"  ><div align="center">用户描述</div></td>
        <td width="4%"   ><div align="center">状态</div></td>
        <td width="15%"   ><div align="center">基本操作</div></td>
      </tr>
     
      <c:forEach items="${adminList }" var="admin" varStatus="s">
    	 	<tr>
    	 		<td ><div align="center"><input type="checkbox" name="checkAdmin" value="${admin.id}" /></div></td>
    	 		<td><div align="center">${admin.id}</div></td>
	    	 	<td><div align="center">${admin.adminNo}</div></td>
	    	 	<td><div align="center">${admin.adminName}</div></td>
	    	 	<td><div align="center">${admin.description}</div></td>
	    	 	<td><div align="center">${admin.isLock}</div></td>
	    	 	<td><div align="center" >
	    	 		<c:choose>
	    	 			<c:when test="${admin.isLock<3 }">
	    	 				<a href="onLock.do?flag=onLock&adminNo=${admin.adminNo}&pageIndex=${pageInfo.pageIndex }">上锁</a>
	    	 			</c:when>
	    	 			<c:otherwise>
	    	 				<a href="unLock.do?flag=unLock&adminNo=${admin.adminNo}&pageIndex=${pageInfo.pageIndex }">解锁</a>
	    	 			</c:otherwise>
	    	 		</c:choose>
	    	 	<a href=""></a>|
	    	 	
	    	 	<a href="admin/AdminEdit.jsp?adminNo=${admin.adminNo}&adminName=${admin.adminName}&password=${admin.password}&description=${admin.description}&pageIndex=${pageInfo.pageIndex}" >修改</a> |
	    	 	<a href='deleteAdmin.do?flag=deleteAdmin&adminNo=${admin.adminNo}&pageIndex=${pageInfo.pageIndex}'>删除</a>
	    	 	</div></td>   	 	
    	 	</tr>
     </c:forEach>                               
    </table>
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
${msg }
</body>
</html>
