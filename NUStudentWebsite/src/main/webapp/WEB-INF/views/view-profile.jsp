<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css" />
<title>View profile</title>
</head>
<body id="top">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<header id="header">
	<div class="inner">
		<ul class="nav nav-tabs nav-stacked" style="width: 600px">
			<c:forEach var="item" items="${sessionScope.user.friends}">
				<li class="active" style="width: 300px"><a
					href="${contextPath}/post/viewpostOfuser.htm?userId=${item.userId}">
						${item.userEmail}</a>&nbsp;&nbsp;&nbsp;&nbsp; <img id="image"
					src="${pageContext.request.contextPath}${item.profile}"
					style="width: 50px; height: 50px;"></li>
			</c:forEach>
		</ul>

	</div>
	</header>
	<div id="main">
		<jsp:include page="header.jsp"></jsp:include>
		<div style="text-align: center; font-size: 20px;">Viewing
			profile</div>
		User email:
		<c:out value="${requestScope.viewUser.userEmail}" />
		&nbsp;&nbsp;&nbsp;&nbsp; User credit:
		<c:out value="${requestScope.viewUser.credit}" />
		<img id="image" src="${contextPath}${requestScope.viewUser.profile}"
			style="width: 50px; height: 50px;"> <a class="blog-nav-item"
			href="${contextPath}/friend/add.htm?userId=${requestScope.viewUser.userId}">Add
			friend</a>


	</div>
</body>
</html>