<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:if test="${not empty sessionScope.user}">
<c:out value="aloha"/>
<c:forEach var="item" items="${sessionScope.user.friends}">
  
   <c:out value="${item.userEmail}"/> <img id="image" src="${item.profile}" style="width: 50px;height:50px;">
   
   
    </c:forEach>
</c:if>
</body>
</html>