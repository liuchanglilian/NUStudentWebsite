<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css" />
<title>Insert title here</title>
</head>
<body id="top">

	<h1>Error Page</h1>
	<p>${errorMessage}</p>

	<div id="main">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<jsp:include page="header.jsp"></jsp:include>
	</div>
</body>
</html>