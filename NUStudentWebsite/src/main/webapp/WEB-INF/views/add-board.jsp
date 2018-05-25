<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css" />
</head>
<body id="top">
	<div id="main">
		<jsp:include page="header.jsp"></jsp:include>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<table>
			<tr>
				<th>The boards</th>
			</tr>
			<c:forEach var="item" items="${requestScope.boardList}">
				<tr>
					<td>${item.boardName }</td>
				</tr>
			</c:forEach>
		</table>
		<form:form action="${contextPath}/master/addboard.htm" method="POST"
			commandName="board">
			<form:input type="text" path="boardName" size="30"
				required="required" />
			<form:errors path="boardName" style="color:red;" />
			<input type="submit" value="add Board" />
		</form:form>
	</div>
</body>
</html>