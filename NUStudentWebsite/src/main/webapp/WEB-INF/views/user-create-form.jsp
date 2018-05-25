<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.captcha.botdetect.web.servlet.Captcha"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value="/resources/jcrop/css/jquery.Jcrop.css"/>" type="text/css"></link>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"> -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
<script type="text/javascript" src="<c:url value="/resources/jcrop/js/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jcrop/js/jquery.Jcrop.js"/>"></script>
<script>

	function ajaxEvent() {
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
	 			if(xmlHttp.responseText.trim()!='no'){
				document.getElementById("demo2").innerHTML = "already exist";
				document.getElementById("queryString").value="";
	 			}
			}
		}
		
		var queryString = document.getElementById("queryString").value;

		xmlHttp.open("POST", ctx+"/ajaxservice.htm?email="+queryString, true);
		xmlHttp.send();
	}
</script>
<title>User Registration</title>
</head>
<body id="top">
<!--  <div class="container">-->
<div id="main">
	<jsp:include page="header.jsp"></jsp:include>
	<font color="red">${errorMessage}</font>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<p id="demo"></p>
	<form:form action="${contextPath}/user/create.htm"
		enctype="multipart/form-data" method="POST" commandName="user">
	  <input type="hidden" id="x" name="x" />
	  <p id="demo2"  style="color:red;" ></p>
		<table>
		
			<tr>
			 
				<td>User Email:</td>
				<td><form:input type="text" id="queryString" onkeyup="ajaxEvent()" path="userEmail" size="30"
					required="required" /><form:errors path="userEmail" /></td>
			</tr>
            <tr>
			<td>Password:</td>
			<td><form:input type="password" path="userPassword" size="30"
				required="required" /><form:errors path="userPassword" /></td>
			</tr>
			<tr>
			<td colspan="2"><label for="captchaCode" class="prompt">Retype
						the characters from the picture:</label> <!-- Captcha code --> <%
 	Captcha captcha = Captcha.load(request, "captchaObject");
 	captcha.setUserInputID("captchaCode");
 	String captchaHtml = captcha.getHtml();
 	out.println(captchaHtml);
 %> <input id="captchaCode" type="text" name="captchaCode" required="required"/></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Login" /></td>
			</tr>

		</table>
	
		Profile <input class="photo-file" type="file" name="imgFile"
			id="fcupload" onchange="readURL(this);" /> <img alt="" src=""
			id="cutimg" /> <input type="hidden" id="y" name="y" /> <input
			type="hidden" id="w" name="w" /> <input type="hidden" id="h"
			name="h"/>
			
		<!--  </div>-->	
			<div id="preview-pane">
			<div class="preview-container">
				<img src="" class="jcrop-preview" alt="Pre see">
			</div>
		</div>
		</form:form>
</div>
<script>
	var api = null,

	boundx, boundy,

	$preview = $('#preview-pane'),
			$pcnt = $('#preview-pane .preview-container'),
			$pimg = $('#preview-pane .preview-container img'),

			xsize = $pcnt.width(), ysize = $pcnt.height();

	function readURL(input) {

		if (input.files && input.files[0]) {

			var reader = new FileReader();
			reader.readAsDataURL(input.files[0]);

			reader.onload = function(e) {
				$('#cutimg').removeAttr('src');
				$('#cutimg').attr('src', e.target.result);
				$pimg.removeAttr('src');
				$pimg.attr('src', e.target.result);

				api = $.Jcrop('#cutimg', {
					setSelect : [ 20, 20, 200, 200 ],
					aspectRatio : 1,
					onSelect : updateCoords,
					onChange : updateCoords
				});
				var bounds = api.getBounds();
				boundx = bounds[0];
				boundy = bounds[1];
				$preview.appendTo(api.ui.holder);

			};
			if (api != undefined) {
				api.destroy();
			}
			
		}
		function updateCoords(obj) {
			$("#x").val(obj.x);
			$("#y").val(obj.y);
			$("#w").val(obj.w);
			$("#h").val(obj.h);
			if (parseInt(obj.w) > 0) {
				var rx = xsize / obj.w;
				var ry = ysize / obj.h;
				$pimg.css({
					width : Math.round(rx * boundx) + 'px',
					height : Math.round(ry * boundy) + 'px',
					marginLeft : '-' + Math.round(rx * obj.x) + 'px',
					marginTop : '-' + Math.round(ry * obj.y) + 'px'
				});
			}
			
		}
		;
	}
</script>

</body>
</html>