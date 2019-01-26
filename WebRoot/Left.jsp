<%@ page language="java" import="java.util.*,com.beans.*,com.dao.*,com.dao.impl.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="js/jquery.easing.js"></script>
<script type="text/javascript" src="js/jquery.dimensions.js"></script>
<script type="text/javascript" src="js/jquery.accordion.js"></script>
<script language="javascript">
	jQuery().ready(function(){
		jQuery('#navigation').accordion({
			header: '.head',
			navigation1: true, 
			event: 'click',
			fillSpace: true,
			animated: 'bounceslide'
		});
	});
</script>
<style type="text/css">
body {
	margin:0px;
	padding:0px;
	font-size: 12px;
}
#navigation {
	margin:0px;
	padding:0px;
	width:147px;
}
#navigation a.head {
	cursor:pointer;
	background:url(images/main_34.gif) no-repeat scroll;
	display:block;
	font-weight:bold;
	margin:0px;
	padding:5px 0 5px;
	text-align:center;
	font-size:12px;
	text-decoration:none;
}
#navigation ul {
	border-width:0px;
	margin:0px;
	padding:0px;
	text-indent:0px;

}
#navigation li {
	list-style:none; display:inline;
text-align:center;
}
#navigation li li a {
	display:block;
	font-size:12px;
	text-decoration: none;
	text-align:center;
	padding:3px;
}
#navigation li a img {
	
	border: none;
}

#navigation li li a:hover {
	background:url(images/tab_bg.gif) repeat-x;
		border:solid 1px #adb9c2;
}

#navigation li li a:hover {
	background:url(images/tab_bg.gif) repeat-x;
		border:solid 1px #adb9c2;
}
</style>
</head>
<body>
<%
	AdminInfo adminInfo = (AdminInfo)session.getAttribute("adminInfo");
		
	MenuInfoDao menuInfoDao  = new MenuInfoDaoImpl();
	String menuIds = menuInfoDao.queryMenuId(adminInfo.getRoleId());  //菜单id
	if(menuIds!=null){
		menuIds = menuIds.substring(0, menuIds.length()-1);
		System.out.print(menuIds);
	}
	List<MenuInfo> menuList = menuInfoDao.queryAllMenuList(0, menuIds);
	request.setAttribute("menuList", menuList);
 %>

<div  style="height:100%;" >

  <ul id="navigation"> 
  	<c:forEach var="menu" items="${menuList }">
  		<li>
			<a class="head">${menu.menuName }</a>
			<ul>
				<c:forEach var="subMenu" items="${menu.subMenuList }">
					<li>
						<a href="${subMenu.url }" target="${subMenu.target }"><img src="${subMenu.icon }" /><label>${subMenu.menuName }</label></a>		
					</li>
				</c:forEach>
				
			</ul>
		</li>
  	</c:forEach> 
	   	

  </ul>
</div>
</body>
</html>
