<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.10/css/all.css"
	integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg"
	crossorigin="anonymous">
<script>
function read(){

	var xmlHttp;
	var ctx = "<%=request.getContextPath()%>"
	try // Firefox, Opera 8.0+, Safari
	{
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		try // Internet Explorer
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Your browser does not support AJAX!");
				return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function() {
 		if (xmlHttp.readyState == 4) {
 			document.getElementById("hasread").innerHTML = "";
 				}
 			
		}
	xmlHttp.open("GET", ctx+"/message/readmessage.htm?messageid="+maxId, true);
	document.getElementById("hasread").innerHTML = "has read";
	xmlHttp.send();
	

	
}



function send(receiverId){
	var xmlHttp;
	var ctx = "<%=request.getContextPath()%>"
	try // Firefox, Opera 8.0+, Safari
	{
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		try // Internet Explorer
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Your browser does not support AJAX!");
				return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function() {
 		if (xmlHttp.readyState == 4) {

 			if(xmlHttp.responseText.trim()==='yes'){
			document.getElementById("showResult").innerHTML = "Already send";
 			}else{
 				document.getElementById("showResult").innerHTML = "Send failed";
 	 			}
 			
		}
	}


	var message = document.getElementById("message").value;
	var receiverId=document.getElementById("receiverId").value;
	xmlHttp.open("POST", ctx+"/message/sendmessage.htm?message="+message+"&senderid=${sessionScope.user.userId}&receiverid="+receiverId, true);
	xmlHttp.send();

	
}




function showdiv(id) { 
document.getElementById("bg").style.display ="block";
document.getElementById("show").style.display ="block";
document.getElementById("receiverId").value=id;
}
function hidediv() {
document.getElementById("bg").style.display ='none';
document.getElementById("show").style.display ='none';
document.getElementById("showResult").innerHTML = "";
document.getElementById("message").innerHTML="";
}
var maxId=0;
function showmessage(){
	var xmlHttp;
	var ctx = "<%=request.getContextPath()%>"
	try // Firefox, Opera 8.0+, Safari
	{
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		try // Internet Explorer
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Your browser does not support AJAX!");
				return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function() {
 		if (xmlHttp.readyState == 4) {
 	 		
           var response=xmlHttp.responseText;
           if(response!="no message"){
           var words = response.split(':');
           document.getElementById("content").innerHTML="      "+words[0];
           document.getElementById("sender").innerHTML="      "+words[1]+":";
           maxId=words[2];
           }
           else{

        	   document.getElementById("content").innerHTML="      ";
               document.getElementById("sender").innerHTML="      ";


               }
 		
		}
	}
    if("${sessionScope.user.userId}"){
  xmlHttp.open('get',ctx+"/message/getmessage.htm?userid=${sessionScope.user.userId}",true);
	xmlHttp.send();    
	
    }else
        return;
}
//更新信息的执行时机
window.onload = function(){
    setInterval("showmessage()",3000);
   
}
</script>
</head>
<body id="top">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<!--
  Below we include the Login Button social plugin. This button uses
  the JavaScript SDK to present a graphical Login button that triggers
  the FB.login() function when clicked.
-->
	<div id="status"></div>

	<!-- Header -->
	<header id="header" style="display: inline-block;">
		<div class="inner">
			<ul class="nav nav-tabs nav-stacked" style="width: 600px">
				<c:forEach var="item" items="${sessionScope.user.friends}">
					<li class="active" style="width: 400px"><a
						href="${contextPath}/post/viewpostOfuser.htm?userId=${item.userId}">
							${item.userEmail}</a>&nbsp&nbsp&nbsp&nbsp <img id="image"
						src="${pageContext.request.contextPath}${item.profile}"
						style="width: 50px; height: 50px;">&nbsp&nbsp&nbsp&nbsp <a
						onclick="showdiv(${item.userId})"> <!-- href="${contextPath}/message/sendmessage.htm?senderId=${sessionScope.user.userId}&receiverId=${item.userId} -->
							<i class="fas fa-envelope fa-3x"></i></a></li>
				</c:forEach>
			</ul>

		</div>
	</header>
	<div id="main"
		style="width: 60%; display: inline-block; vertical-align: top">
		<jsp:include page="header.jsp"></jsp:include>
		<section id="one">
			<header class="major">
				<h2>See the different boards</h2>
			</header>

		</section>
		<p id="here">here</p>

		<c:if test="${!empty boardList}">
			<section id="two">
				<h3>All the boards in website</h3>
				<div class="row">

					<c:forEach var="item" items="${requestScope.boardList}">
						<article class="6u 12u$(xsmall) work-item">
							<a href="${contextPath}/user/addpost.htm?boardId=${item.boardId}">${item.boardName }</a>
							${item.postNumber }
						</article>
					</c:forEach>

				</div>
			</section>
		</c:if>
	</div>
	<div
		style="background-color: lightgrey; width: 12.5%; height: 1000px; display: inline-block; vertical-align: top">
		<table>
			<tr>
				<td>User:</td>
			</tr>
			<tr>
				<td><p id="sender" style="color: black;"></p></td>
			</tr>
			<tr>
				<td>Content:</td>
			</tr>
			<tr>
				<td><p id="content" style="color: black;"></p></td>
			</tr>
			<tr>
				<td>Click read</td>
			</tr>
			<tr>
				<td><a onclick="read()"><i
						class="fas fa-check-square fa-3x "></i></a></td>
				<td><p id="hasread" style="color: red"></p></td>
			</tr>

		</table>
	</div>


	<div id="bg"></div>
	<div id="show">

		Message: <input type="text" id="message" size="30" required="required" />
		<a onclick=send()><i class="fas fa-check-square fa-3x"></i></a> <input
			id="receiverId" style="display: none" />
		<p id="showResult"></p>
		<input id="btnclose" type="button" value="Close" onclick="hidediv();" />
	</div>

</body>
</html>




