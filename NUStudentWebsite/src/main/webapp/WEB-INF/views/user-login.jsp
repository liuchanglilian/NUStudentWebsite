<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Login page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/images/icons/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/util.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/login.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/vendor/daterangepicker/daterangepicker.css">
</head>
<body>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-90 p-b-30">
				<form class="login100-form validate-form"
					action="${contextPath}/login.htm" method="POST">
					<span class="login100-form-title p-b-40"> Login </span>

					<div class="text-center p-t-55 p-b-30">
						<span class="txt1"> Login with email </span>
					</div>


					<div class="wrap-input100 validate-input m-b-16"
						data-validate="Please enter email: ex@abc.xyz">
						<input class="input100" type="email" name="username"
							placeholder="Email" required="required" /> <span
							class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-20"
						data-validate="Please enter password">
						<span class="btn-show-pass"> <i class="fa fa fa-eye"></i>
						</span> <input class="input100" type="password" name="password"
							placeholder="Password" required="required" /> <span
							class="focus-input100"></span>
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn">Login</button>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

				</form>
				<div class="flex-col-c p-t-224">
					<span class="txt2 p-b-10"> Forget Password? </span> <a
						href="${contextPath}/user/forgotpassword.htm"
						class="txt3 bo1 hov1"> Findback password </a>
				</div>
				<div class="flex-col-c p-t-224">
					<span class="txt2 p-b-10"> Don’t have an account? </span> <a
						href="${contextPath}/signin.htm" class="txt3 bo1 hov1"> Sign
						up now </a>
				</div>
				</div>
				</div>
				</div>
</body>
</html>