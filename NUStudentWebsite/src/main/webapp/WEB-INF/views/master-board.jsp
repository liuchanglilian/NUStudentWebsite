<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
<title>manager board</title>
</head>
<body id="top">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<header id="header">
				<div class="inner">
				<ul class="nav nav-tabs nav-stacked" style="width:600px">
					<c:forEach var="item" items="${sessionScope.user.friends}">
						<li class="active" style="width:300px">
						 <a href="${contextPath}/post/viewpostOfuser.htm?userId=${item.userId}">
						${item.userEmail}</a>&nbsp&nbsp&nbsp&nbsp
				        <img
							id="image" src="${pageContext.request.contextPath}${item.profile}"
							style="width: 50px; height: 50px;"></li>
					</c:forEach>
				</ul>
				
				</div>
			</header>
<div id="main">
   <jsp:include page="header.jsp"></jsp:include>
  <a href="${contextPath}/master/addboard.htm">Add Board</a>&nbsp&nbsp
  <a href="${contextPath}/master/userlist.htm">Manage user</a>&nbsp&nbsp
  <!-- <a href="${contextPath}/logout.htm">Logout</a>&nbsp&nbsp  -->
</div>
</body>
</html>