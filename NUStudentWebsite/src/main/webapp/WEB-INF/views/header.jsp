<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


    
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Blog Template for Bootstrap</title>


 <c:set var="contextPath" value="${pageContext.request.contextPath}" />
 
   <div class="blog-masthead">
      <div class="container">
        <nav class="blog-nav">
          <a class="blog-nav-item active" href="${contextPath}/index.htm">Home</a>
          <c:choose>
	          <c:when test="${empty sessionScope.user}">
          <a class="blog-nav-item" href="${contextPath}/login.htm">Login</a>
          <a class="blog-nav-item" href="${contextPath}/signin.htm">Sign in</a>
          </c:when>
	      <c:otherwise>
          <a class="blog-nav-item" href="${contextPath}/logout.htm">Logout</a>
         Welcome&nbsp<c:out value="${ sessionScope.user.userEmail}"/>
       <img id="image" src="${pageContext.request.contextPath}${ sessionScope.user.profile}" style="width: 50px;height:50px;"/>
   </c:otherwise>
	       </c:choose>
        </nav>
      </div>
    </div>


