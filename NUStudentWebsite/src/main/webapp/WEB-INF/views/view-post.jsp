<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css" />
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
		<c:out value="${requestScope.post.title}" />
		<a
			href="${contextPath}/friend/viewprofile.htm?userId=${requestScope.post.user.userId}"><c:out
				value="${requestScope.post.user.userEmail}" /></a> <br>
		${post.text}
		<c:if test="${!empty requestScope.replies}">
			<table>
				<tr>
					<th>Reply</th>
					<th>Replier</th>
				</tr>
				<c:forEach var="item" items="${requestScope.replies}">
					<tr>
						<td>${item.content}</td>
						<td>${item.user.userEmail}</td>
					<tr>
				</c:forEach>
			</table>
		</c:if>
		<form:form action="${contextPath}/post/addreply.htm" method="POST"
			commandName="reply">

			<form:hidden path="postId" value="${requestScope.post.postId}" />
   Content:  <form:textarea rows="4" cols="50" path="content"></form:textarea>
			<form:errors path="content" style="color:red;" />
			<input type="submit" value="Reply" />
			<input type="reset" value="Reset" />
		</form:form>

		<a href="javascript:history.back(-1)">return to former page</a>
	</div>
</body>
</html>