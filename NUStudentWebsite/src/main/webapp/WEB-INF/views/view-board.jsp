<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
</head>
<body  id="top">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<header id="header">
				<div class="inner">
				<ul class="nav nav-tabs nav-stacked" style="width:600px">
					<c:forEach var="item" items="${sessionScope.user.friends}">
						<li class="active" style="width:300px">
						 <a href="${contextPath}/post/viewpostOfuser.htm?userId=${item.userId}">
						${item.userEmail}</a>&nbsp;&nbsp;&nbsp;&nbsp;
				        <img
							id="image" src="${pageContext.request.contextPath}${item.profile}"
							style="width: 50px; height: 50px;"></li>
					</c:forEach>
				</ul>
				
				</div>
			</header>
<div id="main">
<jsp:include page="header.jsp"></jsp:include>
<table>
<tr>
<th>Post tile:

<th>Poster:
</th>
<th>
reply number:
</th>
</tr>
<c:forEach var="item" items="${requestScope.postList}">
    <tr>
    <td><a href="${contextPath}/post/postdetail.htm?postId=${item.postId}"></a>${item.title}</td>
    <td>${item.user.userEmail}</td>
    <td>${item.replyNumber}</td>
    </tr>
    </c:forEach>
</table>
<form:form action="${contextPath}/user/newpost.htm" method="POST"  commandName="post">
   
   Title:  <form:input type="text" path="title" size="30" required="required" /><form:errors path="title"/><br/>
   Content:  <form:textarea rows="4" cols="50" path="text" required="required"></form:textarea><form:errors path="text" style="color:red;"/>
    <input type="hidden" name="boardId" value="${requestScope.boardId}" />
    <input type="submit" value="Post" />&nbsp;
     <input type="reset" value="Reset"/>
	</form:form>
</div>
</body>
</html>